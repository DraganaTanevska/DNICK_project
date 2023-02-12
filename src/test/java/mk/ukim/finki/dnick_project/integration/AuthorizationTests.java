package mk.ukim.finki.dnick_project.integration;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.Role;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.model.exceptions.InvalidUsernameOrPassword;
import mk.ukim.finki.dnick_project.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthorizationTests {
    MockMvc mockMvc;
    @Autowired
    UserService userService;
    private static boolean dataInitialized = false;
    private String username = "dtn";
    private String password = "password";

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testGetLoginPage() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/login");
        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void testPostLoginShouldSucceed() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/login")
                .param("username", username)
                .param("password", password);
        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/home"));

    }

    @Test
    public void testPostLoginPageShouldFail_InvalidUsernameOrPassword() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/login")
                .param("username", "username")
                .param("password", password);
        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hasError"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void testGetRegisterPage() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/register");
        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

    @Test
    public void testPostRegisterPageRequiredAllFields() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/register")
                .param("username", "user")
                .param("password", "password");
        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testPostRegisterPageShouldFail_UsernameAlreadyExists() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/register")
                .param("username", username)
                .param("password", password)
                .param("name","name")
                .param("surname","surname")
                .param("email","email");

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hasError"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

}
