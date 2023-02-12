package mk.ukim.finki.dnick_project.model.Config;

import mk.ukim.finki.dnick_project.model.*;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.repository.QuestionRepository;
import mk.ukim.finki.dnick_project.repository.UserLectureRelationRepository;
import mk.ukim.finki.dnick_project.repository.UserRepository;
import mk.ukim.finki.dnick_project.service.LectureService;
import mk.ukim.finki.dnick_project.service.QuestionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final UserLectureRelationRepository userLectureRelationRepository;
    private final LectureService lectureService;
    private final LectureRepository lectureRepository;
    private final QuestionService questionService;
    private final QuestionRepository questionRepository;


    public DataInitializer(UserRepository userRepository, UserLectureRelationRepository userLectureRelationRepository, LectureService lectureService, LectureRepository lectureRepository, QuestionService questionService, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.userLectureRelationRepository = userLectureRelationRepository;
        this.lectureService = lectureService;
        this.lectureRepository = lectureRepository;
        this.questionService = questionService;

        this.questionRepository = questionRepository;
    }

    @PostConstruct
    public void initData() {
        User testUser = new User("dtn","dragana","tanevska","dragana.tanevska@yahoo.com","password", Role.ROLE_ADMIN);
        userRepository.save(testUser);
        testUser = new User("user","dragana","tanevska","dragana.tanevska@yahoo.com","password", Role.ROLE_USER);
        userRepository.save(testUser);
        for (int i=0;i<3;i++)
        {
            Lecture lecture=new Lecture("title"+i,"text"+i);
            lectureRepository.save(lecture);
        }
        for (Lecture lecture: lectureService.findAll())
        {
            for (User user: userRepository.findAll()) {
                UserLectureRelation userLectureRelation = new UserLectureRelation(user, lecture, false);
                userLectureRelationRepository.save(userLectureRelation);
            }
            Question question1= new Question("question1", "answer1",lecture);
            questionRepository.save(question1);
        }
    }
}
