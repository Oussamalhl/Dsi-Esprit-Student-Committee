package dsi.esprit.tn.services;

import dsi.esprit.tn.Payload.Request.SignupRequest;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RibbonClient(name="zuul")
@FeignClient(name = "zuul", url = "http://zuul-ms:8765")
public interface IUserServiceFeign {
    @PostMapping("/userservice-ms/api/user/auth/signup")
    ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest);
    @GetMapping("/userservice-ms/api/user/test")
    String usermsTest();
}
