package mk.ukim.finki.dnick_project.web;

import mk.ukim.finki.dnick_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String UserLogin(@RequestParam String username, @RequestParam String password) {
        if (authService.CheckIfExistLogin(username, password))
            return "home";
        else return "/home?error=NonExistingUser";
    }

    /**
     * Deletes user with given username
     * @param username - Checks if user with username exists
     * @return home.html
     */
    @PostMapping("/delete/{username}")
    public String DeleteUser(@PathVariable String username) {
        authService.DeleteUser(username);
        return "/home";
    }
}
