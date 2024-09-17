package io.gitHub.AugustoMello09.auth.domain.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails{
	private static final long serialVersionUID = 1L;

	private UUID id;

	private String nome;

	private String email;

	private String senha;

	private Set<Cargo> cargos = new HashSet<>();
	
	private UUID idCarrinho;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return cargos.stream().map(x -> new SimpleGrantedAuthority(x.getAuthority()))
				.collect(Collectors.toList());
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
