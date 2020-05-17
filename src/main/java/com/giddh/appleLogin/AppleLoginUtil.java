package com.giddh.appleLogin;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.Date;

@Service
public class AppleLoginUtil {
    private static String APPLE_AUTH_URL = "https://appleid.apple.com/auth/token";

    private static String KEY_ID = "**********";
    private static String TEAM_ID = "**********";
    private static String CLIENT_ID = "com.example.yourapp";

    private static String authKeyPath = "path to authkey file (.p8 file)";

    private static PrivateKey pKey;

    private static PrivateKey getPrivateKey() throws IOException {
        //read your key
        String path = new ClassPathResource(authKeyPath).getFile().getAbsolutePath();

        final PEMParser pemParser = new PEMParser(new FileReader(path));
        final JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        final PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        final PrivateKey pKey = converter.getPrivateKey(object);

        return pKey;
    }

    private static String generateJWT() throws IOException {
        if (pKey == null) {
            pKey = getPrivateKey();
        }


        String token = Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, KEY_ID)
                .setIssuer(TEAM_ID)
                .setAudience("https://appleid.apple.com")
                .setSubject(CLIENT_ID)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 5)))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(pKey, SignatureAlgorithm.ES256)
                .compact();

        return token;
    }

    /*
     * Returns unique user id from apple
     * */
    public static String appleAuth(String authorizationCode) throws IOException, UnirestException {

        String token = generateJWT();

        HttpResponse<String> response = Unirest.post(APPLE_AUTH_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("client_id", CLIENT_ID)
                .field("client_secret", token)
                .field("grant_type", "authorization_code")
                .field("code", authorizationCode)
                .asString();

        TokenResponse tokenResponse=new Gson().fromJson(response.getBody(),TokenResponse.class);
        String idToken = tokenResponse.getId_token();
        String payload = idToken.split("\\.")[1];//0 is header we ignore it for now
        String decoded = new String(Decoders.BASE64.decode(payload));

        IdTokenPayload idTokenPayload = new Gson().fromJson(decoded,IdTokenPayload.class);
        return idTokenPayload.getSub();
    }

}
