package ProTalk.Protalk.Controller;

import ProTalk.Protalk.Entity.Surveyors;
import ProTalk.Protalk.Services.EmailServices;
import ProTalk.Protalk.Services.SurveyorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class Public {
    @Autowired
    private SurveyorServices surveyorServices;
    @Autowired
    private EmailServices emailServices;
    @PostMapping
    public ResponseEntity<?> signup(@RequestBody Surveyors surveyors){
        try {
            surveyorServices.createSurveyor(surveyors);
            return new ResponseEntity<>("successfully registered", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
