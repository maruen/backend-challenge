package com.invillia.acme.exceptions;

import static com.invillia.acme.constants.ACMEConstants.INVALID_ORDER_STATUS_MSG;
import static com.invillia.acme.enums.OrderStatus.getAllOrderStatusesAsString;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

import java.util.AbstractMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Global Exception handler for all exceptions.
     */
    @ExceptionHandler
    public ResponseEntity<AbstractMap.SimpleEntry<String, String>> handle(Exception exception) {

        AbstractMap.SimpleEntry<String, String> response;

        if (exception.getMessage().contains(INVALID_ORDER_STATUS_MSG)) {
            
            String errorMessage = format("Possible values for orderStatus are: [%s].", getAllOrderStatusesAsString());

            response = new AbstractMap.SimpleEntry<>("Error: ", errorMessage);
            LOG.error(format("Error: %s", errorMessage));

        } else if (exception instanceof MissingServletRequestParameterException){
          
            response = new AbstractMap.SimpleEntry<>("Error", exception.getMessage());
            LOG.error(format("Error: %s", exception.getMessage()));
        
        } else {
        
            response = new AbstractMap.SimpleEntry<>("message", "Unable to process this request.");
            LOG.error("Exception: Unable to process this request. ", exception);
        }

        return status(BAD_REQUEST).body(response);
    }

}
