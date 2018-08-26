package br.com.library.middleware.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.library.middleware.domain.User;
import br.com.library.middleware.repository.UserRepository;
import br.com.library.middleware.security.SecurityUtils;
import java.util.*;

/**
* Service class for managing users.
*/
@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
       this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthoritiesByLogin(String login) {
       return userRepository.findOneWithAuthoritiesByLogin(login);
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities(Long id) {
       return userRepository.findOneWithAuthoritiesById(id);
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
       return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
   }

}
