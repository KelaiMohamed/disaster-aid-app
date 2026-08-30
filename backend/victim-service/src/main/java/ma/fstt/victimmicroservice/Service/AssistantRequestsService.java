package ma.fstt.victimmicroservice.Service;

import ma.fstt.victimmicroservice.Repository.AidtypeRepo;
import ma.fstt.victimmicroservice.Repository.AssistantRequestsrepo;
import ma.fstt.victimmicroservice.Repository.Locationrepo;
import ma.fstt.victimmicroservice.Repository.SkillsRepo;
import ma.fstt.victimmicroservice.entities.AidType;
import ma.fstt.victimmicroservice.entities.AssistantRequests;
import ma.fstt.victimmicroservice.entities.Location;
import ma.fstt.victimmicroservice.entities.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Extracted from AssistantRequestsController. The cascading-save logic in
 * create() - saving the Location, wiring up the Skills/AidType associations,
 * then saving those and finally the AssistantRequests itself - is unchanged
 * from the original controller method; only its location moved.
 */
@Service
public class AssistantRequestsService {

	@Autowired
	private AssistantRequestsrepo assistantrequestsrepo;
	@Autowired
	private SkillsRepo skillsrepo;
	@Autowired
	private AidtypeRepo aidtyperepo;
	@Autowired
	private Locationrepo locationrepo;

	public AssistantRequests create(AssistantRequests assistanceOffer) {
		// Save Location entity
		Location location = assistanceOffer.getLocation();
		locationrepo.save(location);

		// Set the association in Skills and save
		Set<Skills> skills = assistanceOffer.getSkills();
		skills.forEach(skill -> {
			Set<AssistantRequests> assistanceRequestsSet = new HashSet<>();
			assistanceRequestsSet.add(assistanceOffer);
			skill.setAssistancerequest(assistanceRequestsSet);
		});

		// Set the association in AidType and save
		Set<AidType> aidTypes = assistanceOffer.getAidType();
		aidTypes.forEach(aidType -> {
			Set<AssistantRequests> assistanceRequestsSet = new HashSet<>();
			assistanceRequestsSet.add(assistanceOffer);
			aidType.setAssistancerequest(assistanceRequestsSet);
		});

		// Save all Skills entities
		skillsrepo.saveAll(skills);

		// Save all AidType entities
		aidtyperepo.saveAll(aidTypes);

		// Save the AssistantRequests entity
		return assistantrequestsrepo.save(assistanceOffer);
	}

	public List<AssistantRequests> getAll() {
		return assistantrequestsrepo.findAll();
	}

	public AssistantRequests getById(UUID id) {
		return assistantrequestsrepo.findById(id).orElse(null);
	}

	public AssistantRequests update(UUID id, AssistantRequests updatedAssistanceRequest) {
		AssistantRequests existingAssistanceRequest = assistantrequestsrepo.findById(id).orElse(null);
		if (existingAssistanceRequest == null) {
			return null;
		}
		updatedAssistanceRequest.setId(id);
		return assistantrequestsrepo.save(updatedAssistanceRequest);
	}

	public boolean delete(UUID id) {
		if (!assistantrequestsrepo.existsById(id)) {
			return false;
		}
		assistantrequestsrepo.deleteById(id);
		return true;
	}

	public List<AssistantRequests> getByUserId(UUID userId) {
		return assistantrequestsrepo.findByuserId(userId);
	}
}
