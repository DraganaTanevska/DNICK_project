package mk.ukim.finki.dnick_project.web;

import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.model.exceptions.InvalidUsernameOrPassword;
import mk.ukim.finki.dnick_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService authService;

    public LoginController(UserService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    /**
     * Both username and password must belong to the same user
     * @param username - Checks if user with username exists
     * @param password - Checks if user with password exists
     * @return home.html
     */
    @PostMapping
    public String UserLogin(Model model, HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        User user;
        try{
            user = this.authService.Login(username, password);
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        }
        catch (InvalidUsernameOrPassword exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }

}
