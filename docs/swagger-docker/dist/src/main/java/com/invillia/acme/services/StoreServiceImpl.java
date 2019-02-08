package com.invillia.acme.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Store;
import com.invillia.acme.repositories.StoreRepository;

/**
 * @author Maruen Mehana <maruen@gmail.com>
 *
 */

@Service
public class StoreServiceImpl implements StoreService {

  @Autowired
  StoreRepository storeRepository;
  
  
  @Override
  public List<Store> getAll() {
    List<Store> storeList = new ArrayList<>();
    storeRepository.findAll().forEach(storeList::add);
    return storeList;
    
  }
  
  public Store save(Store store) {
    return storeRepository.save(store);
  }

  @Override
  public Optional<Store> findById(Long id) {
    Optional<Store> store = storeRepository.findById(id);
    return store;
  }

  @Override
  public Optional<Store> findByName(String name) {
      Optional<Store> store = storeRepository.findByName(name);
      return store;
  }

}
