package mk.ukim.finki.dnick_project.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    public String getHomePage(Model model) throws IOException, InterruptedException {
        return "index";
    }

    @GetMapping("/access_denied")
    public String getAccessDeniedPage(Model model) {
        return "index";
    }

    @GetMapping("/nav-bar")
    public String getNavBar(Model model) {
        return "nav-bar";
    }
}
