package pl.marcinblok.patientservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Patient getPatientByPesel(String pesel);
}