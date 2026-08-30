package ma.fstt.victimmicroservice.Repository;

import ma.fstt.victimmicroservice.entities.AssistantRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssistantRequestsrepo extends JpaRepository<AssistantRequests, UUID> {
	List<AssistantRequests> findByuserId(UUID userId);
}
