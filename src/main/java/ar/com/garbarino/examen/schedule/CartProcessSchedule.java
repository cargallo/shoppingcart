package ar.com.garbarino.examen.schedule;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ar.com.garbarino.examen.model.Cart;
import ar.com.garbarino.examen.model.Product;
import ar.com.garbarino.examen.repository.CartRepository;
import ar.com.garbarino.examen.service.ProductService;

@Component
public class CartProcessSchedule {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductService productService;

	private static final Logger log = LoggerFactory.getLogger(CartProcessSchedule.class);

	// @Scheduled(fixedDelay = 1000)
	@Scheduled(cron = "0/30 * * * * ?")
	@Transactional
	public void scheduleFixedDelayTask() {
		System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
		processNewCarts();
	}

	public void processNewCarts() {
		Product prod = this.productService.findById(1L);
		try (Stream<Cart> cartStream = this.cartRepository.streamByStatus("NEW")) {
			cartStream.forEach(cart -> {
				String updatedStatus = this.productService.updateProductsStock(cart.getCartProducts());
			});
		}
	}
}
