package mk.ukim.finki.dnick_project.repository;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByLecture(Lecture lecture);
}
