package io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions;

public class AuthorizationException  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public AuthorizationException(String message) {
		super(message);
	}

}
