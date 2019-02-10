package com.invillia.acme.controller;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;

import org.junit.Assert;
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

import com.invillia.acme.dto.input.StoreInputDTO;
import com.invillia.acme.dto.output.StoreOutputDTO;
import com.invillia.acme.model.Store;
import com.invillia.acme.repositories.StoreRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StoreControllerTest {
    
    MockMvc mockMvc;
    
    
    /** USER1 DATA **/
    static final String USER1_NAME               = "APPLE STORE";
    static final String USER1_ADDRESS            = "CUPERTINO, CALIFORNIA, UNITED STATES";
    
    /** USER2 DATA **/
    static final String USER2_NAME               = "PLAY STORE";
    static final String USER2_ADDRESS            = "MOUNTAIN VIEW, CALIFORNIA, UNITED STATES";
    
    
    /** INVALID USER **/
    static final String INVALID_NAME             = "!@#$%";
    static final String INVALID_ADDRESS          = "!@#$%";
   
    
    @Autowired  private StoreRepository storeRepository;
    @Mock       private StoreController storeController;
    
    @Autowired
    private TestRestTemplate template;

    
    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();
    }
    
    @Test
    public void testSucessfullSaveStore() {


        ResponseEntity<StoreOutputDTO> response1 = null;

        try {
            
            StoreInputDTO storeInputDTO1 = new StoreInputDTO();
            storeInputDTO1.setName(USER1_NAME);
            storeInputDTO1.setAddress(USER1_ADDRESS);
            

            HttpEntity<Object> entityToPost = getHttpEntity(storeInputDTO1);
            response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);

            if (response1.getStatusCode().equals(FORBIDDEN)) {

                Optional<Store> existingStore = storeRepository.findByName(USER1_NAME);
                if (existingStore.isPresent()) {
                    storeRepository.deleteById(existingStore.get().getId());
                }
                response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);
            }

            assertEquals(CREATED , response1.getStatusCode());
            assertNotNull(response1.getBody().getId());


        } catch (Exception e) {
            fail(e.getMessage());
        } finally  {
            storeRepository.deleteById(response1.getBody().getId());
        }
    }
    
    
    
    @Test
    public void testUnsuccessfullSaveStore() {


        ResponseEntity<StoreOutputDTO> response1 = null;
        ResponseEntity<StoreOutputDTO> response2 = null;
        ResponseEntity<StoreOutputDTO> response3 = null;
        ResponseEntity<StoreOutputDTO> response4 = null;

        try {

            /**
             * 
             * STEP 1 - INSERT STORE
             * 
             */
            
            StoreInputDTO storeInputDTO1 = new StoreInputDTO();
            storeInputDTO1.setName(USER1_NAME);
            storeInputDTO1.setAddress(USER1_ADDRESS);
            

            HttpEntity<Object> entityToPost = getHttpEntity(storeInputDTO1);
            response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);

            if (response1.getStatusCode().equals(FORBIDDEN)) {

                Optional<Store> existingStore = storeRepository.findByName(USER1_NAME);
                if (existingStore.isPresent()) {
                    storeRepository.deleteById(existingStore.get().getId());
                }
                response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);
            }

            assertEquals(CREATED , response1.getStatusCode());
            assertNotNull(response1.getBody().getId());
            
            /**
             * 
             * STEP 2 - INSERT THE SAME STORE AGAIN
             * 
             */
            
            response2 = template.postForEntity("/api/store", storeInputDTO1, StoreOutputDTO.class);
            assertEquals(FORBIDDEN ,response2.getStatusCode());
            
            /**
             * 
             * STEP 3 - INSERT INVALID STORE NAME
             * 
             */
            
            StoreInputDTO storeInputDTO2 = new StoreInputDTO();
            storeInputDTO2.setName(INVALID_NAME);
            storeInputDTO2.setAddress(USER1_ADDRESS);
         
            entityToPost = getHttpEntity(storeInputDTO2);
            response3 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);
            
            assertEquals(BAD_REQUEST ,response3.getStatusCode());
            
            
            /**
             * STEP 4 - INSERT INVALID STORE ADDRESS
             * 
             */
            
            StoreInputDTO storeInputDTO3 = new StoreInputDTO();
            storeInputDTO3.setName(USER1_NAME);
            storeInputDTO3.setAddress(INVALID_ADDRESS);
         
            entityToPost = getHttpEntity(storeInputDTO3);
            response4 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);
            
            assertEquals(BAD_REQUEST ,response4.getStatusCode());
       

        } catch (Exception e) {

            fail(e.getMessage());

        } finally  {
            // cleanup the store
            storeRepository.deleteById(response1.getBody().getId());
        }
    }
    
    
    
    @Test
    public void testGetStoreById() {

       
        ResponseEntity<StoreOutputDTO> response1 = null;
        ResponseEntity<StoreOutputDTO> response2 = null;
        
        try {
            
            /**
             * 
             * STEP 1 - INSERT STORE
             * 
             */
            
            StoreInputDTO storeInputDTO1 = new StoreInputDTO();
            storeInputDTO1.setName(USER1_NAME);
            storeInputDTO1.setAddress(USER1_ADDRESS);
            

            HttpEntity<Object> entityToPost = getHttpEntity(storeInputDTO1);
            response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);

            if (response1.getStatusCode().equals(FORBIDDEN)) {

                Optional<Store> existingStore = storeRepository.findByName(USER1_NAME);
                if (existingStore.isPresent()) {
                    storeRepository.deleteById(existingStore.get().getId());
                }
                response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);
            }

            assertEquals(CREATED , response1.getStatusCode());
            assertNotNull(response1.getBody().getId());
    
            /**
             * 
             * STEP 2 - QUERY STORE BY ID
             * 
             */
    
           response2 = template.getForEntity(format("/api/store/getById/%s", 
                   response1.getBody().getId()),
                   StoreOutputDTO.class);
            
            Assert.assertEquals(OK                           , response2.getStatusCode());
            Assert.assertEquals(USER1_NAME                   , response2.getBody().getName());
            Assert.assertEquals(response1.getBody().getId()  , response2.getBody().getId());
            
        
        } catch (Exception e) {
            
            fail(e.getMessage());
            
        } finally {
            
            // cleanup the user
            storeRepository.deleteById(response1.getBody().getId());
        }

    }
    
    
    @Test
    public void testGetStoreByName() {

       
        ResponseEntity<StoreOutputDTO> response1 = null;
        ResponseEntity<StoreOutputDTO> response2 = null;
        
        try {
            
            /**
             * 
             * STEP 1 - INSERT STORE
             * 
             */
            
            StoreInputDTO storeInputDTO1 = new StoreInputDTO();
            storeInputDTO1.setName(USER1_NAME);
            storeInputDTO1.setAddress(USER1_ADDRESS);
            

            HttpEntity<Object> entityToPost = getHttpEntity(storeInputDTO1);
            response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);

            if (response1.getStatusCode().equals(FORBIDDEN)) {

                Optional<Store> existingStore = storeRepository.findByName(USER1_NAME);
                if (existingStore.isPresent()) {
                    storeRepository.deleteById(existingStore.get().getId());
                }
                response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);
            }

            assertEquals(CREATED , response1.getStatusCode());
            assertNotNull(response1.getBody().getId());
    
            /**
             * 
             * STEP 2 - QUERY STORE BY NAME
             * 
             */
    
           response2 = template.getForEntity(format("/api/store/getByName/%s", 
                   response1.getBody().getName()), StoreOutputDTO.class);
            
            Assert.assertEquals(OK          , response2.getStatusCode());
            Assert.assertEquals(USER1_NAME  , response2.getBody().getName());
            
        
        } catch (Exception e) {
            
            fail(e.getMessage());
            
        } finally {
            
            // cleanup the user
            storeRepository.deleteById(response1.getBody().getId());
        }

    }
    
    
    @Test
    public void testSucessfullUpdateStore() {


        ResponseEntity<StoreOutputDTO> response1 = null;
        
        try {

            /**
             * 
             * STEP 1 - INSERT STORE
             * 
             */
            
            StoreInputDTO storeInputDTO1 = new StoreInputDTO();
            storeInputDTO1.setName(USER1_NAME);
            storeInputDTO1.setAddress(USER1_ADDRESS);
            

            HttpEntity<Object> entityToPost = getHttpEntity(storeInputDTO1);
            response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);

            if (response1.getStatusCode().equals(FORBIDDEN)) {

                Optional<Store> existingStore = storeRepository.findByName(USER1_NAME);
                if (existingStore.isPresent()) {
                    storeRepository.deleteById(existingStore.get().getId());
                }
                response1 = template.postForEntity("/api/store", entityToPost, StoreOutputDTO.class);
            }

            assertEquals(CREATED , response1.getStatusCode());
            assertNotNull(response1.getBody().getId());
            
            /**
             * 
             *  UPDATE STORE 
             * 
            
            //TODO
              
            storeDTO1.setName("XXXX");
            storeDTO1.setAddress("YYYYYY");
           
            Store result = template.patchForObject(format("/api/store/%s",
                    String.valueOf(response1.getBody().getId())),
                    entityToPost,
                    StoreOutputDTO.class);
            
            assertEquals(USER1_NAME ,result.getName());
            assertEquals(USER2_NAME ,result.getAddress());
           
           */

        } catch (Exception e) {

            fail(e.getMessage());

        } finally  {
            // cleanup the store
            storeRepository.deleteById(response1.getBody().getId());
        }
    }
    
    

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }
    

}
