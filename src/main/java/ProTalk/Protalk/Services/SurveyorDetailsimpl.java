package ProTalk.Protalk.Services;

import ProTalk.Protalk.Entity.Surveyors;
import ProTalk.Protalk.Repositories.SurveyorRepository;
import ProTalk.Protalk.Repositories.SurveysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SurveyorDetailsimpl implements UserDetailsService {
    @Autowired
    private SurveyorRepository surveyorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Surveyors surveyors = surveyorRepository.findByusername(username);
        if (surveyors!= null){
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(username)
                    .password(surveyors.getPassword())
                    .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("user not found with user");
    }
}
