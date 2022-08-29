package mk.ukim.finki.dnick_project.service;

import mk.ukim.finki.dnick_project.model.Role;
import mk.ukim.finki.dnick_project.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User Register(String username, String name, String surname, String email, String password, Role role);

    boolean CheckIfExistLogin(String username, String password);

    User Login(String username, String password);

    void DeleteUser(String username);

    User findUserByUsername(String userId);
}
