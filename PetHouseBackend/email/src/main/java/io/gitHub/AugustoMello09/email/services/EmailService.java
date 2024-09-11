package io.gitHub.AugustoMello09.email.services;

import io.gitHub.AugustoMello09.email.infra.entities.Usuario;

public interface EmailService {
	
	void enviarEmailBemVindo(Usuario user);

}
