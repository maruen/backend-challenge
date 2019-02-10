package com.invillia.acme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.OrderItem;
import com.invillia.acme.repositories.OrderItemRepository;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

@Service
public class OrderItemServiceImpl implements OrderItemService {

  @Autowired
  OrderItemRepository orderItemRepository;
  
  
  @Override
  public List<OrderItem> getAll() {
    List<OrderItem> orderItemList = new ArrayList<>();
    orderItemRepository.findAll().forEach(orderItemList::add);
    return orderItemList;
    
  }
  
  public OrderItem save(OrderItem orderItem) {
    return orderItemRepository.save(orderItem);
  }

  @Override
  public Optional<OrderItem> findById(Long id) {
    Optional<OrderItem> orderItem = orderItemRepository.findById(id);
    return orderItem;
  }
  
}
