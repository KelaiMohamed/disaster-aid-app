package ma.fstt.service;

import ma.fstt.entity.User;
import ma.fstt.repository.UserCredentialRepository;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserCredentialRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	public Map<String, String> saveUser(User credential) {
		credential.setPassword(passwordEncoder.encode(credential.getPassword()));
		repository.save(credential);
		return Map.of("message", "User created successfully");
	}

	public Map<String, String> generateToken(String username) {
		return Map.of("token", jwtService.generateToken(username));
	}

	public void validateToken(String token) {
		jwtService.validateToken(token);
	}

	public UUID getUserIdByUsername(String email) {
		return repository.findByEmail(email)
				.map(User::getId)
				.orElse(null);
	}

}
