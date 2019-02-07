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

import com.invillia.acme.dto.StoreDTO;
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
    public ResponseEntity<Store> saveStore(@RequestBody StoreDTO storeDTO) {
        
        Store newStore = new Store();
        
        Optional<String> name =  Optional.ofNullable(storeDTO.getName());
        
        if (!name.isPresent()) {
            return status(BAD_REQUEST).build();
        } else {
            
            if (!ValidationUtils.validateName(name.get())) {
                return status(BAD_REQUEST).build();
            }
         
            String nameWithProperSpacing = storeDTO.getName().replaceAll("\\s+", " ");
            newStore.setName(nameWithProperSpacing.trim().toUpperCase());
        }
        
        Optional<String> address =  Optional.ofNullable(storeDTO.getAddress());
        
        if (!address.isPresent()) {
            return status(BAD_REQUEST).build();
        } else {
            
            if (!ValidationUtils.validateAddress(address.get())) {
                return status(BAD_REQUEST).build();
            }
         
            String addressWithProperSpacing = storeDTO.getAddress().replaceAll("\\s+", " ");
            newStore.setAddress(addressWithProperSpacing.trim().toUpperCase());
        }
        
        Optional<Store> storeAlreadyExists = storeService.findByName(storeDTO.getName());
        if (storeAlreadyExists.isPresent()) {
            return status(FORBIDDEN).build();
        }
        
        storeService.save(newStore);
        
        ResponseEntity<Store> responseEntity = new ResponseEntity<Store>(newStore,CREATED);
        return responseEntity;
    }

    @GetMapping(path = "/api/store/getById/{store-id}")
    public ResponseEntity<Store> getStoreById(@PathVariable(name = "store-id", required = true) Long storeId) {
        
        Optional<Store> store = storeService.findById(storeId);
        if (store.isPresent()) {
            return ResponseEntity.ok(store.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    
    @GetMapping(path = "/api/store/getByName/{store-name}")
    public ResponseEntity<Store> getStoreByName(@PathVariable(name = "store-name", required = true) String name) {
        
        name = name.replaceAll("\\s+", " ").toUpperCase();
        
        Optional<Store> store = storeService.findByName(name);
        if (store.isPresent()) {
            return ResponseEntity.ok(store.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    
    
    @PatchMapping(path= "/api/store/{store-id}")
    public ResponseEntity<Store> updateStore(@PathVariable(name="store-id")   Long       storeId,
                                             @RequestBody                     StoreDTO   storeDTO){
      
        Store currentStore = null;
        try {
            currentStore = storeService.findById(storeId).get();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).build();
        }
        
        
        Optional<String> newName =  Optional.ofNullable(storeDTO.getName());
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
        
        Optional<String> newAddress =  Optional.ofNullable(storeDTO.getAddress());
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
