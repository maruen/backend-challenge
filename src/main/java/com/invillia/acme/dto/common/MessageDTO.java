package com.invillia.acme.dto.common;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class MessageDTO {
    
    public MessageDTO() {}
    public String message;
    
    public MessageDTO(String message) {
        super();
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return String.format("MessageDTO [message=%s]", message);
    }
    
}
