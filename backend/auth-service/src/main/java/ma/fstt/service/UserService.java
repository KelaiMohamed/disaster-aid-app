package ma.fstt.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.fstt.entity.User;
import ma.fstt.repository.UserCredentialRepository;

@Service
public class UserService {
	@Autowired
	private UserCredentialRepository userCredentialRepository;

	public Optional<User> getUserById(UUID id) {
		return userCredentialRepository.findById(id);
	}

}
