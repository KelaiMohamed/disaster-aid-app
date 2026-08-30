package ma.fstt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ma.fstt.entity.User;
import ma.fstt.repository.UserCredentialRepository;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserCredentialRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> credential = repository.findByEmail(username);
    return credential.map(CustomUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
  }
}
