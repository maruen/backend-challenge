package com.invillia.acme.utils;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.ResponseEntity;

import com.invillia.acme.dto.ErrorDTO;

public class ResponseUtils {

    
    public static ResponseEntity<Object> getErrorResponse(String message) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError(message);
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(errorDTO, BAD_REQUEST);
        return responseEntity;
    }
    
}
