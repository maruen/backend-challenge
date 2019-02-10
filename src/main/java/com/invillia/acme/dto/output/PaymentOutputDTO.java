package com.invillia.acme.dto.output;

import java.time.LocalDateTime;

import com.invillia.acme.enums.PaymentStatus;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class PaymentOutputDTO {
    
    public PaymentOutputDTO() {}

    
    public Long                 id;
    public Long                 orderId;
    public PaymentStatus        status;
    public Long                 creditCardNumber;
    public LocalDateTime        paymentDate;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    @Override
    public String toString() {
        return String.format("PaymentOutputDTO [id=%s, orderId=%s, status=%s, creditCardNumber=%s, paymentDate=%s]",
                id,
                orderId,
                status,
                creditCardNumber,
                paymentDate);
    }
    
}
