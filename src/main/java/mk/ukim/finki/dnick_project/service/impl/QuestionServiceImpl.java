package mk.ukim.finki.dnick_project.service.impl;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.Question;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.model.exceptions.LectureNotFoundException;
import mk.ukim.finki.dnick_project.model.exceptions.QuestionNotFoundException;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.repository.QuestionRepository;
import mk.ukim.finki.dnick_project.repository.UserRepository;
import mk.ukim.finki.dnick_project.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, LectureRepository lectureRepository, UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }

    @Override
    public List<Question> findAllByLecture(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(()-> new LectureNotFoundException(lectureId));
        List<Question> questionList = new ArrayList<>();
        if(lecture != null)
        {
            questionList = questionRepository.findAllByLecture(lecture);
        }
        return questionList;
    }

    @Override
    public List<Question> getQuestionsForTest() {
        List<Question> questionList = questionRepository.findAll();
        List<Question> questions = new ArrayList<>();
        questions.add(questionList.get(0));
        questions.add(questionList.get(1));
        questions.add(questionList.get(2));
        return questions;
    }

    @Override
    public Double checkAnswers(String answer1, String answer2, String answer3, String userId) {
        List<Question> questions = this.getQuestionsForTest();
        double sum = 1.0;
        if(!questions.get(0).getAnswer().equals(answer1)){
            sum-=0.33;
        }
        if(!questions.get(1).getAnswer().equals(answer2)){
            sum-=0.33;
        }
        if(!questions.get(2).getAnswer().equals(answer3))
        {
            sum-=0.33;
        }
        if(sum<=0.01)
        {
            sum=0;
        }
        sum*=100;
        User user=userRepository.findByUsername(userId).orElse(null);
        if(user == null)
            return 0.0;
        user.setTestResults(sum);
        userRepository.save(user);
        return sum;
    }


}
