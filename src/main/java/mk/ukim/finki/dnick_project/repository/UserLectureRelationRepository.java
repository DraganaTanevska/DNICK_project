package mk.ukim.finki.dnick_project.repository;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.model.UserLectureRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLectureRelationRepository extends JpaRepository<UserLectureRelation, Long> {
    List<UserLectureRelation> findAllByLectureAndUser(Lecture lecture, User user);
    Page<UserLectureRelation> findAllByUser(User user, Pageable pageable);
}
