package dsi.esprit.tn.Controllers;

import java.util.List;
import java.util.stream.Collectors;

//import dsi.esprit.tn.Config.JpaConfig;
import dsi.esprit.tn.Payload.Request.SignupRequest;
import dsi.esprit.tn.security.UserDetailsImpl;
import dsi.esprit.tn.security.jwt.JwtUtils;
import dsi.esprit.tn.services.IUserServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import dsi.esprit.tn.Payload.Request.LoginRequest;
import dsi.esprit.tn.Payload.Response.JwtResponse;
import dsi.esprit.tn.Payload.Response.MessageResponse;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;


  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  private IUserServiceFeign userServiceFeign;
//  @Autowired
//  private JpaConfig em;
//  @GetMapping("/users")
//  public int numberOfUsers() {
//    return em.numberOfUsers();
//  }
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    userServiceFeign.registerUser(signUpRequest);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  @GetMapping("/usermstest")
  public ResponseEntity<?> usermsTest() {
    return ResponseEntity.ok(userServiceFeign.usermsTest());
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new MessageResponse("You've been signed out!"));
  }
}
