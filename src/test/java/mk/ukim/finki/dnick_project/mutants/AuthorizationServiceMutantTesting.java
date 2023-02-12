package mk.ukim.finki.dnick_project.mutants;


import mk.ukim.finki.dnick_project.model.Role;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.model.exceptions.InvalidUsernameOrPassword;
import mk.ukim.finki.dnick_project.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.repository.UserLectureRelationRepository;
import mk.ukim.finki.dnick_project.repository.UserRepository;
import mk.ukim.finki.dnick_project.service.UserService;
import mk.ukim.finki.dnick_project.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorizationServiceMutantTesting {


    @Mock
    private UserRepository userRepository;
    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService service;
    @Mock
    private UserLectureRelationRepository userLectureRelationRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        User test = new User("test", "name", "surname", "email","password", Role.ROLE_ADMIN);
        Mockito.when(this.userRepository.findByUsernameAndPassword("test","password")).thenReturn(Optional.of(test));
        service = Mockito.spy(new UserServiceImpl(this.userRepository, this.passwordEncoder,this.lectureRepository, this.userLectureRelationRepository));
    }
    @Test
    public void testSuccessRegister() {
        User user = this.service.Register("test", "name", "surname", "email", "password", Role.ROLE_ADMIN);
        Mockito.verify(this.service).Register("test", "name", "surname", "email", "password", Role.ROLE_ADMIN);


        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("names do not mach", "name", user.getName());
        Assert.assertEquals("roles do not mach", Role.ROLE_ADMIN, user.getRole());
        Assert.assertEquals("surnames do not mach", "surname", user.getSurname());
        Assert.assertEquals("passwords do not mach", "test", user.getPassword());
        Assert.assertEquals("usernames do not mach", "test", user.getUsername());
    }

    @Test
    public void testSuccessLogin() {
        User user=service.Login("test","password");
        Assertions.assertEquals("test",user.getUsername());
        Assertions.assertEquals("password",user.getPassword());
        Assertions.assertEquals("name",user.getName());
        Assertions.assertEquals("surname",user.getSurname());
        Assertions.assertEquals(Role.ROLE_ADMIN,user.getRole());
    }

    @Test
    public void testSuccessLogin_ShouldFail() {
        Assertions.assertThrows(InvalidUsernameOrPassword.class,()->service.Login("test","incorrectPass"));
    }

    @Test
    public void testDuplicateUsername() {
        User user = new User("username", "password", "name", "surename", "password",Role.ROLE_USER);
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                UsernameAlreadyExistsException.class,
                () -> this.service.Register(username, "password", "password", "name", "surename", Role.ROLE_USER));
        Mockito.verify(this.service).Register(username, "password", "password", "name", "surename", Role.ROLE_USER);
    }
}
