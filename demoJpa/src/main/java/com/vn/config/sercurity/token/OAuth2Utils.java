//package com.vn.config.sercurity.token;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Set;
//import java.util.TreeSet;
//import org.springframework.util.StringUtils;
//
//public abstract class OAuth2Utils {
//    public static final String CLIENT_ID = "client_id";
//    public static final String STATE = "state";
//    public static final String SCOPE = "scope";
//    public static final String REDIRECT_URI = "redirect_uri";
//    public static final String RESPONSE_TYPE = "response_type";
//    public static final String USER_OAUTH_APPROVAL = "user_oauth_approval";
//    public static final String SCOPE_PREFIX = "scope.";
//    public static final String GRANT_TYPE = "grant_type";
//
//    public OAuth2Utils() {
//    }
//
//    public static Set<String> parseParameterList(String values) {
//        Set<String> result = new TreeSet();
//        if (values != null && values.trim().length() > 0) {
//            String[] tokens = values.split("[\\s+]");
//            result.addAll(Arrays.asList(tokens));
//        }
//
//        return result;
//    }
//
//    public static String formatParameterList(Collection<String> value) {
//        return value == null ? null : StringUtils.collectionToDelimitedString(value, " ");
//    }
//
//    public static Map<String, String> extractMap(String query) {
//        Map<String, String> map = new HashMap();
//        Properties properties = StringUtils.splitArrayElementsIntoProperties(StringUtils.delimitedListToStringArray(query, "&"), "=");
//        if (properties != null) {
//            Iterator var4 = properties.keySet().iterator();
//
//            while(var4.hasNext()) {
//                Object key = var4.next();
//                map.put(key.toString(), properties.get(key).toString());
//            }
//        }
//
//        return map;
//    }
//
//    public static boolean containsAll(Set<String> target, Set<String> members) {
//        target = new HashSet(target);
//        target.retainAll(members);
//        return target.size() == members.size();
//    }
//}
