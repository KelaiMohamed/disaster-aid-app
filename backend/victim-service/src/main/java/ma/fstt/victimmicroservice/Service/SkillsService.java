package ma.fstt.victimmicroservice.Service;

import ma.fstt.victimmicroservice.Repository.SkillsRepo;
import ma.fstt.victimmicroservice.entities.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SkillsService {

	@Autowired
	private SkillsRepo skillsRepository;

	public List<Skills> getAll() {
		return skillsRepository.findAll();
	}

	public Skills getById(UUID id) {
		return skillsRepository.findById(id).orElse(null);
	}

	public Skills create(Skills skills) {
		return skillsRepository.save(skills);
	}

	public Skills update(UUID id, Skills updatedSkills) {
		Skills existingSkills = skillsRepository.findById(id).orElse(null);
		if (existingSkills == null) {
			return null;
		}
		updatedSkills.setId(id);
		return skillsRepository.save(updatedSkills);
	}

	public boolean delete(UUID id) {
		if (!skillsRepository.existsById(id)) {
			return false;
		}
		skillsRepository.deleteById(id);
		return true;
	}
}
