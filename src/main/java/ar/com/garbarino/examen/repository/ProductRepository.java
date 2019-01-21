package ar.com.garbarino.examen.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import ar.com.garbarino.examen.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Product> findById(Long id);
	
}
