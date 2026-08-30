package ma.fstt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ma.fstt.entity.Skill;
import ma.fstt.service.SkillService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/volunteer/skills")
public class SkillController {

	@Autowired
	private SkillService skillService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllSkills() {
		try {
			List<Skill> allSkills = skillService.getAll();
			return ResponseEntity.ok(Map.of("data", allSkills));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getSkillById(@PathVariable int id) {
		Skill skill = skillService.getById(id);
		if (skill != null) {
			return ResponseEntity.ok(Map.of("data", skill));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Skill not found"));
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Map<String, Object>> getSkillsByUserId(@PathVariable UUID userId) {
		try {
			List<Skill> userSkills = skillService.getByUserId(userId);
			return ResponseEntity.ok(Map.of("data", userSkills));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> createSkill(@RequestBody Skill skill) {
		try {
			Skill savedSkill = skillService.create(skill);
			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("data", savedSkill));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", e.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateSkill(@PathVariable int id, @RequestBody Skill updatedSkill) {
		Skill savedSkill = skillService.update(id, updatedSkill);
		if (savedSkill != null) {
			return ResponseEntity.ok(Map.of("data", savedSkill));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Skill not found"));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteSkill(@PathVariable int id) {
		if (skillService.delete(id)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Skill not found"));
		}
	}
}
