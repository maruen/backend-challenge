package com.invillia.acme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Payment;
import com.invillia.acme.repositories.PaymentRepository;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

@Service
public class PaymentServiceImpl implements PaymentService {

  @Autowired PaymentRepository paymentRepository;
  
  
  @Override
  public List<Payment> getAll() {
    List<Payment> paymentList = new ArrayList<>();
    paymentRepository.findAll().forEach(paymentList::add);
    return paymentList;
  }
  
  public Payment save(Payment payment) {
    return paymentRepository.save(payment);
  }

  @Override
  public Optional<Payment> findById(Long id) {
    Optional<Payment> payment = paymentRepository.findById(id);
    return payment;
  }

  @Override
  public Optional<Payment> findByOrderId(Long orderId) {
      Optional<Payment> payment = paymentRepository.findByOrderId(orderId);
      return payment;
  }
  
}
