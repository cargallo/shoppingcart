package ar.com.garbarino.examen.repository;

import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.garbarino.examen.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	//@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Product> findById(Long id);
	
	//@Lock(LockModeType.PESSIMISTIC_WRITE)
	//@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value   ="1000")}) 
    @Query("select p from Product p where p.id = :id")
    Product findByIdForUpdate(@Param("id") Long id);
	
}
