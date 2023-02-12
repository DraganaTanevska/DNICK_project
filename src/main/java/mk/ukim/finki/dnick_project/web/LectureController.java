package mk.ukim.finki.dnick_project.web;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.exceptions.LectureNotFoundException;
import mk.ukim.finki.dnick_project.service.LectureService;
import mk.ukim.finki.dnick_project.service.QuestionService;
import mk.ukim.finki.dnick_project.service.UserLectureService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"/lectures"})
public class LectureController {

    private final LectureService lectureService;
    private final UserLectureService userLectureRelationService;
    private final QuestionService questionService;

    public LectureController(LectureService lectureService, UserLectureService userLectureRelationService, QuestionService questionService) {
        this.lectureService = lectureService;
        this.userLectureRelationService = userLectureRelationService;
        this.questionService = questionService;
    }

    @GetMapping({"/list-all/{page}"})
    public String findAll(Model model, HttpServletRequest request,@PathVariable Integer page){
            model.addAttribute("statuses", userLectureRelationService.findAllByUsername(request.getRemoteUser(), page));
            return "courses-page-1";
    }

    @GetMapping({"/lecture/{lectureId}"})
    public String findById(Model model, @PathVariable Long lectureId, HttpServletRequest request){
        Lecture lecture;
        try{
            lecture = lectureService.findById(lectureId);
            model.addAttribute("lecture", lecture);
            model.addAttribute("lectures", lectureService.findAll());
            model.addAttribute("status", userLectureRelationService.getStatus(request.getRemoteUser(),lectureId));
            model.addAttribute("questions", questionService.findAllByLecture(lectureId));
            return "course";
        }
        catch (LectureNotFoundException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("statuses", userLectureRelationService.findAllByUsername(request.getRemoteUser(), 1));
            return "courses-page-1";
        }
    }

    @GetMapping({"/lecture/set-status/{lectureId}"})
    public String setStatus(Model model, @PathVariable Long lectureId, HttpServletRequest request){
        try{
            userLectureRelationService.setStatus(request.getRemoteUser(),lectureId);
            return "redirect:/lectures/lecture/{lectureId}";
        }
        catch (LectureNotFoundException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("statuses", userLectureRelationService.findAllByUsername(request.getRemoteUser(), 1));
            return "courses-page-1";
        }
    }
}
