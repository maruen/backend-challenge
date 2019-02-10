package com.invillia.acme.dto;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class StoreDTO {

    public StoreDTO() {
    }

    public String  name;
    public String  address;
    

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
        return String.format("StoreDTO [name=%s, address=%s]", name, address);
    }

  
}
