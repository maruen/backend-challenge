package com.invillia.acme.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.invillia.acme.model.OrderItem;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

@RestResource(exported = false)
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
   

}
