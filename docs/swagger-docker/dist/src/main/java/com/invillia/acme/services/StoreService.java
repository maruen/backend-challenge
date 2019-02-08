package com.invillia.acme.services;

import java.util.List;
import java.util.Optional;

import com.invillia.acme.model.Store;

/**
 * StoreService interface for Stores.
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */
public interface StoreService {
  
  public List<Store> getAll();
  
  public Store           save       (Store    store);
  public Optional<Store> findById   (Long     id);
  public Optional<Store> findByName (String   name);
  
}
