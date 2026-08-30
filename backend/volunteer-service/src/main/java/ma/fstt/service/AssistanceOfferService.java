package ma.fstt.service;

import ma.fstt.entity.AidType;
import ma.fstt.entity.AssistanceOffer;
import ma.fstt.entity.Donation;
import ma.fstt.repository.AidTypeRepository;
import ma.fstt.repository.AssistanceOfferRepository;
import ma.fstt.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Extracted from AssistanceOfferController. The cascading-save logic in
 * create() - wiring up the Donations/AidTypes associations before saving -
 * is unchanged from the original controller method; only its location
 * moved.
 */
@Service
public class AssistanceOfferService {

	@Autowired
	private AssistanceOfferRepository assistanceOfferRepository;
	@Autowired
	private DonationRepository donationRepository;
	@Autowired
	private AidTypeRepository aidTypeRepository;

	public List<AssistanceOffer> getAll() {
		return assistanceOfferRepository.findAll();
	}

	public AssistanceOffer getById(UUID id) {
		return assistanceOfferRepository.findById(id).orElse(null);
	}

	public AssistanceOffer create(AssistanceOffer assistanceOffer) {
		// Set the association in Donations and save
		Set<Donation> donations = assistanceOffer.getDonations();
		donations.forEach(donation -> donation.setAssistanceOffer(assistanceOffer));

		// Set the association in AidTypes and save
		Set<AidType> aidTypes = assistanceOffer.getAidTypes();
		aidTypes.forEach(aidType -> {
			Set<AssistanceOffer> assistanceOffers = new HashSet<>();
			assistanceOffers.add(assistanceOffer);
			aidType.setAssistanceOffers(assistanceOffers);
		});

		return assistanceOfferRepository.save(assistanceOffer);
	}

	public AssistanceOffer update(UUID id, AssistanceOffer updatedAssistanceOffer) {
		AssistanceOffer existingAssistanceOffer = assistanceOfferRepository.findById(id).orElse(null);
		if (existingAssistanceOffer == null) {
			return null;
		}
		updatedAssistanceOffer.setId(id);
		return assistanceOfferRepository.save(updatedAssistanceOffer);
	}

	public boolean delete(UUID id) {
		if (!assistanceOfferRepository.existsById(id)) {
			return false;
		}
		assistanceOfferRepository.deleteById(id);
		return true;
	}

	public List<AssistanceOffer> getByUserId(UUID userId) {
		return assistanceOfferRepository.findByuserId(userId);
	}

	public List<AssistanceOffer> getByAssistanceRequestId(UUID assistanceRequestId) {
		return assistanceOfferRepository.findByassistanceRequestId(assistanceRequestId);
	}
}
