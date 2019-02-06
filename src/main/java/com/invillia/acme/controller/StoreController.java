/**
 * 
 */
package com.invillia.acme.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.model.Store;
import com.invillia.acme.services.StoreService;
import com.invillia.acme.utils.ValidationUtils;


/**
 * StoreController for Store related APIs.
 * @author Maruen Mehana <maruen@gmail.com>
 */


@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping(path = "/api/store")
    public ResponseEntity<List<Store>> getStores() {
        return ResponseEntity.ok(storeService.getAll());
    }

    @PostMapping(path = "/api/store")
    public ResponseEntity<Store> saveStore(@RequestBody Store store) {
        
        Optional<String> name =  Optional.ofNullable(store.getName());
        
        if (!name.isPresent()) {
            return status(BAD_REQUEST).build();
        } else {
            
            if (!ValidationUtils.validateName(name.get())) {
                return status(BAD_REQUEST).build();
            }
         
            String nameWithProperSpacing = store.getName().replaceAll("\\s+", " ");
            store.setName(nameWithProperSpacing.trim());
        }
        
        Optional<Store> storeAlreadyExists = storeService.findByName(store.getName());
        if (storeAlreadyExists.isPresent()) {
            return status(FORBIDDEN).build();
        }
        
        
        
        Optional<String> address =  Optional.ofNullable(store.getAddress());
        
        if (!address.isPresent()) {
            return status(BAD_REQUEST).build();
        } else {
            
            if (!ValidationUtils.validateAddress(address.get())) {
                return status(BAD_REQUEST).build();
            }
         
            String addressWithProperSpacing = store.getName().replaceAll("\\s+", " ");
            store.setName(addressWithProperSpacing.trim());
        }
        
        store.setName(store.getName().toUpperCase());
        store.setAddress(store.getAddress().toUpperCase());
        
        storeService.save(store);
        
        ResponseEntity<Store> responseEntity = new ResponseEntity<Store>(store,CREATED);
        return responseEntity;
    }

    @GetMapping(path = "/api/store/{store-id}")
    public ResponseEntity<Store> getStoreById(@PathVariable(name = "store-id", required = true) Long storeId) {
        
        Optional<Store> store = storeService.findById(storeId);
        if (store.isPresent()) {
            return ResponseEntity.ok(store.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    
    @PatchMapping(path= "/api/store/{store-id}")
    public ResponseEntity<Store> updateStore(@PathVariable(name="store-id")   Long    storeId,
                                             @RequestBody                     Store   store){
      
        Store currentStore = null;
        try {
            currentStore = storeService.findById(storeId).get();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
        
        
        Optional<String> newName =  Optional.ofNullable(store.getName());
        if (newName.isPresent()) {
            
            if (!ValidationUtils.validateName(newName.get())) {
                return status(BAD_REQUEST).build();
            }
         
            String newNameWithProperSpacing = newName.get().replaceAll("\\s+", " ");
            if (!org.apache.commons.lang.StringUtils.equalsIgnoreCase(newNameWithProperSpacing, currentStore.getName())) {
     
                Optional<Store> storeAlreadyExists = storeService.findByName(newNameWithProperSpacing);
                if (storeAlreadyExists.isPresent()) {
                    return status(FORBIDDEN).build();
                }
            }
        
            currentStore.setName(newNameWithProperSpacing.toUpperCase());
        }
        
        Optional<String> newAddress =  Optional.ofNullable(store.getAddress());
        if (newAddress.isPresent()) {
            
            if (!ValidationUtils.validateAddress(newAddress.get())) {
                return status(BAD_REQUEST).build();
            }
         
            String newAddressWithProperSpacing = newAddress.get().replaceAll("\\s+", " ");
            currentStore.setAddress(newAddressWithProperSpacing);
        }
        
        
        storeService.save(currentStore);
        return ResponseEntity.ok().body(currentStore);
    }
    
    
    
    
    
    
}
