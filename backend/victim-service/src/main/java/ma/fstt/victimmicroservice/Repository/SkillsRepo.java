package ma.fstt.victimmicroservice.Repository;

import ma.fstt.victimmicroservice.entities.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillsRepo extends JpaRepository<Skills, UUID> {
}
