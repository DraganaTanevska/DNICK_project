package mk.ukim.finki.dnick_project.service;

import mk.ukim.finki.dnick_project.model.Question;

import java.util.List;

public interface QuestionService {
    Question findById(Long id);
    List<Question> findAllByLecture(Long lectureId);
    List<Question> getQuestionsForTest();
    Double checkAnswers(String answer1, String answer2, String answer3, String userId);
}
