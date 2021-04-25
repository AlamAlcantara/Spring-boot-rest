package com.example.users.models;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RUNTIME)
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@PostAuthorize("hasRole('ROLE_ADMIN')")
public @interface SecurityRule {

}
