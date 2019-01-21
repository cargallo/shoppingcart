package ar.com.garbarino.examen.exception;

public class BackendException extends RuntimeException {
	private static final long serialVersionUID = -433264910780895068L;

    public BackendException(String message) {
        super(message);
    }

    public BackendException(Throwable t) {
        super(t);
    }
	
}
