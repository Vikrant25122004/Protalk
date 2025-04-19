package ProTalk.Protalk.Controller;

import ProTalk.Protalk.Entity.Surveyors;
import ProTalk.Protalk.Repositories.SurveyorRepository;
import ProTalk.Protalk.Services.SurveyorServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Surveyor")
public class SurveyorController {
    @Autowired
    private SurveyorServices surveyorServices;
    @Autowired
    private SurveyorRepository surveyorRepository;
    @GetMapping("/login")
    public ResponseEntity<?> getSurveyor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {

            Surveyors surveyors = surveyorServices.getSurveyor(username);
            return new ResponseEntity<>(surveyors, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deletebyusename(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            surveyorServices.deleteSurveyor(username);
            return new ResponseEntity<>("user account deleted successfully",HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e,HttpStatus.UNAUTHORIZED);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateuser(@RequestBody Surveyors surveyors){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            surveyorServices.updatesurveyor(username,surveyors);
            return new ResponseEntity<>("user updated successfully",HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }



}
