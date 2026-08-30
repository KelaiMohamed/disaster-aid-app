package ma.fstt.victimmicroservice.Service;

import ma.fstt.victimmicroservice.Repository.AidtypeRepo;
import ma.fstt.victimmicroservice.entities.AidType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Extracted from AidTypeController, which previously called the repository
 * directly. Behavior is unchanged - every method here returns exactly what
 * the equivalent inline code in the controller used to (including null for
 * "not found", matching the original's orElse(null) pattern), so the
 * controller's request handling logic didn't need to change either.
 */
@Service
public class AidTypeService {

	@Autowired
	private AidtypeRepo aidTypeRepository;

	public List<AidType> getAll() {
		return aidTypeRepository.findAll();
	}

	public AidType getById(UUID id) {
		return aidTypeRepository.findById(id).orElse(null);
	}

	public AidType create(AidType aidType) {
		return aidTypeRepository.save(aidType);
	}

	/**
	 * Returns the saved entity, or null if no AidType exists with the given id
	 * (matching the controller's original not-found handling).
	 */
	public AidType update(UUID id, AidType updatedAidType) {
		AidType existingAidType = aidTypeRepository.findById(id).orElse(null);
		if (existingAidType == null) {
			return null;
		}
		updatedAidType.setId(id);
		return aidTypeRepository.save(updatedAidType);
	}

	public boolean delete(UUID id) {
		if (!aidTypeRepository.existsById(id)) {
			return false;
		}
		aidTypeRepository.deleteById(id);
		return true;
	}
}
