package ma.fstt.victimmicroservice.entities;

import ma.fstt.victimmicroservice.enumeration.AssistantState;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class AssistantRequests {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String Description;
	// I use enumeration for attribute state(IN_PROGRESS/COMPLETED)
	@Enumerated(EnumType.STRING)
	private AssistantState state;
	private Date date;

	// Create a user-id commun between microservice auth and victim
	private UUID userId;

	// Relationship

	@ManyToMany(mappedBy = "assistancerequest", cascade = CascadeType.ALL)
	private Set<Skills> skills;
	@ManyToMany(mappedBy = "assistancerequest", cascade = CascadeType.ALL)
	private Set<AidType> aidType;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public AssistantState getState() {
		return state;
	}

	public void setState(AssistantState state) {
		this.state = state;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public Set<Skills> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skills> skills) {
		this.skills = skills;
	}

	public Set<AidType> getAidType() {
		return aidType;
	}

	public void setAidType(Set<AidType> aidType) {
		this.aidType = aidType;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
