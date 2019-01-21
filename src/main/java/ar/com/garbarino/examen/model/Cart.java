package ar.com.garbarino.examen.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ar.com.garbarino.examen.model.audit.DateAudit;

@Entity
@Table(name = "cart")
public class Cart extends DateAudit {
	private static final long serialVersionUID = -450641808194888534L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotBlank
	@Size(max = 100)
	String fullName;

	@NotBlank
	@Size(max = 50)
	String email;

	BigDecimal total = BigDecimal.ZERO;

	@Enumerated(EnumType.ORDINAL)
	CartStatus status = CartStatus.NEW;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	Set<CartProduct> cartProducts;

	public Cart(String fullName, String email) {
		this.fullName = fullName;
		this.email = email;
	}

	public Cart() {
	}

	public void addProduct(Product product, Integer quantity) {
		CartProduct cartProduct = new CartProduct(this, product, quantity);
		if (!this.cartProducts.add(cartProduct)) {
			this.cartProducts.forEach(cp -> {
				if (cp.equals(cartProduct)) {
					cp.setQuantity(cp.getQuantity() + cartProduct.getQuantity());
				}
			});
		}
		this.total = this.total.add(product.unitPrice.multiply(BigDecimal.valueOf(quantity)));
	}

	public void removeProduct(Product product) {
		CartProduct cartProduct = new CartProduct(this, product);
		for (CartProduct cp : this.cartProducts) {
			if (cp.equals(cartProduct)) {
				this.total = this.total.subtract(product.unitPrice.multiply(BigDecimal.valueOf(cp.getQuantity())));			
			}
		}
		this.cartProducts.remove(cartProduct);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public CartStatus getStatus() {
		return status;
	}

	public void setStatus(CartStatus status) {
		this.status = status;
	}

	public Set<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(Set<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Cart cart = (Cart) o;
		return Objects.equals(id, cart.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
