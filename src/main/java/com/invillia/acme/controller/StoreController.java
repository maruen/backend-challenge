/**
 * 
 */
package com.invillia.acme.controller;

import static com.invillia.acme.utils.ResponseUtils.getResponse;
import static com.invillia.acme.utils.ValidationUtils.validateAddress;
import static com.invillia.acme.utils.ValidationUtils.validateName;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

import java.util.List;
import java.util.NoSuchElementException;
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
import com.invillia.acme.dto.input.StoreInputDTO;
import com.invillia.acme.dto.output.StoreOutputDTO;
import com.invillia.acme.model.Store;
import com.invillia.acme.services.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



/**
 * StoreController for Store related APIs.
 * @author Maruen Mehana <maruen@gmail.com>
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Store Services")
@Path("/")
@RestController
public class StoreController {

    @Autowired private StoreService storeService;
    
    
    
    
    
    
    /** GET STORES SWAGGER ANNOTATIONS **/
    @GET
    @Path("/api/store")
    
    @Produces({ APPLICATION_JSON })
    
    @ApiOperation(  notes       = "Returns all stores",
                    response    = List.class,
                    produces    = APPLICATION_JSON,
                    value       = EMPTY)
   
    @ApiResponses(
            value   = { @ApiResponse(code  = 200, message  = "Successfully retrieved stores", response = List.class) 
                      })
    /** END SWAGGER ANNOTATIONS **/
    
    @GetMapping(path = "/api/store")
    public ResponseEntity<List<Store>> getStores() {
        return ResponseEntity.ok(storeService.getAll());
    }

    
    
    
    
    
    
    
    /** SAVE STORE SWAGGER ANNOTATIONS **/
    @POST
    @Path("/api/store")
    
    @Produces({ APPLICATION_JSON })
    @Consumes({ APPLICATION_JSON })
    
    @ApiOperation(  notes         = "Save store",
                    response      = StoreOutputDTO.class,
                    produces      = APPLICATION_JSON,
                    value         = EMPTY)
   
    @ApiResponses( value = {
          @ApiResponse(code   = 201, message    = "Store successfully saved", response = StoreOutputDTO.class),
          @ApiResponse(code   = 403, message    = "Store already saved",      response = ErrorDTO.class) 
    })
    
    /** END SWAGGER ANNOTATIONS **/
    
    
    @PostMapping(path = "/api/store")
    public ResponseEntity<Object> saveStore(@RequestBody StoreInputDTO storeInputDTO) {
        
        Store            newStore   =  new Store();
        Optional<String> name       =  ofNullable(storeInputDTO.getName());
        
        if (!name.isPresent()) {
            return status(BAD_REQUEST).build();
        } else {
            
            if (!validateName(name.get())) {
                return getResponse("Invalid Name", BAD_REQUEST);
            }
         
            String nameWithProperSpacing = storeInputDTO.getName().replaceAll("\\s+", " ");
            newStore.setName(nameWithProperSpacing.trim().toUpperCase());
        }
        
        Optional<String> address = ofNullable(storeInputDTO.getAddress());
        
        if (!address.isPresent()) {
            return status(BAD_REQUEST).build();
        } else {
            
            if (!validateAddress(address.get())) {
                return getResponse("Invalid Address", BAD_REQUEST);
            }
         
            String addressWithProperSpacing = storeInputDTO.getAddress().replaceAll("\\s+", " ");
            newStore.setAddress(addressWithProperSpacing.trim().toUpperCase());
        }
        
        Optional<Store> storeAlreadyExists = storeService.findByName(storeInputDTO.getName());
        if (storeAlreadyExists.isPresent()) {
            return getResponse("Store not allowed to be saved", FORBIDDEN);
        }
        
        storeService.save(newStore);
        
        StoreOutputDTO storeOutputDTO = new StoreOutputDTO();
        storeOutputDTO.setId(newStore.getId());
        storeOutputDTO.setName(newStore.getName());
        storeOutputDTO.setAddress(newStore.getAddress());
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(storeOutputDTO,CREATED);
        return responseEntity;
    }
    
    
    
    
    
    
    
    /** GET STORE BY ID SWAGGER ANNOTATIONS **/
    @GET
    @Path("/api/store/getById/{store-id}}")
    @Produces({ APPLICATION_JSON })
    
    @ApiOperation(  notes         = "Gets the store by id",
                    response      = StoreOutputDTO.class,
                    produces      = APPLICATION_JSON,
                    value         = EMPTY)
   
    @ApiResponses(
          value = {
          @ApiResponse(code  = 200,  message  = "Returns the store",            response   = StoreOutputDTO.class),
          @ApiResponse(code  = 404,  message  = "The store id was not found",   response   = ErrorDTO.class)
    })
   
    /** END SWAGGER ANNOTATIONS **/

    @GetMapping(path = "/api/store/getById/{store-id}")
    public ResponseEntity<Object> getStoreById(
            
            /** SWAGGER ANNOTATIONS **/
            @ApiParam    (value = "Store Id"     , required = true)   
            @PathParam   (value = "store-id")             
            
            @PathVariable(name  = "store-id"     , required = true) Long storeId )  {
     
        
        Optional<Store> store = storeService.findById(storeId);
        if (store.isPresent()) {
            
            StoreOutputDTO storeOutputDTO = new StoreOutputDTO();

            storeOutputDTO.setId(store.get().getId());
            storeOutputDTO.setName(store.get().getName());
            storeOutputDTO.setAddress(store.get().getAddress());
            
            ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(storeOutputDTO,OK);
            return responseEntity;
        }
        
        return getResponse(format("The store id %n was not found",storeId), NOT_FOUND);
    }
    
    
    
    
    
    
    
    /** GET STORE BY NAME SWAGGER ANNOTATIONS **/
    @GET
    @Path("/api/store/getByName/{store-name}}")
    @Produces({ APPLICATION_JSON })
    
    @ApiOperation(  notes         = "Gets the store by name",
                    response      = StoreOutputDTO.class,
                    produces      = APPLICATION_JSON,
                    value         = EMPTY)
   
    @ApiResponses(
          value = {
          @ApiResponse(code  = 200, message  = "Returns the store",            response   = StoreOutputDTO.class),
          @ApiResponse(code  = 404, message  = "The store name was not found", response   = ErrorDTO.class)
          })
   
    /** END SWAGGER ANNOTATIONS **/
    
    
    @GetMapping(path = "/api/store/getByName/{store-name}")
    public ResponseEntity<Object> getStoreByName(
            
            /** SWAGGER ANNOTATIONS **/
            @ApiParam    (value = "Store name"     , required = true)   
            @PathParam   (value = "store-name")             
            
            @PathVariable(name  = "store-name"     , required = true) String storeName )  {
        
        storeName = storeName.replaceAll("\\s+", " ").toUpperCase();
        
        Optional<Store> store = storeService.findByName(storeName);
        if (store.isPresent()) {
            
            StoreOutputDTO storeOutputDTO = new StoreOutputDTO();

            storeOutputDTO.setId(store.get().getId());
            storeOutputDTO.setName(store.get().getName());
            storeOutputDTO.setAddress(store.get().getAddress());
            
            ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(storeOutputDTO,OK);
            return responseEntity;
        }
        
        return getResponse(format("The store name %s was not found",storeName), NOT_FOUND);
    }
    
    
    
    
    
    
    
    /** UPDATES THE STORE SWAGGER ANNOTATIONS **/
    @POST
    @Path("/api/store/{store-id}")
    @Produces({ APPLICATION_JSON })
    
    @ApiOperation(  notes         = "Updates the store by id",
                    response      = StoreOutputDTO.class,
                    produces      = APPLICATION_JSON,
                    value         = EMPTY)
   
    @ApiResponses(
          value = {
          @ApiResponse(code = 200, message  = "The store was updated sucessfully", response   = StoreOutputDTO.class),
          @ApiResponse(code = 404, message  = "The store was not found",           response    = ErrorDTO.class)
          })
   
    /** END SWAGGER ANNOTATIONS **/
    
    
    
    @PostMapping(path= "/api/store/{store-id}")
    public ResponseEntity<Object> updateStore(
            
          
            @ApiParam     (value    = "Store id" , required = true)   
            @PathParam    (value    = "store-id")     
            @PathVariable (name     = "store-id" ) Long  storeId,
         
            @ApiParam(value = "StoreDTO"  , required = true) 
            @RequestBody StoreInputDTO   storeDTO){
      
        
        Store currentStore = null;
        try {
            currentStore = storeService.findById(storeId).get();
        } catch (NoSuchElementException e) {
            return getResponse(format("The store id %s was not found",String.valueOf(storeId)), NOT_FOUND);
        }
       
        
        Optional<String> newName =  ofNullable(storeDTO.getName());
        if (newName.isPresent()) {
            
            if (!validateName(newName.get())) {
                return getResponse("Invalid Name", BAD_REQUEST);
            }
         
            String newNameWithProperSpacing = newName.get().replaceAll("\\s+", " ");
            if (!org.apache.commons.lang.StringUtils.equalsIgnoreCase(newNameWithProperSpacing, currentStore.getName())) {
     
                Optional<Store> storeAlreadyExists = storeService.findByName(newNameWithProperSpacing);
                if (storeAlreadyExists.isPresent()) {
                    return getResponse("Not allowed to update to this name", FORBIDDEN);
                }
            }
        
            currentStore.setName(newNameWithProperSpacing.toUpperCase());
        }
        
        Optional<String> newAddress =  ofNullable(storeDTO.getAddress());
        if (newAddress.isPresent()) {
            
            if (!validateAddress(newAddress.get())) {
                return getResponse("Invalid Address", BAD_REQUEST);
            }
         
            String newAddressWithProperSpacing = newAddress.get().replaceAll("\\s+", " ");
            currentStore.setAddress(newAddressWithProperSpacing);
        }
        
        
        storeService.save(currentStore);
        StoreOutputDTO storeOutputDTO = new StoreOutputDTO();
        storeOutputDTO.setId(currentStore.getId());
        storeOutputDTO.setName(currentStore.getName());
        storeOutputDTO.setAddress(currentStore.getAddress());
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(storeOutputDTO,OK);
        return responseEntity;
    }
    
    
    
    
    
    
}
