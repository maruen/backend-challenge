package com.invillia.acme.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.invillia.acme.enums.OrderStatus;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class OrderDTO {
    
    public OrderDTO() {}

    public StoreDTO             store;
    public String               address;
    public LocalDateTime        confirmationDate;
    public OrderStatus          status;
    public Long                 orderId;
    public List<OrderItemDTO>   items;
   
    public StoreDTO getStore() {
        return store;
    }
    public void setStore(StoreDTO store) {
        this.store = store;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public List<OrderItemDTO> getItems() {
        return items;
    }
    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return String.format("OrderDTO [store=%s, address=%s, confirmationDate=%s, status=%s, orderId=%s, items=%s]",
                store,
                address,
                confirmationDate,
                status,
                orderId,
                items);
    }
    
    
    
    
    
}
