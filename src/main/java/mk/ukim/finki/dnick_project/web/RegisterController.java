package mk.ukim.finki.dnick_project.web;

import mk.ukim.finki.dnick_project.model.Role;
import mk.ukim.finki.dnick_project.service.UserLectureService;
import mk.ukim.finki.dnick_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
    // private final AuthService authService;
    private final UserService userService;
    private final UserLectureService userLectureService;

    public RegisterController(UserService userService, UserLectureService userLectureService) {
        // this.authService = authService;
        this.userService = userService;
        this.userLectureService = userLectureService;
    }

    @GetMapping
    public String getRegisterPage() {
        return "register";
    }

    /**
     *
     * @param email - Makes new user with given email
     * @param password - Makes new user with given password
     * @param username - Makes new user with given username
     * @return login.html
     */
    @PostMapping
    public String toLoginPage(@RequestParam String email,@RequestParam String name,@RequestParam String surname, @RequestParam String password, @RequestParam String username) {
        Role role = Role.ROLE_ADMIN;
        userService.Register(username, name, surname, email, password, role);
        return "/login";
    }
}
