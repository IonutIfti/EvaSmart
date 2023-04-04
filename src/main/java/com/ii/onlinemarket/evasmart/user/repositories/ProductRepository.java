package com.ii.onlinemarket.evasmart.user.repositories;

import com.ii.onlinemarket.evasmart.user.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
