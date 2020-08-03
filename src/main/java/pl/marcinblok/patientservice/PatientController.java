package pl.marcinblok.patientservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping(path = "/patients")
    public @ResponseBody
    String add(@RequestBody Patient patient) {
        patientRepository.save(patient);
        return "Saved";
    }

    @GetMapping(path = "/patients")
    public @ResponseBody
    List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @DeleteMapping(path = "/patients/{id}")
    public @ResponseBody
    String deletePatientById(@PathVariable Integer id) {
        patientRepository.deleteById(id);
        return "Patient with id " + id + " has been succesfully removed from database";
    }

    @PutMapping("/patients/{id}")
    Optional<Patient> replacePatient(@RequestBody Patient newPatient, @PathVariable Integer id) {
        return patientRepository.findById(id).map(patient -> {
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
    }
}
