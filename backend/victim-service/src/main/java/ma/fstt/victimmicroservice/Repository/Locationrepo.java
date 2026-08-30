package ma.fstt.victimmicroservice.Repository;

import ma.fstt.victimmicroservice.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Locationrepo extends JpaRepository<Location, UUID> {
}
