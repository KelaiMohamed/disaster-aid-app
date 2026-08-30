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
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)

	private UUID id;
	private String City;
	private String Street;
	private String Address;

	@JsonBackReference
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
	private Set<AssistantRequests> assistanceRequests;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public Set<AssistantRequests> getAssistanceRequests() {
		return assistanceRequests;
	}

	public void setAssistanceRequests(Set<AssistantRequests> assistanceRequests) {
		this.assistanceRequests = assistanceRequests;
	}

}
