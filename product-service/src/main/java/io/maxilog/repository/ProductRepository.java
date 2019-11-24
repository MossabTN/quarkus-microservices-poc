package io.maxilog.repository;

import io.maxilog.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = "UPDATE Product p SET p.quantity = p.quantity - ?2  WHERE p.id = ?1")
    int decreaseProductQuantity(long id, int qte);

    Page<Product> findAllByNameContaining(String name, Pageable pageable);
}
