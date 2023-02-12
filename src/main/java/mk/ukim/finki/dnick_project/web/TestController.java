package mk.ukim.finki.dnick_project.web;

import mk.ukim.finki.dnick_project.model.Question;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.model.exceptions.LectureNotFoundException;
import mk.ukim.finki.dnick_project.service.QuestionService;
import mk.ukim.finki.dnick_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    private final QuestionService questionService;
    private final UserService userService;

    public TestController(QuestionService questionService, UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllQuestions(Model model, HttpServletRequest request){
            List<Question> questionList = questionService.getQuestionsForTest();
            model.addAttribute("questions",questionList);
            model.addAttribute("score", userService.findUserByUsernameAndGetTestResults(request.getRemoteUser()));
            return "test";
    }

    @PostMapping("/post")
    public String postAnswers(Model model,HttpServletRequest request, @RequestParam(required = false) String answer1, @RequestParam(required = false) String answer2, @RequestParam(required = false) String answer3 )
    {
            Double score = this.questionService.checkAnswers(answer1,answer2,answer3,request.getRemoteUser());
            model.addAttribute("score",score);
            return "testScore";
    }
}
