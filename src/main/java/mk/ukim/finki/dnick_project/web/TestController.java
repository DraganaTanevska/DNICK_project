package mk.ukim.finki.dnick_project.web;

import mk.ukim.finki.dnick_project.service.QuestionService;
import mk.ukim.finki.dnick_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
        model.addAttribute("questions",questionService.getQuestionsForTest());
        model.addAttribute("score", userService.findUserByUsername(request.getRemoteUser()).getTestResults());
        return "test";
    }

    @PostMapping("/post")
    public String postAnswers(Model model,HttpServletRequest request, @RequestParam(required = false) String answer1, @RequestParam(required = false) String answer2, @RequestParam(required = false) String answer3 )
    {
        model.addAttribute("score",this.questionService.checkAnswers(answer1,answer2,answer3,request.getRemoteUser()));
        return "testScore";
    }
}
