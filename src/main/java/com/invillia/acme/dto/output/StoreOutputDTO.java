package com.invillia.acme.dto.output;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class StoreOutputDTO {

    public StoreOutputDTO() { }

    public Long    id;
    public String  name;
    public String  address;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return String.format("StoreOutputDTO [id=%s, name=%s, address=%s]",
                id,
                name,
                address);
    }
    
    
    
    
  
}
