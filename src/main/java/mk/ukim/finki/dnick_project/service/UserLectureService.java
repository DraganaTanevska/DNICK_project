package mk.ukim.finki.dnick_project.service;

import mk.ukim.finki.dnick_project.model.UserLectureRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface UserLectureService {
    List<UserLectureRelation> findAll();
    void addUserLectureRelation(String userId);
    Page<UserLectureRelation> findAllByUsername(String userId, Integer page);
    void setStatus(String userId, Long lectureId);
    boolean getStatus(String userId, Long lectureId);
}
