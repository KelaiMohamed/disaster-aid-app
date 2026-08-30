package ma.fstt.controller;

import ma.fstt.dto.AuthRequest;
import ma.fstt.entity.User;
import ma.fstt.service.AuthService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(path = "/register", produces = "application/json")
	public ResponseEntity<Map<String, String>> addNewUser(@RequestBody User user) {
		try {
			return ResponseEntity.ok(service.saveUser(user));
		} catch (DataIntegrityViolationException e) {
			String errorMessage = "Email is already in use";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Map.of("message", errorMessage));
		} catch (Exception e) {
			String errorMessage = "Error while processing the request";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("message", errorMessage));
		}
	}

	@PostMapping(path = "/token", produces = "application/json")
	public ResponseEntity<Map<String, String>> getToken(@RequestBody AuthRequest authRequest) {
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

			if (authenticate.isAuthenticated()) {
				Map<String, String> response = new HashMap<>();

				// Generate token and get userId by email
				Map<String, String> tokenResponse = service.generateToken(authRequest.getEmail());
				String userId = service.getUserIdByUsername(authRequest.getEmail()).toString();

				// Check if the user exists
				if (userId != null) {
					response.putAll(tokenResponse);
					response.put("userId", userId);
					return ResponseEntity.ok(response);
				} else {
					// Handle the case when the user is not found
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
				}

			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
		}
	}

	@GetMapping(path = "/validate", produces = "application/json")
	public ResponseEntity<Map<String, Object>> validateToken(@RequestParam("token") String token) {
		try {
			// Validate the token
			service.validateToken(token);

			// If validation is successful, return a JSON response with a success message
			Map<String, Object> response = Map.of("status", "success", "message", "Token is valid");
			return ResponseEntity.ok(response);
		} catch (TransactionException e) {
			// If validation fails, return a JSON response with an error message
			Map<String, Object> response = Map.of("status", "error", "message", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			// Handle other exceptions (e.g., custom validation failure exceptions)
			Map<String, Object> response = Map.of("status", "error", "message", "Token is invalid");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
