package com.invillia.acme.utils;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",  CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    
    public static final Pattern VALID_NAME_REGEX = 
            Pattern.compile("^[A-Z]{2,100}+",  CASE_INSENSITIVE);
    
    public static final Pattern VALID_ADDRESS_REGEX = 
            Pattern.compile("^[a-zA-Z0-9_.,-]+( [a-zA-Z0-9_.,-]+)*$");
    

    public static boolean validateName(String name) {
        
        if (name.length() > 100 ) {
            return false;
        }
        
         Matcher matcher = VALID_NAME_REGEX.matcher(name);
         boolean result  = matcher.find();
         return result;
    }
    
    
    public static boolean validateAddress(String address) {

        if (address.length() > 255 ) {
            return false;
        }

        Matcher matcher = VALID_ADDRESS_REGEX.matcher(address);
        boolean result  = matcher.find();
        return result;
    }

    

}
