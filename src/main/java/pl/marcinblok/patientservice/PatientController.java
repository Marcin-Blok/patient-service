package pl.marcinblok.patientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class PatientController {

    Pattern pattern = Pattern.compile("[0-9]{11}");


    @Autowired
    private PatientRepository patientRepository;

    @PostMapping(path = "/patients")
    public @ResponseBody
    ResponseEntity<String> add(@RequestBody Patient patient) {
        String pesel = patient.getPesel();
        Matcher matcher = pattern.matcher(pesel);

        if (patient.getName() != null && patient.getSurname() != null && patient.getPesel() != null) {
            return peselValidator(patient, matcher);
        } else {
            return new ResponseEntity<>("Nie zapisano, następujące pola muszą zostać wypełnione: Imię, Nazwisko, Pesel", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    private ResponseEntity<String> peselValidator(@RequestBody Patient patient, Matcher matcher) {
        if (matcher.matches()) {
            try {
                patientRepository.save(patient);
                return new ResponseEntity<>("Zapisano", HttpStatus.CREATED);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>("Nie udało się dodać pacjenta", HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity<>("Nie zapisano, długość numeru pesel jest niepoprawna, bądź użyto niedozwolonych znaków lub liter", HttpStatus.NOT_ACCEPTABLE);
        }
    }


    @GetMapping(path = "/patients")
    public @ResponseBody
    ResponseEntity<List<Patient>> getAll() {
        return new ResponseEntity<>(patientRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/patients/{pesel}")
    public ResponseEntity<Patient> getPatientByPesel(@PathVariable String pesel) {
        return new ResponseEntity<>(patientRepository.getPatientByPesel(pesel), HttpStatus.OK);
    }

    @DeleteMapping(path = "/patients/{id}")
    public @ResponseBody
    ResponseEntity<String> deletePatientById(@PathVariable Integer id) {
        try {
            patientRepository.deleteById(id);
            return new ResponseEntity<>("Pacjent o podanym id został usunięty.", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Nie usunięto, wpis o podanym id nie istnieje", HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> replacePatient(@RequestBody Patient newPatient, @PathVariable Integer id) {
        Optional<Patient> pacjentPoZmianie = patientRepository.findById(id).map(patient -> {
            if (newPatient.getName() != null) {
                patient.setName(newPatient.getName());
            }
            if (newPatient.getSurname() != null) {
                patient.setSurname(newPatient.getSurname());
            }
            if (newPatient.getPesel() != null) {
                patient.setPesel(newPatient.getPesel());
            }
            return patientRepository.save(patient);
        });

        if (pacjentPoZmianie.isEmpty()) {
            return new ResponseEntity<Patient>(HttpStatus.NOT_MODIFIED);
        } else {
            return new ResponseEntity<Patient>(pacjentPoZmianie.get(), HttpStatus.OK);
        }
    }
}