package mk.ukim.finki.dnick_project.service.impl;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.exceptions.LectureNotFoundException;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.service.LectureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    public LectureServiceImpl(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public Lecture findById(Long id) {
        return lectureRepository.findById(id).orElseThrow(()-> new LectureNotFoundException(id));
    }

    public List<Lecture> findAll() {
        return lectureRepository.findAll();
    }

    public Page<Lecture> findAllPaging(Integer page, Integer size)
    {
        return lectureRepository.findAll(PageRequest.of(page,size, Sort.by("id").ascending()));
    }
}
