package com.devozs.components.common.utils;

import com.devozs.components.common.security.SecurityGroupsNaming;
import io.jsonwebtoken.Jwts;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A utility class to extract information from a jwt
 */
@Component
public class JwtUtils {
    public String getUserIdFromToken(String token) {
        return getAttributeFromToken(token, SecurityGroupsNaming.SUB, String.class);
    }

    public String getUserNameToken(String token) {
        return getAttributeFromToken(token, SecurityGroupsNaming.PREFERRED_USER_NAME, String.class);
    }

    public String getUserEmailFromToken(String token) {
        return getAttributeFromToken(token, SecurityGroupsNaming.EMAIL, String.class);
    }

    public List<Long> getTrialTokenFromUser(String token) {
        return getAttributeFromToken(token, SecurityGroupsNaming.TRIAL, ArrayList.class);
    }

    public String getISSFromToken(String token) {
        return getAttributeFromToken(token, SecurityGroupsNaming.ISS, String.class);
    }

    public String getNameFromToken(String token) {
        return getAttributeFromToken(token, SecurityGroupsNaming.NAME, String.class);
    }

    public boolean getIsMasterRealmFromToken(String token) {
        return Optional.ofNullable(getAttributeFromToken(token, SecurityGroupsNaming.ISS, String.class).endsWith(SecurityGroupsNaming.MASTER))
                .orElse(false);
    }


    public boolean getIsAdminFromToken(String unsignedToken) {
        return Optional.ofNullable(getAttributeFromToken(unsignedToken, SecurityGroupsNaming.REALM_ADMIN, Boolean.class))
                .orElse(false);
    }

    public <T> T getAttributeFromToken(String token, String attributeName, Class<T> tClass) {
        return Jwts.parser().unsecured().build().parseUnsecuredClaims(token).getPayload().get(attributeName, tClass);
    }

    public <T> List<T> getListFromUnsignedTokenByAttribute(String token, String attributeName, Function<String, T> parser) {
        Class<List<String>> listOfStringType = new TypeToken<List<String>>() {
        }.getRawType();

        return Optional.ofNullable(getAttributeFromToken(token, attributeName, listOfStringType))
                .orElse(new ArrayList<>())
                .stream().map(parser).collect(Collectors.toList());
    }

    public List<Long>  getListFromUnsignedToken(String token, String attributeName) {
        return getListFromUnsignedTokenByAttribute(token, attributeName, Long::parseLong);
    }

    /**
     * This function extracts the jwt from the cookies and removes the signature part.
     * Since we dont have the public key, we will be using the token without the signature part,
     * otherwise the jwtParser wont parse the token
     *
     * @param authorization The authorization header
     * @return The token without its signature
     */
    public String getUnsignedToken(String authorization) {
        String signedToken = getSignedTokenFromAuthorization(authorization);

        int i = signedToken.lastIndexOf('.');
        return signedToken.substring(0, i + 1);
    }

    public String getSignedTokenFromAuthorization(String authorization) {
        return authorization.split("Bearer ")[1];
    }

}
