package ma.fstt.controller;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ma.fstt.entity.User;
import ma.fstt.service.JwtService;
import ma.fstt.service.UserService;

/**
 * UserController
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	JwtService jwtService;
	@Autowired
	UserService userService;

	// get username from token
	@GetMapping(path = "", produces = "application/json")
	public ResponseEntity<Map<String, Object>> GetUsername(@RequestParam("token") String token) {
		try {
			String username = jwtService.getUserNameFromToken(token);
			return ResponseEntity.ok(Map.of("username", username));
		} catch (Exception e) {
			Map<String, Object> response = Map.of("status", "error", "message", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<?> getUserById(@PathVariable String id) {
		try {
			UUID userId = UUID.fromString(id);
			Optional<User> userOptional = userService.getUserById(userId);
			if (userOptional.isPresent()) {
				User user = userOptional.get();

				// Convert User to UserDTO
				UserDTO userDTO = new UserDTO();
				userDTO.setName(user.getName());
				userDTO.setEmail(user.getEmail());

				return ResponseEntity.ok(userDTO);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));
			}
		} catch (IllegalArgumentException e) {
			// Handle invalid UUID format
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("status", "error", "message", "Invalid UUID format"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("status", "error", "message", e.getMessage()));
		}
	}
}

class UserDTO {
	private String name;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
