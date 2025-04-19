package ProTalk.Protalk.Controller;

import ProTalk.Protalk.Entity.Surveyors;
import ProTalk.Protalk.Services.EmailServices;
import ProTalk.Protalk.Services.SurveyorDetailsimpl;
import ProTalk.Protalk.Services.SurveyorServices;

import ProTalk.Protalk.utils.Jwtutils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RequestMapping("/public")
@RestController
public class Public {
    private static final Logger logger = LoggerFactory.getLogger(Public.class);

    @Autowired
    private SurveyorServices surveyorServices;
    @Autowired
    private EmailServices emailServices;
    @Autowired
    private SurveyorDetailsimpl surveyorDetailsimpl;
    @Autowired
    private Jwtutils jwtutils;

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
    @PostMapping("/signup")
    public ResponseEntity<?> login(@RequestBody Surveyors surveyors){
        try {
            UserDetails userDetails = surveyorDetailsimpl.loadUserByUsername(surveyors.getUsername());
            String jwt = jwtutils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Surveyor login error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect username or password");
        }
    }
    @PostMapping("/forgot/{username}")
    public ResponseEntity<?> forgotpassword(@PathVariable String username,@RequestBody String password) throws JsonProcessingException {
        Surveyors surveyors = surveyorServices.getSurveyor(username);
        surveyorServices.forgotpassword(surveyors,password);
        return new ResponseEntity<>("password reset successfully",HttpStatus.CREATED);
    }

}
