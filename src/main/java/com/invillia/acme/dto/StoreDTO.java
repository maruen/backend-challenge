package com.invillia.acme.dto;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class StoreDTO {

    public StoreDTO() {
    }

    public String  name;
    public Long    id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("StoreDTO [name=%s, id=%s]", name, id);
    }
    
    

  
}
