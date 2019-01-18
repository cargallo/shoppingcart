package ar.com.garbarino.examen.payload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartProductRequest {
	@NotNull
	@Positive
	Long id;

	@NotNull
	@Positive
	Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}