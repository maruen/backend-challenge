package com.invillia.acme.dto.output;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class DefaultOutputDTO {
    
    public DefaultOutputDTO() {}
    public Long id;
    
    public DefaultOutputDTO(Long id) {
        super();
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return String.format("DefaultOutputDTO [id=%s]", id);
    }
    
}
