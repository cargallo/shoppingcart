package ar.com.garbarino.examen.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.com.garbarino.examen.exception.EntityNotFoundException;
import ar.com.garbarino.examen.model.Cart;
import ar.com.garbarino.examen.payload.ApiResponse;
import ar.com.garbarino.examen.payload.CartProductRequest;
import ar.com.garbarino.examen.payload.CartRequest;
import ar.com.garbarino.examen.service.CartService;
import ar.com.garbarino.examen.service.ProductService;

@RestController
@RequestMapping("/api/carts")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	ProductService productService;
		
	@GetMapping("/{cartId}")
	public Cart getCart(@PathVariable Long cartId) {
		return cartService.findById(cartId);
	}
	
	@PostMapping
	public ResponseEntity<?> createCart(@Valid @RequestBody CartRequest cartRequest){
		Cart cart = cartService.createCart(cartRequest);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{cartId}")
                .buildAndExpand(cart.getId()).toUri();
		
		return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Cart Created Successfully"));
	}
	
	@PostMapping("/{cartId}/products")
	public ResponseEntity<?> insertProduct (@PathVariable (required = true) Long cartId, @RequestBody CartProductRequest cartProductRequest){
		cartService.insertProduct(cartId, cartProductRequest);
		return null;
	}
	
	@PostMapping("/{cartId}/checkout")
	public ResponseEntity<?> checkoutCart(@PathVariable Long cartId){
		cartService.checkOut(cartId);
		return ResponseEntity.ok().body(new ApiResponse(true, "Cart checkout Successfully"));
	}
	
	@DeleteMapping("/{cartId}/products/{productId}")
	public ResponseEntity<?> deleteProduct (@PathVariable Long cartId, @PathVariable Long productId){
		cartService.deleteProduct(cartId,productId);
		return ResponseEntity.ok().body(new ApiResponse(true, "Product in Cart Deleted Successfully"));
	}

}
