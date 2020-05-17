package com.giddh.appleLogin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppleUser {
    private String authorizationCode;
    private String email;
    private NSPersonNameComponents fullName;
    private String identityToken;
    private String state;
    private String user;

}
