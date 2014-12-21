package elixir.model;

public class SignatureFailureException extends RuntimeException {
	
	public SignatureFailureException (String message) {
		super(message);
	}
	
	public SignatureFailureException (String message, Throwable cause) {
		super(message, cause);
	}
}
