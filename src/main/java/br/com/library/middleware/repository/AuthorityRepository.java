package br.com.library.middleware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.library.middleware.domain.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
