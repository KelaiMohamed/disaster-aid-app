package ma.fstt.victimmicroservice.Service;

import ma.fstt.victimmicroservice.Repository.Locationrepo;
import ma.fstt.victimmicroservice.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LocationService {

	@Autowired
	private Locationrepo locationRepository;

	public List<Location> getAll() {
		return locationRepository.findAll();
	}

	public Location getById(UUID id) {
		return locationRepository.findById(id).orElse(null);
	}

	public Location create(Location location) {
		return locationRepository.save(location);
	}

	public Location update(UUID id, Location updatedLocation) {
		Location existingLocation = locationRepository.findById(id).orElse(null);
		if (existingLocation == null) {
			return null;
		}
		updatedLocation.setId(id);
		return locationRepository.save(updatedLocation);
	}

	public boolean delete(UUID id) {
		if (!locationRepository.existsById(id)) {
			return false;
		}
		locationRepository.deleteById(id);
		return true;
	}
}
