package ar.com.garbarino.examen.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import ar.com.garbarino.examen.service.CartService;

@Controller
public class CartProcessSchedule {

	@Autowired
	private CartService cartService;

	private static final Logger log = LoggerFactory.getLogger(CartProcessSchedule.class);

	@Scheduled(cron = "0/59 * * * * ?")
	public void scheduleFixedDelayTask() {
		log.info("=========Fixed task Begins===========");
		cartService.processReadyCarts();
		log.info("=========Fixed task Ends  ===========");
	}
}
