package ar.com.garbarino.examen.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.garbarino.examen.model.Cart;
import ar.com.garbarino.examen.model.CartStatus;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	Optional<Cart> findById(Long cartId);
	
	@Query("select c from Cart c where c.status = :status order by id")
	Stream<Cart> streamByStatus(@Param("status") CartStatus status);
}
