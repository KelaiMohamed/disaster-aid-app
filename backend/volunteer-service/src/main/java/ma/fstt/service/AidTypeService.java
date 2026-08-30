package ma.fstt.service;

import ma.fstt.entity.AidType;
import ma.fstt.repository.AidTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AidTypeService {

	@Autowired
	private AidTypeRepository aidTypeRepository;

	public List<AidType> getAll() {
		return aidTypeRepository.findAll();
	}

	public AidType getById(UUID id) {
		return aidTypeRepository.findById(id).orElse(null);
	}

	public AidType create(AidType aidType) {
		return aidTypeRepository.save(aidType);
	}

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
