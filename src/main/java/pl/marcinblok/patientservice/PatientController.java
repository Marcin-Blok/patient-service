package pl.marcinblok.patientservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
