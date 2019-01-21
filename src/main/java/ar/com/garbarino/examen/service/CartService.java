package ar.com.garbarino.examen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.garbarino.examen.exception.CartProductsIsEmptyException;
import ar.com.garbarino.examen.exception.EntityNotFoundException;
import ar.com.garbarino.examen.exception.ProductOutOfStockException;
import ar.com.garbarino.examen.exception.UnprocessableEntityException;
import ar.com.garbarino.examen.model.Cart;
import ar.com.garbarino.examen.model.CartStatus;
import ar.com.garbarino.examen.model.Product;
import ar.com.garbarino.examen.payload.CartProductRequest;
import ar.com.garbarino.examen.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductService productService;

	private static final Logger log = LoggerFactory.getLogger(CartService.class);

	private Boolean isValidCartStatusOrThrow(Cart cart) {
		if (!cart.getStatus().equals(CartStatus.NEW)) {
			throw new UnprocessableEntityException("Only Carts in NEW status can be processed. Cart: " + cart.getId()
					+ " is in " + cart.getStatus() + " status.");
		}
		return true;
	}

	public Cart createCart(Cart cart) {
		return cartRepository.save(cart);
	}

	public Cart findById(Long id) {
		return cartRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Cart.class, "id", id.toString()));
	}

	public void addProduct(Long cartId, CartProductRequest cartProductRequest) {
		Cart cart = findById(cartId);
		Product product = productService.findById(cartProductRequest.getId());
		if (isValidCartStatusOrThrow(cart)) {
			cart.addProduct(product, cartProductRequest.getQuantity());
			cartRepository.save(cart);
		}
	}

	public void checkOut(Long cartId) {
		Cart cart = findById(cartId);
		if (isValidCartStatusOrThrow(cart)) {
			if (cart.getCartProducts().size() == 0) {
				throw new CartProductsIsEmptyException("Cart " + cartId + " has no associated products.");
			}
			cart.setStatus(CartStatus.READY);
			cartRepository.save(cart);
		}
	}

	public void deleteProduct(Long cartId, Long productId) {
		Cart cart = findById(cartId);
		Product product = this.productService.findById(productId);
		if (isValidCartStatusOrThrow(cart)) {
			cart.removeProduct(product);
			cartRepository.save(cart);
		}
	}

	@Transactional
	public void processReadyCarts() {
		List<Cart> failedCarts = new ArrayList<Cart>();
		try (Stream<Cart> cartStream = cartRepository.streamByStatus(CartStatus.READY)) {
			cartStream.parallel().forEach(cart -> {
				Cart updatedCart;
				updatedCart = processReadyCart(cart);
				if (updatedCart.getStatus().equals(CartStatus.FAILED)) {
					failedCarts.add(cart);
				} else {
					log.info("cart: " + cart.getId() + " status: " + cart.getStatus());
				}
			});
			logFailedCarts(failedCarts);
		}
	}

	public Cart processReadyCart(Cart cart) {
		CartStatus updatedStatus = CartStatus.PROCESSED;
		try {
			productService.updateProductsStock(cart.getId());
		} catch (ProductOutOfStockException e) {
			updatedStatus = CartStatus.FAILED;
		}
		cart.setStatus(updatedStatus);
		return cartRepository.save(cart);
	}

	public void logFailedCarts(List<Cart> failedCarts) {
		for (Cart cart : failedCarts) {
			log.warn("cart: " + cart.getId() + " status: " + CartStatus.FAILED);
		}
	}
	
    public void setRepository(CartRepository repository) {
        this.cartRepository = repository;
    }
    
    public void setProductService (ProductService productService) {
    	this.productService = productService;
    }

}
