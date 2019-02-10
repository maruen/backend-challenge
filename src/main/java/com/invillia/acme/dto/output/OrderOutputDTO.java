package com.invillia.acme.dto.output;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class OrderOutputDTO {
    
    public OrderOutputDTO() {}
    
    public Long     id;
    public Long     storeId;
    public String   address;
    public String   confirmationDate;
    public String   status;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getStoreId() {
        return storeId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(String confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return String.format("OrderOutputDTO [id=%s, storeId=%s, address=%s, confirmationDate=%s, status=%s]",
                id,
                storeId,
                address,
                confirmationDate,
                status);
    }
    
    
   
    
}
