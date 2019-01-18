package ar.com.garbarino.examen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.garbarino.examen.model.Product;
import ar.com.garbarino.examen.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping
	List<Product> getProducts(){
		return null;
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<?> updateStock(@PathVariable Long productId){
		productService.updateStock(productId, 55L);
		return null;
	}
}
