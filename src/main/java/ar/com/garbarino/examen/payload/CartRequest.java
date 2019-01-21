package ar.com.garbarino.examen.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CartRequest {
	@NotBlank
	@Size(max = 100)
	String fullname;

	@NotBlank
	@Size(max = 50)
	String email;
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}