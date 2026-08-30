package ma.fstt.victimmicroservice.Controller;

import ma.fstt.victimmicroservice.Service.AidTypeService;
import ma.fstt.victimmicroservice.entities.AidType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/victim/aidtypes")
public class AidTypeController {
	@Autowired
	private AidTypeService aidTypeService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllAidTypes() {
		try {
			List<AidType> allAidTypes = aidTypeService.getAll();
			return ResponseEntity.ok(Map.of("data", allAidTypes));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getAidTypeById(@PathVariable UUID id) {
		AidType aidType = aidTypeService.getById(id);
		if (aidType != null) {
			return ResponseEntity.ok(Map.of("data", aidType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "AidType not found"));
		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> createAidType(@RequestBody AidType aidType) {
		try {
			AidType savedAidType = aidTypeService.create(aidType);
			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("data", savedAidType));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateAidType(@PathVariable UUID id,
			@RequestBody AidType updatedAidType) {
		AidType savedAidType = aidTypeService.update(id, updatedAidType);
		if (savedAidType != null) {
			return ResponseEntity.ok(Map.of("data", savedAidType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "AidType not found"));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteAidType(@PathVariable UUID id) {
		if (aidTypeService.delete(id)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "AidType not found"));
		}
	}
}
