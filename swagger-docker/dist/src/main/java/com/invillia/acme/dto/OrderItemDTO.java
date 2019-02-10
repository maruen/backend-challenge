package com.invillia.acme.dto;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class OrderItemDTO {
    
    public OrderItemDTO() {}

    public String  description;
    public Float   unitPrice;
    public Integer quantity;
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Float getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return String.format("OrderItemDTO [description=%s, unitPrice=%s, quantity=%s]",
                description,
                unitPrice,
                quantity);
    }
    
}
