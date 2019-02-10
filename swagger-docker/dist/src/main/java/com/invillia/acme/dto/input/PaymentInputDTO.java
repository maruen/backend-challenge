package com.invillia.acme.dto.input;

import java.time.LocalDateTime;

import com.invillia.acme.enums.PaymentStatus;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

public class PaymentInputDTO {
    
    public PaymentInputDTO() {}

    public Long                 orderId;
    public PaymentStatus        status;
    public Long                 creditCardNumber;
    public LocalDateTime        paymentDate;
    
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
        return String.format("PaymentInputDTO [orderId=%s, status=%s, creditCardNumber=%s, paymentDate=%s]",
                orderId,
                status,
                creditCardNumber,
                paymentDate);
    }
    
    
    
    
}
