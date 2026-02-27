package com._Rbrothers.product_service.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com._Rbrothers.product_service.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = """
        UPDATE products
        SET reserved_quantity = reserved_quantity + :qty
        WHERE id = :id
        AND (available_quantity - reserved_quantity) >= :qty
        """,
        nativeQuery = true)
    int reserveStock(@Param("id") Long productId,
                     @Param("qty") Integer quantity);

    @Modifying
    @Query(value = """
        UPDATE products
        SET reserved_quantity = reserved_quantity - :qty
        WHERE id = :id
        """,
        nativeQuery = true)
    void releaseStock(@Param("id") Long productId,
                      @Param("qty") Integer quantity);

    @Modifying
    @Query(value = """
        UPDATE products
        SET available_quantity = available_quantity - :qty,
            reserved_quantity = reserved_quantity - :qty
        WHERE id = :id
        """,
        nativeQuery = true)
    void confirmStock(@Param("id") Long productId,
                      @Param("qty") Integer quantity);
}