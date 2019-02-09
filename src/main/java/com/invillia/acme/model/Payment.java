package com.invillia.acme.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.invillia.acme.enums.PaymentStatus;

/**
 * @author Maruen
 *
 */

@Entity
@Table(name = "acme_payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1698120863790290166L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @Enumerated(EnumType.STRING)
    PaymentStatus status;
    
    @Column(name="credit_card_number")
    Long creditCardNumber;
    
    
    @Column(name = "payment_date")
    LocalDateTime paymentDate;
    
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return String.format("Payment [id=%s, status=%s, creditCardNumber=%s, paymentDate=%s, order=%s]",
                id,
                status,
                creditCardNumber,
                paymentDate,
                order);
    }
    
    
   
    
}
