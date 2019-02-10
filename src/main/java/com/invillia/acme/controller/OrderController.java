/**
 * 
 */
package com.invillia.acme.controller;

import static com.invillia.acme.utils.ResponseUtils.getResponse;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import com.invillia.acme.dto.common.ErrorDTO;
import com.invillia.acme.dto.common.MessageDTO;
import com.invillia.acme.dto.input.OrderInputDTO;
import com.invillia.acme.dto.input.OrderItemInputDTO;
import com.invillia.acme.dto.output.OrderItemOutputDTO;
import com.invillia.acme.dto.output.OrderOutputDTO;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.enums.PaymentStatus;
import com.invillia.acme.model.Order;
import com.invillia.acme.model.OrderItem;
import com.invillia.acme.model.Payment;
import com.invillia.acme.model.Store;
import com.invillia.acme.services.OrderItemService;
import com.invillia.acme.services.OrderService;
import com.invillia.acme.services.PaymentService;
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
    @Autowired private PaymentService   paymentService;
    
    
    /** SAVE ORDER SWAGGER ANNOTATIONS **/
    @POST
    @Path("/api/order")
    
    @Produces({ APPLICATION_JSON })
    @Consumes({ APPLICATION_JSON })
    
    @ApiOperation(  notes         = "Save order",
                    response      = OrderOutputDTO.class,
                    produces      = APPLICATION_JSON,
                    value         = EMPTY)
   
    @ApiResponses(
          value = {
          @ApiResponse(code  = 201, message  = "Order successfully saved", response  = OrderOutputDTO.class),
          @ApiResponse(code  = 400, message  = "Bad request",              response  = ErrorDTO.class),
          @ApiResponse(code  = 404, message  = "Store not found",          response  = ErrorDTO.class) 
    })
    /** END SWAGGER ANNOTATIONS **/
    
    
   
    @PostMapping(path = "/api/order")
    public ResponseEntity<Object> saveOrder(@RequestBody OrderInputDTO orderInputDTO) {
        
        
        Optional<Long> storeId =  Optional.ofNullable(orderInputDTO.getStoreId());
        if (!storeId.isPresent())  {
            return status(BAD_REQUEST).build();
        }
        
        Optional<Store> store;
        store = storeService.findById(storeId.get());
        if (!store.isPresent()) {
            return getResponse("Store not found", NOT_FOUND);
        }
        
        Order newOrder = new Order();
        newOrder.setAddress(orderInputDTO.getAddress());
        newOrder.setConfirmationDate(orderInputDTO.getConfirmationDate());
        newOrder.setStatus(OrderStatus.valueOf(orderInputDTO.getStatus().toString()));
        newOrder.setStore(store.get());
        orderService.save(newOrder);
        
        
        List<OrderItemOutputDTO> items = new LinkedList<OrderItemOutputDTO>();
        for (OrderItemInputDTO orderItemDTO : orderInputDTO.getItems() ) {
            
            OrderItem orderItem = new OrderItem();
            orderItem.setDescription(orderItemDTO.getDescription());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setUnitPrice(orderItemDTO.getUnitPrice());
            orderItem.setOrder(newOrder);
            orderItemService.save(orderItem);
            
            OrderItemOutputDTO orderItemOutputDTO = new OrderItemOutputDTO();
            orderItemOutputDTO.setDescription(orderItem.getDescription());
            orderItemOutputDTO.setQuantity(orderItem.getQuantity());
            orderItemOutputDTO.setUnitPrice(orderItem.getUnitPrice());
            
            items.add(orderItemOutputDTO);
        }
        
        OrderOutputDTO orderOutputDTO = new OrderOutputDTO();

        orderOutputDTO.setId(newOrder.getId());
        orderOutputDTO.setAddress(newOrder.getAddress());
        orderOutputDTO.setConfirmationDate(String.valueOf(newOrder.getConfirmationDate()));
        orderOutputDTO.setStoreId(newOrder.getStore().getId());
        orderOutputDTO.setStatus(String.valueOf(newOrder.getStatus()));
        orderOutputDTO.setItems(items);
       
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(orderOutputDTO,CREATED);
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
          @ApiResponse(code  = 200, message  = "Returns the order",          response  = OrderOutputDTO.class),
          @ApiResponse(code  = 404, message  = "The order id was not found", response  = ErrorDTO.class)
    })
    /** END SWAGGER ANNOTATIONS **/
    
    @GetMapping(path = "/api/order/getById/{order-id}")
    
    public ResponseEntity<Object> getOrderById(
            
            /** SWAGGER ANNOTATIONS **/
            @ApiParam    (value = "Order Id"     , required = true)   
            @PathParam   (value = "order-id")             
            
            @PathVariable(name  = "order-id"     , required = true) Long orderId )  {
        
        
        Optional<Order> order = orderService.findById(orderId);
        if (order.isPresent()) {

            OrderOutputDTO orderOutputDTO = new OrderOutputDTO();

            orderOutputDTO.setId(order.get().getId());
            orderOutputDTO.setAddress(order.get().getAddress());
            orderOutputDTO.setConfirmationDate(String.valueOf(order.get().getConfirmationDate()));
            orderOutputDTO.setStoreId(order.get().getStore().getId());
            orderOutputDTO.setStatus(String.valueOf(order.get().getStatus()));
           
            ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(orderOutputDTO,OK);
            return responseEntity;
        }

        return getResponse("The order id was not found", NOT_FOUND);
    }
    
    
    
    
    
    /** REFUND ORDER SWAGGER ANNOTATIONS **/
    @POST
    @Path("/api/order/refund/{order-id}")
    
    @Produces({ APPLICATION_JSON })
    
    @ApiOperation(  notes         = "Refund order",
                    response      = MessageDTO.class,
                    produces      = APPLICATION_JSON,
                    value         = EMPTY)
   
    @ApiResponses(
          value = {
          @ApiResponse(code  = 200, message  = "Order successfully refunded"        , response  = MessageDTO.class),
          @ApiResponse(code  = 403, message  = "Order not allowed to be refunded"   , response  = MessageDTO.class),
          @ApiResponse(code  = 404, message  = "The order id was not found"         , response  = ErrorDTO.class) 
    })
    /** END SWAGGER ANNOTATIONS **/
    
    
   
    @PostMapping(path = "/api/order/refund/{order-id}")
    public ResponseEntity<Object> refundOrder(
            /** SWAGGER ANNOTATIONS **/
            @ApiParam    (value = "Order Id"     , required = true)   
            @PathParam   (value = "order-id")             
            
            @PathVariable(name  = "order-id"     , required = true) Long orderId )  {
     
        Optional<Order> order;
        order = orderService.findById(orderId);
        if (!order.isPresent()) {
            MessageDTO messageDTO                   = new MessageDTO("Order id was not found");
            ResponseEntity<Object> responseEntity   = new ResponseEntity<Object>(messageDTO, NOT_FOUND);
            return responseEntity;
        }

        Optional<Payment> payment = paymentService.findByOrderId(order.get().getId());
        if (!payment.isPresent()) {
            MessageDTO messageDTO                   = new MessageDTO("There was no payment for this order");
            ResponseEntity<Object> responseEntity   = new ResponseEntity<Object>(messageDTO, NOT_FOUND);
            return responseEntity;
        }
            
        LocalDateTime currentDate       = LocalDateTime.now();
        LocalDateTime refundDateLimit   = order.get().getConfirmationDate().plusDays(10);
        
        if ( OrderStatus.COMPLETED.equals(order.get().getStatus())                  && 
             PaymentStatus.COMPLETED.equals(payment.get().getStatus())              &&
             currentDate.isBefore(refundDateLimit) ) {
            
            Order orderToRefund = order.get();
            orderToRefund.setStatus(OrderStatus.REFUNDED);
            orderService.save(orderToRefund);
            
            Payment paymentToRefund = payment.get();
            paymentToRefund.setStatus(PaymentStatus.REFUNDED);
            paymentService.save(paymentToRefund);
          
            MessageDTO messageDTO                   = new MessageDTO("Order sucessfully refunded");
            ResponseEntity<Object> responseEntity   = new ResponseEntity<Object>(messageDTO, OK);
            return responseEntity;

        } else {

            MessageDTO messageDTO                   = new MessageDTO("Order not allowed to be refunded");
            ResponseEntity<Object> responseEntity   = new ResponseEntity<Object>(messageDTO, FORBIDDEN);
            return responseEntity;
        }
       
    }
    
    
}
