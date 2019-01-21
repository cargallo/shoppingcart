package ar.com.garbarino.examen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.garbarino.examen.exception.EntityNotFoundException;
import ar.com.garbarino.examen.exception.ProductOutOfStockException;
import ar.com.garbarino.examen.model.CartProduct;
import ar.com.garbarino.examen.model.Product;
import ar.com.garbarino.examen.repository.CartProductRepository;
import ar.com.garbarino.examen.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartProductRepository cartProductRepository;

	private void updateStock(Long productId, Long stock) {
		Product product = findById(productId);
		product.setStock(stock);
		productRepository.save(product);
	}
	
	public Product findById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Product.class, "id", id.toString()));
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ProductOutOfStockException.class)
	public void updateProductsStock(Long cartId) throws ProductOutOfStockException {
		List<CartProduct> cartProducts = cartProductRepository.findByCartId(cartId);
		for (CartProduct cartProduct : cartProducts) {
			Long updatedStock = cartProduct.getProduct().getStock() - cartProduct.getQuantity();
			if (updatedStock < 0) {
				throw new ProductOutOfStockException("Product: " + cartProduct.getId() + "is OUT of STOCK for CART:" + cartId);
			} else {
				updateStock(cartProduct.getProduct().getId(), updatedStock);
			}
		}
	}
}
