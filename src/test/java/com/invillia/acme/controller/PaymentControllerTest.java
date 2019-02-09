package com.invillia.acme.controller;

import static com.invillia.acme.enums.OrderStatus.PENDING;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.invillia.acme.dto.OrderDTO;
import com.invillia.acme.dto.OrderItemDTO;
import com.invillia.acme.model.Order;
import com.invillia.acme.model.Store;
import com.invillia.acme.repositories.StoreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {
    
    MockMvc mockMvc;
    
    String INSERT_STORE_MESSAGE_PATTERN     =    
    "{"                                                                                 +
            "\"name\"               : \"%s\"    ,"                                      +
            "\"address\"            : \"%s\"    "                                       + 
    "}";
    
    
    String INSERT_ORDER_MESSAGE_PATTERN   =      
    "{"                                                                                 +
        "\"storeId\"                :   %n      ,"                                      +
        "\"address\"                :  \"%s\"   ,"                                      +
        "\"confirmationDate\"       :  \"%s\"   ,"                                      +
        "\"status\"                 :  \"%s\"   ,"                                      +
        "\"items\"                  :   [        "                                      +
        "                                {       "                                      +
        "                                    \"description\"      : \"%s\"  ,"          +
        "                                    \"unitPrice\"        :  %n     ,"          +
        "                                    \"quantity\"         :  %n      "          +
        "                                },                                  "          +
        "                                {                                   "          +
        "                                    \"description\"      : \"%s\"  ,"          +
        "                                    \"unitPrice\"        :  %n     ,"          +
        "                                    \"quantity\"         :  %n     ,"          +
        "                                }                                   "          +
        "                               ]                                    "          +
    "}";
    
    String INSERT_PAYMENT_MESSAGE_PATTERN   =      
    "{"                                                                                 +
        "\"orderId\"                : %n        ,"                                      +
        "\"status\"                 : \"%s\"    ,"                                      +
        "\"creditCardNumber\"       : %n        ,"                                      +
        "\"paymentDate\"            : \"%s\"     "                                      +
    "}";
    
    
    /** STORE DATA **/
    static final String  USER_NAME                = "APPLE STORE";
    static final String  USER_ADDRESS             = "CUPERTINO, CALIFORNIA, UNITED STATES";
    
    /** ORDER DATA **/
    static final String  ORDER_ADDRESS            = "ORDER SITE ADDRESS";
    
    /** ORDER ITEM DATA **/
    static final String  DESCRIPTION1             = "JOSEPH'S HISTORY BOOK";
    static final Float   UNIT_PRICE1              = 29.99F;
    static final Integer QUANTITY1                = 1;
    
    static final String  DESCRIPTION2             = "JOHN'S HISTORY BOOK";
    static final Float   UNIT_PRICE2              = 39.99F;
    static final Integer QUANTITY2                = 2;
    
    
   
    @Autowired
    StoreRepository storeRepository;
    
    @Autowired
    StoreRepository orderRepository;
    
    @Mock
    private OrderController orderController;
    
    @Autowired
    private TestRestTemplate template;

    
    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }
    
    @Test
    public void testSucessfullSavePayment() {

        ResponseEntity<Store> response1 = null;
        ResponseEntity<Order> response2 = null;

        try {

            /**
             * 
             * STEP 1 - INSERT STORE
             * 
             */
            
            
            HttpEntity<Object> store = getHttpEntity(format(INSERT_STORE_MESSAGE_PATTERN, 
                    USER_NAME,
                    USER_ADDRESS));

            response1 = template.postForEntity("/api/store", store, Store.class);

            if (response1.getStatusCode().equals(FORBIDDEN)) {

                Optional<Store> existingStore = storeRepository.findByName(USER_NAME);

                if (existingStore.isPresent()) {
                    storeRepository.deleteById(existingStore.get().getId());
                }
                response1 = template.postForEntity("/api/store", store, Store.class);
            }

            assertEquals(CREATED    , response1.getStatusCode());
            assertEquals(USER_NAME  , response1.getBody().getName());
            assertNotNull(response1.getBody().getId());
            
            /**
             * 
             * STEP 2 - INSERT ORDER
             * 
             */
            
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setStoreId(response1.getBody().getId());
            orderDTO.setAddress(ORDER_ADDRESS);
            orderDTO.setConfirmationDate( LocalDateTime.now());
            orderDTO.setStatus(PENDING);
            
            LinkedList<OrderItemDTO> items = new LinkedList<OrderItemDTO>();
            OrderItemDTO orderItemDTO1 = new OrderItemDTO();
            orderItemDTO1.setDescription(DESCRIPTION1);
            orderItemDTO1.setUnitPrice(UNIT_PRICE1);
            orderItemDTO1.setQuantity(QUANTITY1);
            
            OrderItemDTO orderItemDTO2 = new OrderItemDTO();
            orderItemDTO2.setDescription(DESCRIPTION2);
            orderItemDTO2.setUnitPrice(UNIT_PRICE2);
            orderItemDTO2.setQuantity(QUANTITY2);
            
            items.add(orderItemDTO1);
            items.add(orderItemDTO2);
            
            orderDTO.setItems(items);
            
            HttpEntity<Object> order = getHttpEntity(orderDTO);
            response2 = template.postForEntity("/api/order", order, Order.class);
            
            assertEquals(CREATED,response2.getStatusCode());
            assertNotNull(response2.getBody().getId());
   

        } catch (Exception e) {

            fail(e.getMessage());

        } finally  {
            /**
             * WHEN DELETING STORE, THE ORDER AND THE ORDERM ITEMS ARE AUTOMATICALLY DELETED
             * BECAUSE THE CASCADE MODE WAS CONFIGURED
             **/
         
            storeRepository.deleteById(response1.getBody().getId());
          
            
        }
    }
    
    
    
    //@Test
    //TODO
    public void testUnsuccessfullSavePayment() {

        //ResponseEntity<Store> response1 = null;
     
        try {

        } catch (Exception e) {

            fail(e.getMessage());

        } finally  {
            // cleanup the store
            //storeRepository.deleteById(response1.getBody().getId());
        }
    }
    
    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }
    

}
