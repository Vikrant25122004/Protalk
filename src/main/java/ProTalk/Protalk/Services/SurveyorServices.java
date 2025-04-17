package ProTalk.Protalk.Services;

import ProTalk.Protalk.Entity.Surveyors;
import ProTalk.Protalk.Repositories.SurveyorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SurveyorServices {
    @Autowired
    private SurveyorRepository surveyorRepository;
    @Autowired
    private EmailServices emailServices;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void createSurveyor(Surveyors surveyor){
        surveyor.setPassword(passwordEncoder.encode(surveyor.getPassword()));
        surveyorRepository.save(surveyor);
        emailServices.messages(surveyor.getEmail(),"user Registration"," you have successfully signup on our Protalk platform where you can show your skills and know through your conducted surveys");

    }
}
