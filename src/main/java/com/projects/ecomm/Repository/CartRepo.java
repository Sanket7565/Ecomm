package com.projects.ecomm.Repository;

import com.projects.ecomm.Model.CartItem;
import com.projects.ecomm.Model.Product;
import com.projects.ecomm.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartItem, Integer> {
    boolean existsByProduct(Product product);

    CartItem findByProduct(Product product);

    CartItem findByUserAndProduct(User user, Product product);

    CartItem findByUserAndProduct(Optional<User> byId, Optional<Product> byId1);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
