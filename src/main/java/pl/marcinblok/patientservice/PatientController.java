package pl.marcinblok.patientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping(path = "/patients")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Patient patient) {
        patientRepository.save(patient);
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
    void deletePatientById(@PathVariable Integer id) {
          patientRepository.deleteById(id);
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