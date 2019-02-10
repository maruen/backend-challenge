/**
 * 
 */
package com.invillia.acme.controller;

import static com.invillia.acme.utils.ResponseUtils.getResponse;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.dto.OrderDTO;
import com.invillia.acme.dto.OrderItemDTO;
import com.invillia.acme.dto.output.DefaultOutputDTO;
import com.invillia.acme.dto.output.OrderOutputDTO;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.model.Order;
import com.invillia.acme.model.OrderItem;
import com.invillia.acme.model.Store;
import com.invillia.acme.services.OrderItemService;
import com.invillia.acme.services.OrderService;
import com.invillia.acme.services.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * OrderController for Order related APIs.
 * @author Maruen Mehana <maruen@gmail.com>
 */


@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Order Services")
@Path("/")
@RestController
public class OrderController {

    @Autowired private StoreService     storeService;
    @Autowired private OrderService     orderService;
    @Autowired private OrderItemService orderItemService;
    
   
    @PostMapping(path = "/api/order")
    public ResponseEntity<Object> saveOrder(@RequestBody OrderDTO orderDTO) {
        
        
        Optional<Long> storeId =  Optional.ofNullable(orderDTO.getStoreId());
        if (!storeId.isPresent())  {
            return status(BAD_REQUEST).build();
        }
        
        Optional<Store> store;
        store = storeService.findById(storeId.get());
        if (!store.isPresent()) {
            return status(NOT_FOUND).build();
        }
        
        Order newOrder = new Order();
        newOrder.setAddress(orderDTO.getAddress());
        newOrder.setConfirmationDate(orderDTO.getConfirmationDate());
        newOrder.setStatus(OrderStatus.valueOf(orderDTO.getStatus().toString()));
        newOrder.setStore(store.get());
        orderService.save(newOrder);
        
        for (OrderItemDTO orderItemDTO : orderDTO.getItems() ) {
            
            OrderItem orderItem = new OrderItem();
            orderItem.setDescription(orderItemDTO.getDescription());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
            orderItem.setOrder(newOrder);
            orderItemService.save(orderItem);
       
        }
        
        DefaultOutputDTO defaultOutputDTO      = new DefaultOutputDTO(newOrder.getId());
        ResponseEntity<Object> responseEntity  = new ResponseEntity<Object>(defaultOutputDTO,CREATED);
        return responseEntity;
    }
    
    
    /** GET ORDER SWAGGER ANNOTATIONS **/
    @GET
    @Path("/api/order/getById/{order-id}")
    @Produces({ APPLICATION_JSON })
    
    @ApiOperation(  notes         = "Get order by id",
                    response      = OrderOutputDTO.class,
                    produces      = APPLICATION_JSON,
                    value         = EMPTY)
   
    @ApiResponses(
            value = {
          @ApiResponse(code       = 200,
                       message    = "Returns the order",
                       response   = OrderOutputDTO.class),
          
          @ApiResponse(code       = 404,
                      message     = "The order id was not found",
                      response    = String.class)
          
                    
                     })
    /** END SWAGGER ANNOTATIONS **/
    
    
    @GetMapping(path = "/api/order/getById/{order-id}")
    
    public ResponseEntity<Object> getOrderById(
            
            /** SWAGGER ANNOTATIONS **/
            @ApiParam    (value = "The order Id"     , required = true)   
            @PathParam   (value = "order-id")             
            
            @PathVariable(name  = "order-id"         , required = true) Long orderId )  {
        
        
        Optional<Order> order = orderService.findById(orderId);
        if (order.isPresent()) {

            OrderOutputDTO orderOutputDTO = new OrderOutputDTO();

            orderOutputDTO.setId(order.get().getId());
            orderOutputDTO.setAddress(order.get().getAddress());
            orderOutputDTO.setConfirmationDate(String.valueOf(order.get().getConfirmationDate()));
            orderOutputDTO.setStoreId(order.get().getStore().getId());
            orderOutputDTO.setStatus(String.valueOf(order.get().getStatus()))
            ;
            ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(orderOutputDTO,OK);
            return responseEntity;
        }

        return getResponse("The order id was not found", NOT_FOUND);
    }
    
    

   
    
    
}
