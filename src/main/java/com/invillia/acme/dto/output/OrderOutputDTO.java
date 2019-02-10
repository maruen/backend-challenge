package com.invillia.acme.dto.output;

import java.util.List;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class OrderOutputDTO {
    
    public OrderOutputDTO() {}
    
    public Long                       id;
    public Long                       storeId;
    public String                     address;
    public String                     confirmationDate;
    public String                     status;
    public List<OrderItemOutputDTO>   items;
    
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
    public List<OrderItemOutputDTO> getItems() {
        return items;
    }
    public void setItems(List<OrderItemOutputDTO> items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return String.format("OrderOutputDTO [id=%s, storeId=%s, address=%s, confirmationDate=%s, status=%s, items=%s]",
                id,
                storeId,
                address,
                confirmationDate,
                status,
                items);
    }
    
    
    
    
}
