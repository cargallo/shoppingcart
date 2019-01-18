package ar.com.garbarino.examen.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cart_product")
public class CartProduct {
	@Id
	@GeneratedValue
	@Column(name = "user_group_id")
	@JsonIgnore
	Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	@JsonBackReference
	Cart cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	Product product;

	// additional fields
	@NotNull
	@Positive
	BigDecimal unitPrice = BigDecimal.ZERO;

	@NotNull
	@Positive
	Integer quantity = 0;

	public CartProduct() {
	}

	public CartProduct(Cart cart, Product product) {
		super();
		this.cart = cart;
		this.product = product;
		this.unitPrice = product.getUnitPrice();
	}

	public CartProduct(Cart cart, Product product, @NotNull @Positive Integer quantity) {
		super();
		this.cart = cart;
		this.product = product;
		this.unitPrice = product.getUnitPrice();
		this.quantity = quantity;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		CartProduct that = (CartProduct) o;
		return Objects.equals(cart, that.cart) && Objects.equals(product, that.product);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart, product);
	}

}
