package com.weather.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

public class BCryptPasswordEncoder {

    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean  matches(String rawPassword,String encodePassword){
        return BCrypt.checkpw(rawPassword,encodePassword);
    }
}
