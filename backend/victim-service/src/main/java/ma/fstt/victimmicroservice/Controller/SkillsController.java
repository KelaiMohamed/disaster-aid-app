package ma.fstt.victimmicroservice.Controller;

import ma.fstt.victimmicroservice.Service.SkillsService;
import ma.fstt.victimmicroservice.entities.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/victim/skills")
public class SkillsController {
	@Autowired
	private SkillsService skillsService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllSkills() {
		try {
			List<Skills> allskills = skillsService.getAll();
			return ResponseEntity.ok(Map.of("data", allskills));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getSkillsById(@PathVariable UUID id) {
		Skills skils = skillsService.getById(id);
		if (skils != null) {
			return ResponseEntity.ok(Map.of("data", skils));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "AidType not found"));
		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> createSkills(@RequestBody Skills skills) {
		try {
			Skills savedskill = skillsService.create(skills);
			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("data", savedskill));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateSkills(@PathVariable UUID id,
			@RequestBody Skills updatedSkills) {
		Skills savedskills = skillsService.update(id, updatedSkills);
		if (savedskills != null) {
			return ResponseEntity.ok(Map.of("data", savedskills));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "AidType not found"));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteSkills(@PathVariable UUID id) {
		if (skillsService.delete(id)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "AidType not found"));
		}
	}
}
