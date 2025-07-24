package com.trackit.authservice.utils;


import com.trackit.authservice.model.UserInfoDto;

import java.util.regex.Pattern;

public class Validation {

    final static String emailRegEx = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    final static String passwordRegEx = "^(?=.*[0-9])" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#$%^&+=!])" +
            "(?=\\S+$).{8,}$";

    public static Boolean validateUser(UserInfoDto user) {
        if(user.getEmail() != null && isValidEmail(user.getEmail())) {
            return user.getPassword() != null && isValidPassword(user.getPassword());
        }
        return false;
    }

    public static Boolean isValidEmail(String email) {
        return Pattern.compile(emailRegEx)
                .matcher(email)
                .matches();
    }

    public static Boolean isValidPassword(String password) {
        return Pattern.compile(passwordRegEx)
                .matcher(password)
                .matches();
    }
}

