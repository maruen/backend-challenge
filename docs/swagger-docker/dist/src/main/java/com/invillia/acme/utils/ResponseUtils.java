package com.invillia.acme.utils;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.invillia.acme.dto.ErrorDTO;

public class ResponseUtils {

    
    public static ResponseEntity<Object> getResponse(Object object, HttpStatus httpStatus) {
        
    
        if ( httpStatus == BAD_REQUEST ) {
            
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setError(String.valueOf(object));
            ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(errorDTO, BAD_REQUEST);
            return responseEntity;
        
        }
        
        return null;
     
    }
    
}
