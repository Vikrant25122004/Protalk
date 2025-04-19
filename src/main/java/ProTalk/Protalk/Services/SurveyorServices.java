package ProTalk.Protalk.Services;

import ProTalk.Protalk.Entity.Surveyors;
import ProTalk.Protalk.Repositories.SurveyorRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import jdk.security.jarsigner.JarSignerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SurveyorServices {
    @Autowired
    private SurveyorRepository surveyorRepository;
    @Autowired
    private EmailServices emailServices;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisService redisService;
    public void createSurveyor(Surveyors surveyor){
        surveyor.setPassword(passwordEncoder.encode(surveyor.getPassword()));
        emailServices.messages(surveyor.getEmail(),"user Registration"," you have successfully signup on our Protalk platform where you can show your skills and know through your conducted surveys");
        surveyorRepository.save(surveyor);

    }
    public Surveyors getSurveyor(String username) throws JsonProcessingException {
        Surveyors surveyors = redisService.get(username,Surveyors.class);
        if (surveyors!=null){
            return surveyors;
        }
        else{
            Surveyors surveyors1 = surveyorRepository.findByusername(username);
            if (surveyors1 == null) {
                throw new RuntimeException("Surveyor not found for username: " + username);
            }
            redisService.setLog(username,surveyors1, 300L);
            return surveyors1;
        }

    }
    @Transactional
    public void deleteSurveyor(String username){
        surveyorRepository.deleteByusername(username);
    }

    public void updatesurveyor(String username ,Surveyors surveyors) {
        Surveyors surveyors1 = surveyorRepository.findByusername(username);
        surveyors1.setPassword(passwordEncoder.encode(surveyors.getPassword()));
        surveyors1.setUsername(surveyors.getUsername());
        surveyors1.setEmail(surveyors.getEmail());
        surveyorRepository.save(surveyors1);
    }
    public void forgotpassword(Surveyors surveyors,String password){
        surveyors.setPassword(passwordEncoder.encode(password));
        emailServices.messages(surveyors.getEmail(),"reset password","you have successfully reset your password");

    }
}
