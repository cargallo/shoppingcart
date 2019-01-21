package ar.com.garbarino.examen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.garbarino.examen.model.CartProduct;
import ar.com.garbarino.examen.model.Product;

@Repository
public interface CartProductRepository extends JpaRepository<Product, Long>{

	@Query("SELECT cp FROM CartProduct cp where cp.cart.id = :cartId") 
    List<CartProduct> findByCartId(@Param("cartId") Long cartId);
}
