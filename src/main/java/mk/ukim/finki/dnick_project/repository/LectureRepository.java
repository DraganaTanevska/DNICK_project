package mk.ukim.finki.dnick_project.repository;

import mk.ukim.finki.dnick_project.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
