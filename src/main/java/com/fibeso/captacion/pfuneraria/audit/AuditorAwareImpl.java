package com.fibeso.captacion.pfuneraria.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		String usuariolog = null;
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		if(authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
			usuariolog = springSecurityUser.getUsername();
		} else if (authentication.getPrincipal() instanceof String) {
			usuariolog = (String) authentication.getPrincipal();
		}
		return Optional.of(usuariolog);
	}

}
