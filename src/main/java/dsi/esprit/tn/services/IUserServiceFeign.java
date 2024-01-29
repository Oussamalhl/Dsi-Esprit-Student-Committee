package dsi.esprit.tn.services;

import dsi.esprit.tn.Payload.Request.SignupRequest;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RibbonClient(name="localhost")
@FeignClient(name = "localhost", url = "http://localhost:8082")
public interface IUserServiceFeign {
    @PostMapping("/api/user/signup")
    ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest);
    @GetMapping("/api/user/test")
    String usermsTest();
}
