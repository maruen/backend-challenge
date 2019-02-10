package com.invillia.acme.services;

import java.util.List;
import java.util.Optional;

import com.invillia.acme.model.Payment;

/**
 * PaymentService interface for Payments.
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */
public interface PaymentService {
  
  public List<Payment> getAll();
  
  public Payment             save           (Payment    payment);
  public Optional<Payment>   findById       (Long       id);
  public Optional<Payment>   findByOrderId  (Long       orderId);
  
  
}
