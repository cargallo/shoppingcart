package ar.com.garbarino.examen.exception;

public class CartProductsIsEmptyException extends BackendException {
	private static final long serialVersionUID = -5672567588735793025L;

	public CartProductsIsEmptyException(String message) {
		super(message);
	}

	public CartProductsIsEmptyException(Throwable t) {
		super(t);
	}

}
