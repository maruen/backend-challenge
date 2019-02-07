package com.invillia.acme.services;

import java.util.List;
import java.util.Optional;

import com.invillia.acme.model.Order;

/**
 * StoreService interface for Stores.
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */
public interface OrderService {
  
  public List<Order> getAll();
  
  public Order           save       (Order    order);
  public Optional<Order> findById   (Long     id);
 
  
}
