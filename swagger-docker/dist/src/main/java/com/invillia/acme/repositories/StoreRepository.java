package com.invillia.acme.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.invillia.acme.model.Store;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

@RestResource(exported = false)
public interface StoreRepository extends CrudRepository<Store, Long> {
    
    Optional<Store> findByName(String name);

}
