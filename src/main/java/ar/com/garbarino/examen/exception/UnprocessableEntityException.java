package ar.com.garbarino.examen.exception;

public class UnprocessableEntityException extends BackendException {
	private static final long serialVersionUID = -433264910780895068L;

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(Throwable t) {
        super(t);
    }
	
}
