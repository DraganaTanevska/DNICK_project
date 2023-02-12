package mk.ukim.finki.dnick_project.mutants;

import mk.ukim.finki.dnick_project.model.*;
import mk.ukim.finki.dnick_project.model.exceptions.InvalidUsernameOrPassword;
import mk.ukim.finki.dnick_project.model.exceptions.QuestionNotFoundException;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.repository.QuestionRepository;
import mk.ukim.finki.dnick_project.repository.UserLectureRelationRepository;
import mk.ukim.finki.dnick_project.repository.UserRepository;
import mk.ukim.finki.dnick_project.service.LectureService;
import mk.ukim.finki.dnick_project.service.QuestionService;
import mk.ukim.finki.dnick_project.service.UserService;
import mk.ukim.finki.dnick_project.service.impl.QuestionServiceImpl;
import mk.ukim.finki.dnick_project.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionServiceMutantTesting {

    @Mock
    private UserRepository userRepository;
    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private QuestionRepository questionRepository;

    private QuestionService service;

    private LectureService lectureService;
    @Mock
    private UserLectureRelationRepository userLectureRelationRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        List<Question> questionList = new ArrayList<>();
        for (int i=0;i<3;i++)
        {
            Lecture lecture=new Lecture("title"+i,"text"+i);
            Question question1= new Question((long)i+1,"question1", "answer1",lecture);
            questionList.add(question1);
        }
        User test = new User("test", "name", "surname", "email","password", Role.ROLE_ADMIN);
        Mockito.when(this.questionRepository.findAll()).thenReturn(questionList);
        Mockito.when(this.userRepository.findByUsername("test")).thenReturn(Optional.of(test));
        Mockito.when(this.questionRepository.findById(Mockito.anyLong())).thenAnswer(i->Optional.of(new Question((long)i.getArguments()[0],"question1","answer1",new Lecture())));
        service = Mockito.spy(new QuestionServiceImpl(this.questionRepository,this.lectureRepository, this.userRepository));
    }

    @Test
    public void testFindById_ShouldFail() {
        Assertions.assertThrows(QuestionNotFoundException.class,()->service.findById(Long.valueOf(0)));
    }

    @Test
    public void testQuestionsForTest() {
        Assertions.assertEquals(3,service.getQuestionsForTest().size());
    }

    @Test
    public void testcheckAnswers() {
        Double result = service.checkAnswers("no","no","no","username");
        Assertions.assertEquals(0,result);
    }
    @Test
    public void testFindById_ShouldSucceed() {
        Question question = service.findById(Long.valueOf(1));
        Assertions.assertEquals(Long.valueOf(1),question.getId());
        Assertions.assertEquals("question1",question.getQuestion());
        Assertions.assertEquals("answer1",question.getAnswer());
    }
}
