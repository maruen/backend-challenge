/**
 * 
 */
package com.invillia.acme.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.dto.OrderDTO;
import com.invillia.acme.dto.OrderItemDTO;
import com.invillia.acme.dto.StoreDTO;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.model.Order;
import com.invillia.acme.model.OrderItem;
import com.invillia.acme.model.Store;
import com.invillia.acme.services.OrderItemService;
import com.invillia.acme.services.OrderService;
import com.invillia.acme.services.StoreService;


/**
 * StoreController for Store related APIs.
 * @author Maruen Mehana <maruen@gmail.com>
 */


@RestController
public class OrderController {

    @Autowired
    private StoreService storeService;

    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderItemService orderItemService;
    
   
    @PostMapping(path = "/api/order")
    public ResponseEntity<Order> saveOrder(@RequestBody OrderDTO orderDTO) {
        
        
        Optional<StoreDTO> storeDTO =  Optional.ofNullable(orderDTO.getStore());
        if (!storeDTO.isPresent()) {
            return status(BAD_REQUEST).build();
        } 
          
        Optional<Long>   storeId      =  Optional.ofNullable(storeDTO.get().getId());
        Optional<String> storeName    =  Optional.ofNullable(storeDTO.get().getName());
        
        if (!storeId.isPresent() && !storeName.isPresent())  {
            return status(BAD_REQUEST).build();
        }
        
        Optional<Store> store;
        if (storeId.isPresent()) {
            store = storeService.findById(storeId.get());
            if (!store.isPresent()) {
                return status(NOT_FOUND).build();
            }
        } else {
            store = storeService.findByName(storeName.get());
            if (!store.isPresent()) {
                return status(NOT_FOUND).build();
            }
        }
        
        Order order = new Order();
        order.setAddress(orderDTO.getAddress());
        order.setConfirmationDate(orderDTO.getConfirmationDate());
        order.setStatus(OrderStatus.valueOf(orderDTO.getStatus().toString()));
        order.setStore(store.get());
        
        LinkedList<OrderItem> items = new LinkedList<OrderItem>();
        for (OrderItemDTO orderItemDTO : orderDTO.getItems() ) {
            
            OrderItem orderItem = new OrderItem();
            orderItem.setDescription(orderItemDTO.getDescription());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
            
            orderItemService.save(orderItem);
            items.add(orderItem);
        }
        
        
        order.setItems(items);
        orderService.save(order);
        
        ResponseEntity<Order> responseEntity = new ResponseEntity<Order>(order,CREATED);
        return responseEntity;
    }

   
    
    
}
