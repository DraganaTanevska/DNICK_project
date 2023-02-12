package mk.ukim.finki.dnick_project.integration;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.Question;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.repository.QuestionRepository;
import mk.ukim.finki.dnick_project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
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
public class QuestionTests {


    MockMvc mockMvc;
    @Autowired
    UserService userService;
    @Autowired
    LectureRepository lectureRepository;
    private static User user;
    private static boolean dataInitialized = false;
    private static MockHttpSession mockHttpSession;


    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
        initData();
    }

    private void initData() {
        if (!dataInitialized) {

            String admin = "admin";
            User user = userService.Login("dtn","password");
            dataInitialized = true;
        }
    }

    @Test
    public void testGetTestPage() throws Exception {
        MockHttpServletRequestBuilder lectureRequest = MockMvcRequestBuilders.get("/test");
        this.mockMvc.perform(lectureRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("questions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("score"))
                .andExpect(MockMvcResultMatchers.view().name("test"));
    }

    @Test
    public void testGetTestResultsPage() throws Exception {
        MockHttpServletRequestBuilder lectureRequest = MockMvcRequestBuilders.post("/test/post");
        this.mockMvc.perform(lectureRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("score"))
                .andExpect(MockMvcResultMatchers.view().name("testScore"));
    }
}
