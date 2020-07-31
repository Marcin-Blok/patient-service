package pl.marcinblok.patientservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
