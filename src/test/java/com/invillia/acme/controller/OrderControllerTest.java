package com.invillia.acme.controller;

import static com.invillia.acme.enums.OrderStatus.PENDING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.http.HttpStatus.CREATED;

import java.time.LocalDateTime;
import java.util.LinkedList;

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

import com.invillia.acme.dto.input.OrderInputDTO;
import com.invillia.acme.dto.input.OrderItemInputDTO;
import com.invillia.acme.dto.input.StoreInputDTO;
import com.invillia.acme.dto.output.StoreOutputDTO;
import com.invillia.acme.model.Order;
import com.invillia.acme.repositories.OrderRepository;
import com.invillia.acme.repositories.StoreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
    
    MockMvc mockMvc;
    
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
    
    
   
    @Autowired  private StoreRepository  storeRepository;
    @Autowired  private OrderRepository  orderRepository;
    
    @Mock       private OrderController  orderController;
    @Autowired  private TestRestTemplate template;

    
    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }
    
    @Test
    public void testSucessfullSavePayment() {

        ResponseEntity<StoreOutputDTO> response1 = null;
        ResponseEntity<Order> response2 = null;

        try {

            /**
             * 
             * STEP 1 - INSERT STORE
             * 
             */
            
            StoreInputDTO storeInputDTO1 = new StoreInputDTO();
            storeInputDTO1.setName(USER_NAME);
            storeInputDTO1.setAddress(USER_ADDRESS);
            

            HttpEntity<Object> entityToPost = getHttpEntity(storeInputDTO1);
            response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);

            assertEquals(CREATED  , response1.getStatusCode());
            assertNotNull(response1.getBody().getId());
            
            /**
             * 
             * STEP 2 - INSERT ORDER
             * 
             */
            
            OrderInputDTO orderInputDTO = new OrderInputDTO();
            orderInputDTO.setStoreId(response1.getBody().getId());
            orderInputDTO.setAddress(ORDER_ADDRESS);
            orderInputDTO.setConfirmationDate( LocalDateTime.now());
            orderInputDTO.setStatus(PENDING);
            
            LinkedList<OrderItemInputDTO> items = new LinkedList<OrderItemInputDTO>();
            OrderItemInputDTO orderItemDTO1 = new OrderItemInputDTO();
            orderItemDTO1.setDescription(DESCRIPTION1);
            orderItemDTO1.setUnitPrice(UNIT_PRICE1);
            orderItemDTO1.setQuantity(QUANTITY1);
            
            OrderItemInputDTO orderItemDTO2 = new OrderItemInputDTO();
            orderItemDTO2.setDescription(DESCRIPTION2);
            orderItemDTO2.setUnitPrice(UNIT_PRICE2);
            orderItemDTO2.setQuantity(QUANTITY2);
            
            items.add(orderItemDTO1);
            items.add(orderItemDTO2);
            
            orderInputDTO.setItems(items);
            
            HttpEntity<Object> order = getHttpEntity(orderInputDTO);
            response2 = template.postForEntity("/api/order", order, Order.class);
            
            assertEquals(CREATED,response2.getStatusCode());
            assertNotNull(response2.getBody().getId());
            assertNotNull(orderRepository.findById(response2.getBody().getId())) ;
            

        } catch (Exception e) {

            fail(e.getMessage());

        } finally  {
            /**
             * WHEN DELETING STORE, THE ORDER AND THE ORDER ITEMS ARE AUTOMATICALLY DELETED
             * BECAUSE THE CASCADE MODE WAS CONFIGURED
             **/
         
            storeRepository.deleteById(response1.getBody().getId());
          
            
        }
    }
    
    
    // @Test
    // TODO
    public void testUnsuccessfullSaveOrder() {

        try {

        } catch (Exception e) {

            fail(e.getMessage());

        } finally {
        }
    }
    
    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }
    

}
