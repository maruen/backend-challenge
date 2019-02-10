package com.invillia.acme.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.invillia.acme.dto.output.ErrorDTO;

public class ResponseUtils {

    
    public static ResponseEntity<Object> getResponse(Object object, HttpStatus httpStatus) {
        
        Object resultObject;
        switch (httpStatus) {
        
            case BAD_REQUEST:
            case NOT_FOUND:
            case FORBIDDEN:
    
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setError(String.valueOf(object));
                resultObject = errorDTO;
                break;
                
    
            default:
                resultObject = object;
        
        }
        
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(resultObject, httpStatus);
        return responseEntity;
    }
    
}
