package com.invillia.acme.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Maruen
 *
 */

@Entity
@Table(name = "acme_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1698120863790290166L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    Order order;
    
    @Column(name = "description")
    String description;
    
    @Column(name = "unit_price")
    Float unitPrice;
    
    @Column(name = "quantity")
    Integer quantity;

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("OrderItem [id=%s, order=%s, description=%s, unitPrice=%s, quantity=%s]",
                id,
                order,
                description,
                unitPrice,
                quantity);
    }

    

}
