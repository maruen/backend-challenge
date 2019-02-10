package com.invillia.acme.dto.input;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class OrderItemInputDTO {
    
    public OrderItemInputDTO() {}

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
        return String.format("OrderItemInputDTO [description=%s, unitPrice=%s, quantity=%s]",
                description,
                unitPrice,
                quantity);
    }
    
}
