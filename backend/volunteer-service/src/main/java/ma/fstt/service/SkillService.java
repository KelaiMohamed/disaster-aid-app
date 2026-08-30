package ma.fstt.service;

import ma.fstt.entity.Skill;
import ma.fstt.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SkillService {

	@Autowired
	private SkillRepository skillRepository;

	public List<Skill> getAll() {
		return skillRepository.findAll();
	}

	public Skill getById(int id) {
		return skillRepository.findById(id).orElse(null);
	}

	public List<Skill> getByUserId(UUID userId) {
		return skillRepository.findByuserId(userId);
	}

	public Skill create(Skill skill) {
		return skillRepository.save(skill);
	}

	public Skill update(int id, Skill updatedSkill) {
		Skill existingSkill = skillRepository.findById(id).orElse(null);
		if (existingSkill == null) {
			return null;
		}
		updatedSkill.setId(id);
		return skillRepository.save(updatedSkill);
	}

	public boolean delete(int id) {
		if (!skillRepository.existsById(id)) {
			return false;
		}
		skillRepository.deleteById(id);
		return true;
	}
}
