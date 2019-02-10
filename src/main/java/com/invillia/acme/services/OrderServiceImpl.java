package com.invillia.acme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Order;
import com.invillia.acme.repositories.OrderRepository;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired OrderRepository orderRepository;
  
  
  @Override
  public List<Order> getAll() {
    List<Order> orderList = new ArrayList<>();
    orderRepository.findAll().forEach(orderList::add);
    return orderList;
    
  }
  
  public Order save(Order order) {
    return orderRepository.save(order);
  }

  @Override
  public Optional<Order> findById(Long id) {
    Optional<Order> order = orderRepository.findById(id);
    return order;
  }
  
}
