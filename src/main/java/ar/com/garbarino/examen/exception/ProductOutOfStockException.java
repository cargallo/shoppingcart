package ar.com.garbarino.examen.exception;

public class ProductOutOfStockException extends Exception {
	private static final long serialVersionUID = -5672567588735793025L;

	public ProductOutOfStockException() {
	}

	public ProductOutOfStockException(String message) {
		super(message);
	}

	public ProductOutOfStockException(Throwable t) {
		super(t);
	}

}
