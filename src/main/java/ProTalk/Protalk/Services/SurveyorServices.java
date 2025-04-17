package ProTalk.Protalk.Services;

import ProTalk.Protalk.Entity.Surveyors;
import ProTalk.Protalk.Repositories.SurveyorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyorServices {
    @Autowired
    private SurveyorRepository surveyorRepository;
    @Autowired
    private EmailServices emailServices;
    public void createSurveyor(Surveyors surveyor){
        surveyorRepository.save(surveyor);
        emailServices.messages(surveyor.getEmail(),"user Registration"," you have successfully signup on our Protalk platform where you can show your skills and know through your conducted surveys");

    }
}
