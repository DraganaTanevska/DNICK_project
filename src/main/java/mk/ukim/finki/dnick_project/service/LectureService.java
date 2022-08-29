package mk.ukim.finki.dnick_project.service;

import mk.ukim.finki.dnick_project.model.Lecture;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LectureService {
    Lecture findById(Long id);

    List<Lecture> findAll();

    Page<Lecture> findAllPaging(Integer page, Integer size);
}
