package ma.fstt.victimmicroservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class AidType {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(nullable = false)
	private String name;

	// Relationship
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "assistance_request_aid_type", joinColumns = @JoinColumn(name = "aid_type_id"), inverseJoinColumns = @JoinColumn(name = "assistance_request_id"))
	private Set<AssistantRequests> assistancerequest;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<AssistantRequests> getAssistancerequest() {
		return assistancerequest;
	}

	public void setAssistancerequest(Set<AssistantRequests> assistancerequest) {
		this.assistancerequest = assistancerequest;
	}

}
