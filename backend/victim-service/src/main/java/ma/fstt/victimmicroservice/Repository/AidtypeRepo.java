package ma.fstt.victimmicroservice.Repository;

import ma.fstt.victimmicroservice.entities.AidType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AidtypeRepo extends JpaRepository<AidType, UUID> {
}
