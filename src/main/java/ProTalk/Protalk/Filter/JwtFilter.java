package ProTalk.Protalk.Filter;

import ProTalk.Protalk.Services.SurveyorDetailsimpl;
import ProTalk.Protalk.utils.Jwtutils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private SurveyorDetailsimpl surveyorDetailsimpl;
    @Autowired
    private Jwtutils jwtutils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorisationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authorisationHeader != null && authorisationHeader.startsWith("Bearer")){
            jwt = authorisationHeader.substring(7);
            username = jwtutils.extractUsername(jwt);
        }
        if (username!=null){
            UserDetails userDetails = surveyorDetailsimpl.loadUserByUsername(username);
            if (jwtutils.validateToken(jwt)){
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request,response);

    }
}
