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
public class Skills {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "assistance_request_skill_type", joinColumns = @JoinColumn(name = "skils_id"), inverseJoinColumns = @JoinColumn(name = "assistancee_request_id"))
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
