package jw.spring.training.securityexample.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jw.spring.training.securityexample.entity.AppUser;
import jw.spring.training.securityexample.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private static final String JWT_KEY = "super secret key";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String NAME_CLAIM_TAG = "name";
//    private static final String BEARER_TOKEN_TAG = "Authorization";

    private final AppUserService appUserService;

    @Autowired
    public JwtFilter(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if(bearerToken == null || !bearerToken.contains("Bearer ")){
            System.out.println("No bearer token found, Security Context not modified");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_KEY)).build();

        System.out.println("bearerToken:" + bearerToken);
        System.out.println("bearerToken.substring(7):" + bearerToken.substring(7));
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtVerifier.verify(bearerToken.substring(7));
        }catch (TokenExpiredException | InvalidClaimException ex){
            System.out.println(ex.getMessage());
            httpServletResponse.sendError(401);
            return;
        }

        Claim userNameClaimed = decodedJWT.getClaim(NAME_CLAIM_TAG);
        handleSecurityContext(userNameClaimed);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void handleSecurityContext(Claim userNameClaimed){
        if (userNameClaimed != null) {
            UserDetails userDetails = appUserService.loadUserByUsername(userNameClaimed.asString());
            printDebugUserDetails(userDetails);

            UsernamePasswordAuthenticationToken roleToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(roleToken);
        }
    }

    private void printDebugUserDetails(UserDetails userDetails) {
        if (userDetails == null) {
            System.out.println("User details NULL!");
            return;

        }
        System.out.println("*****");
        System.out.println("User details of user: " + userDetails.getUsername());
        userDetails.getAuthorities().forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));
        System.out.println("*****");
    }
}
