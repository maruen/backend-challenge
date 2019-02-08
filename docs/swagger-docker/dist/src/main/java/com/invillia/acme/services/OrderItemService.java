package com.invillia.acme.services;

import java.util.List;
import java.util.Optional;

import com.invillia.acme.model.OrderItem;

/**
 * StoreService interface for Stores.
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */
public interface OrderItemService {
  
  public List<OrderItem> getAll();
  
  public OrderItem               save       (OrderItem    orderItem);
  public Optional<OrderItem>    findById    (Long         id);
 
  
}
