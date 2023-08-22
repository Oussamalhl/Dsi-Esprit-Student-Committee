package dsi.esprit.tn.Controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Authentication {
    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }
}
