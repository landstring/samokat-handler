package com.example.samokathandler.repositories.product;

import com.example.samokathandler.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true, value = """
        SELECT DISTINCT p.*
        FROM product p
        JOIN category c ON p.category_id = c.id
        WHERE c.id = :categoryId OR c.parent_id IN (
            WITH RECURSIVE category_tree AS (
                SELECT id, parent_id
                FROM category
                WHERE id = :categoryId
                UNION ALL
                SELECT c.id, c.parent_id
                FROM category c
                JOIN category_tree ct ON c.parent_id = ct.id
            )
            SELECT id FROM category_tree
        )
    """)
    Page<Product> findProductsByCategory_Id(Long categoryId, Pageable pageable);
}
