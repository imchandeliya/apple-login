package com.giddh.appleLogin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class NSPersonNameComponents {
    private String familyName;
    private String givenName;
    private String namePrefix;
    private String nameSuffix;
    private String nickName;
    private NSPersonNameComponents phoneticRepresentation;
}
