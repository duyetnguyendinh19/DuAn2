//package com.vn.config.sercurity.token;
//
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.OAuth2Request;
//import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
//
//import java.io.UnsupportedEncodingException;
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class SingleAuthenticationKeyGenerator implements AuthenticationKeyGenerator {
//
//    private static final String CLIENT_ID = "client_id";
//    private static final String SCOPRE = "scope";
//    private static final String USERNAME = "username";
//
//    @Override
//    public String extractKey(OAuth2Authentication oAuth2Authentication) {
//        Map<String, String> values = new LinkedHashMap<>();
//        values.put("uuid", UUID.randomUUID().toString());
//        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
//        if (!oAuth2Authentication.isClientOnly()) {
//            values.put(USERNAME, oAuth2Authentication.getName());
//        }
//        values.put(CLIENT_ID, oAuth2Request.getClientId());
//        if (oAuth2Request.getScope() != null) {
//            values.put(SCOPRE, OAuth2Utils.formatParameterList(oAuth2Request.getScope()));
//        }
//        MessageDigest messageDigest;
//        try {
//            messageDigest = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
//        }
//
//        try {
//            byte[] bytes = messageDigest.digest(values.toString().getBytes("UTF-8"));
//            return String.format("%032s", new BigInteger(1, bytes));
//        } catch (UnsupportedEncodingException e) {
//            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
//        }
//    }
//}
