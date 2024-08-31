package io.gitHub.AugustoMello09.PetHouse.provider;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Cargo;

public class CargoProvider {
	
	private static final long ID = 1L;
	private static final String AUTHORITY = "ROLE_OPERATOR";

	public Cargo criar() {
		Cargo cargo = new Cargo();
		cargo.setId(ID);
		cargo.setAuthority(AUTHORITY);
		return cargo;
	}


}
