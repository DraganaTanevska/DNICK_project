package mk.ukim.finki.dnick_project.integration;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.Role;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.service.LectureService;
import mk.ukim.finki.dnick_project.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
public class LecturesTests {

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
    public void testGetLecturesPage() throws Exception {
        MockHttpServletRequestBuilder lectureRequest = MockMvcRequestBuilders.get("/lectures/list-all/1");
        this.mockMvc.perform(lectureRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("courses-page-1"));
    }

    @Test
    public void testGetLecturesPagePagination() throws Exception {
        MockHttpServletRequestBuilder lectureRequest = MockMvcRequestBuilders.get("/lectures/list-all/2");
        this.mockMvc.perform(lectureRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("statuses"))
                .andExpect(MockMvcResultMatchers.view().name("courses-page-1"));
    }

    @Test
    public void testGetLectureById() throws Exception {
        MockHttpServletRequestBuilder lectureRequest = MockMvcRequestBuilders.get("/lectures/lecture/1");
        this.mockMvc.perform(lectureRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("lecture"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("status"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("lectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("questions"))
                .andExpect(MockMvcResultMatchers.view().name("course"));
    }

    @Test
    public void testGetLectureById_ShouldFail() throws Exception {
        MockHttpServletRequestBuilder lectureRequest = MockMvcRequestBuilders.get("/lectures/lecture/0");

        this.mockMvc.perform(lectureRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hasError"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("statuses"))
                .andExpect(MockMvcResultMatchers.view().name("courses-page-1"));
    }

    @Test
    public void testSetStatus() throws Exception {
        Lecture lecture = new Lecture("titleText","textTest");
        lectureRepository.save(lecture);
        MockHttpServletRequestBuilder lectureRequest = MockMvcRequestBuilders.get("/lectures/lecture/set-status/1");
        this.mockMvc.perform(lectureRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/lectures/lecture/{lectureId}"));
    }

    @Test
    public void testSetStatus_ShouldFail() throws Exception {
        Lecture lecture = new Lecture("titleText","textTest");
        lectureRepository.save(lecture);
        MockHttpServletRequestBuilder lectureRequest = MockMvcRequestBuilders.get("/lectures/lecture/set-status/0");
        this.mockMvc.perform(lectureRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("hasError"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("statuses"))
                .andExpect(MockMvcResultMatchers.view().name("courses-page-1"));
    }

}
