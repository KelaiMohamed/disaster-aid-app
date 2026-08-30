package ma.fstt.service;

import ma.fstt.entity.Donation;
import ma.fstt.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DonationService {

	@Autowired
	private DonationRepository donationRepository;

	public List<Donation> getAll() {
		return donationRepository.findAll();
	}

	public Donation getById(UUID id) {
		return donationRepository.findById(id).orElse(null);
	}

	public Donation create(Donation donation) {
		return donationRepository.save(donation);
	}

	public Donation update(UUID id, Donation updatedDonation) {
		Donation existingDonation = donationRepository.findById(id).orElse(null);
		if (existingDonation == null) {
			return null;
		}
		updatedDonation.setId(id);
		return donationRepository.save(updatedDonation);
	}

	public boolean delete(UUID id) {
		if (!donationRepository.existsById(id)) {
			return false;
		}
		donationRepository.deleteById(id);
		return true;
	}
}
