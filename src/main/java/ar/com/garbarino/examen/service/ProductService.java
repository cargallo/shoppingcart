package ar.com.garbarino.examen.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.garbarino.examen.exception.EntityNotFoundException;
import ar.com.garbarino.examen.model.CartProduct;
import ar.com.garbarino.examen.model.Product;
import ar.com.garbarino.examen.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public Product findById(Long id) {
		Product product = productRepository.findByIdForUpdate(id);
		return productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Product.class, "id", id.toString()));
	}
	
	public void updateStock (Long productId, Long stock) {
		Product product = this.findById(productId);
		product.setStock(stock);
		this.productRepository.save(product);
	}

	@Transactional
	public String updateProductsStock(Set<CartProduct> products) {
		String updatedStatus = "PROCESSED";
		for (CartProduct cartProduct : products) {
			Long updatedStock = cartProduct.getProduct().getStock() - cartProduct.getQuantity();
			if (updatedStock < 0) {
				updatedStatus = "FAILED";
			} else {
				this.updateStock(cartProduct.getProduct().getId(), updatedStock);
			}
		}
		return updatedStatus;
	}
}
