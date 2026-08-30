package ma.fstt.victimmicroservice.Controller;

import ma.fstt.victimmicroservice.Service.LocationService;
import ma.fstt.victimmicroservice.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/victim/Locations")
public class LocationController {

	@Autowired
	private LocationService locationService;

	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllLocation() {
		try {
			List<Location> allLocations = locationService.getAll();
			return ResponseEntity.ok(Map.of("data", allLocations));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getLocationById(@PathVariable UUID id) {
		Location location = locationService.getById(id);
		if (location != null) {
			return ResponseEntity.ok(Map.of("data", location));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "location not found"));
		}
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> createLocation(@RequestBody Location location) {
		try {
			Location savedLocation = locationService.create(location);
			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("data", savedLocation));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while processing the request"));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateLocation(@PathVariable UUID id,
			@RequestBody Location updatedLocation) {
		Location savedLocation = locationService.update(id, updatedLocation);
		if (savedLocation != null) {
			return ResponseEntity.ok(Map.of("data", savedLocation));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Location not found"));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteLocation(@PathVariable UUID id) {
		if (locationService.delete(id)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Location not found"));
		}
	}

}
