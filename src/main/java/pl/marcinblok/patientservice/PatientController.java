package pl.marcinblok.patientservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping(path = "/patient")
    public @ResponseBody
    String add(@RequestBody Patient patient) {
        patientRepository.save(patient);
        return "Saved";
    }

    @GetMapping(path="/patient")
    public @ResponseBody
    List<Patient> getAll() {
        return patientRepository.findAll();
    }

}
