package com.weather.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCyptPasswordEncoder {

    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean  matches(String rawPassword,String encodePassword){
        return BCrypt.checkpw(rawPassword,encodePassword);
    }

}
