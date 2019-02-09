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

import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.dto.ErrorDTO;
import com.invillia.acme.dto.PaymentDTO;
import com.invillia.acme.model.Order;
import com.invillia.acme.model.Payment;
import com.invillia.acme.model.Store;
import com.invillia.acme.services.OrderService;
import com.invillia.acme.services.PaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * PaymentController for Payment related APIs.
 * @author Maruen Mehana <maruen@gmail.com>
 */


@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Payment Services")
@Path("/")
@RestController
public class PaymentController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private PaymentService paymentService;
    
    
    
    /** SAVE PAYMENT SWAGGER ANNOTATIONS **/
    @POST
    @Path("/api/payment")
    
    @Produces({ APPLICATION_JSON })
    @Consumes({ APPLICATION_JSON })
    
    @ApiOperation(  notes         = "Save payment",
                    response      = Payment.class,
                    produces      = APPLICATION_JSON,
                    value         = EMPTY)
   
    @ApiResponses(
            value = {
          @ApiResponse(code       = 201,
                       message    = "Payment successfully saved",
                       response   = Payment.class)
                    
                     })
    /** END SWAGGER ANNOTATIONS **/
    
    
   
    @PostMapping(path = "/api/payment")
    public ResponseEntity<Object> savePayment(@RequestBody PaymentDTO paymentDTO) {
        
        if (!Optional.ofNullable(paymentDTO).isPresent())  {
            return getResponse("Payment object is empty or null", BAD_REQUEST);
        }
        
        Optional<Long> orderId =  Optional.ofNullable(paymentDTO.getOrderId());
        if (!orderId.isPresent())  {
            return getResponse("Order id is empty or null", BAD_REQUEST);
        }
        
        Optional<Long> creditCardNumber =  Optional.ofNullable(paymentDTO.getCreditCardNumber());
        if (!creditCardNumber.isPresent())  {
            return getResponse("Credit card number is empty or null", BAD_REQUEST);
        }
        
        String creditCardNumberAsString = String.valueOf(creditCardNumber.get().longValue());
        if ( creditCardNumberAsString.length() != 16 )  {
            return getResponse("Invalid credit card number ", BAD_REQUEST);
        }
        
        Optional<Order> order;
        order = orderService.findById(orderId.get());
        if (!order.isPresent()) {
            return getResponse("Order id is empty or null", NOT_FOUND);
        }
        
        Payment newPayment = new Payment();
        newPayment.setStatus(paymentDTO.getStatus());
        newPayment.setCreditCardNumber(paymentDTO.getCreditCardNumber());
        newPayment.setPaymentDate(paymentDTO.getPaymentDate());
        
        paymentService.save(newPayment);
        
        
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(newPayment,CREATED);
        return responseEntity;
    }

   
    
    
}
