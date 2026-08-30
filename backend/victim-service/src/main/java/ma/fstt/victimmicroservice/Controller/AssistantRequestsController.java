package ma.fstt.victimmicroservice.Controller;

import ma.fstt.victimmicroservice.Service.AssistantRequestsService;
import ma.fstt.victimmicroservice.entities.AssistantRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/victim/AssistantRequests")
public class AssistantRequestsController {
	@Autowired
	private AssistantRequestsService assistantRequestsService;

	@PostMapping
	public ResponseEntity<Map<String, Object>> createAssistanceOffer(@RequestBody AssistantRequests assistanceOffer) {
		try {
			AssistantRequests savedAssistanceRequest = assistantRequestsService.create(assistanceOffer);
			// Build the response map
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("data", savedAssistanceRequest);

			return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
		} catch (Exception e) {
			// Handle the exception and provide an error response
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
		}
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllAssistanceOffers() {
		try {
			List<AssistantRequests> allAssistanceRequest = assistantRequestsService.getAll();
			return ResponseEntity.ok(Map.of("data", allAssistanceRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getAssistanceOfferById(@PathVariable UUID id) {
		AssistantRequests assistanceRequest = assistantRequestsService.getById(id);
		if (assistanceRequest != null) {
			return ResponseEntity.ok(Map.of("data", assistanceRequest));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "AssistanceOffer not found"));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateAssistanceOffer(@PathVariable UUID id,
			@RequestBody AssistantRequests updatedAssistanceRequest) {
		AssistantRequests savedAssistanceOffer = assistantRequestsService.update(id, updatedAssistanceRequest);
		if (savedAssistanceOffer != null) {
			return ResponseEntity.ok(Map.of("data", savedAssistanceOffer));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "AssistanceOffer not found"));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteAssistanceRequest(@PathVariable UUID id) {
		if (assistantRequestsService.delete(id)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "AssistanceRequest not found"));
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Map<String, Object>> getAssistanceOffersByUserId(@PathVariable UUID userId) {
		try {
			List<AssistantRequests> userAssistanceRequest = assistantRequestsService.getByUserId(userId);
			return ResponseEntity.ok(Map.of("data", userAssistanceRequest));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}
}
