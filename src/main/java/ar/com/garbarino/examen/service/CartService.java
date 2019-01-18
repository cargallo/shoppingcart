package ar.com.garbarino.examen.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import ar.com.garbarino.examen.exception.EntityNotFoundException;
import ar.com.garbarino.examen.model.Cart;
import ar.com.garbarino.examen.model.Product;
import ar.com.garbarino.examen.payload.CartProductRequest;
import ar.com.garbarino.examen.payload.CartRequest;
import ar.com.garbarino.examen.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductService productService;

	public Cart createCart(CartRequest cartRequest) {
		Cart cart = new Cart(cartRequest.getFullname(), cartRequest.getEmail());
		return cartRepository.save(cart);
	}

	public Cart findById(Long id) {
		return cartRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Cart.class, "id", id.toString()));
	}

	@Transactional()
	public void insertProduct(Long cartId, CartProductRequest cartProductRequest) {
		Cart cart = this.findById(cartId);
		Product product = this.productService.findById(cartProductRequest.getId());
		cart.addProduct(product, cartProductRequest.getQuantity());
		cartRepository.save(cart);
	}

	public void checkOut(Long cartId) {
		Cart cart = this.findById(cartId);
		if (!cart.getStatus().equals("NEW")) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Only Carts in NEW status can be checked out. Cart: " + cartId + " is in " + cart.getStatus()
							+ " status.");
		}
		cart.setStatus("READY");
		this.cartRepository.save(cart);
	}

	public void deleteProduct(Long cartId, Long productId) {
		Cart cart = this.findById(cartId);
		Product product = this.productService.findById(productId);
		cart.removeProduct(product);
		this.cartRepository.save(cart);
	}

}
