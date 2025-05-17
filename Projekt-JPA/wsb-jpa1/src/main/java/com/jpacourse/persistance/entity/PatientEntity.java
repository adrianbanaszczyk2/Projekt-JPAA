package com.jpacourse.persistance.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "PATIENT")
public class PatientEntity {

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private List<VisitEntity> visitEntities = new ArrayList<>();

	@ManyToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	@JoinTable(
			name = "PATIENT_TO_ADDRESS",
			joinColumns = @JoinColumn(name = "PATIENT_ID"),
			inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID")
	)
	private List<AddressEntity> addressEntities = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String telephoneNumber;

	private String email;

	@Column(nullable = false)
	private String patientNumber;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	@Column(nullable = false)
	private Boolean isalive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(String patientNumber) {
		this.patientNumber = patientNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Boolean getIsalive() {
		return isalive;
	}

	public void setIsalive(Boolean isalive) {
		this.isalive = isalive;
	}

	// Gettery i settery dla kolekcji
	public List<VisitEntity> getVisitEntities() {
		return visitEntities;
	}

	public void setVisitEntities(List<VisitEntity> visitEntities) {
		this.visitEntities = visitEntities != null ? visitEntities : new ArrayList<>();
	}

	public List<AddressEntity> getAddressEntities() {
		return addressEntities;
	}

	public void setAddressEntities(List<AddressEntity> addressEntities) {
		this.addressEntities = addressEntities != null ? addressEntities : new ArrayList<>();
	}
}