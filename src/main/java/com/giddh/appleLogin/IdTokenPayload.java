package com.giddh.appleLogin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class IdTokenPayload {
    private String iss;
    private String aud;
    private Long exp;
    private Long iat;
    private String sub;//users unique id
    private String at_hash;
    private Long auth_time;
}