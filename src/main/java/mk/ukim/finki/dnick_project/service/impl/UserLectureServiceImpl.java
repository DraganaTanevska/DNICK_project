package mk.ukim.finki.dnick_project.service.impl;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.model.UserLectureRelation;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.repository.UserLectureRelationRepository;
import mk.ukim.finki.dnick_project.repository.UserRepository;
import mk.ukim.finki.dnick_project.service.UserLectureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLectureServiceImpl implements UserLectureService {

    private final UserLectureRelationRepository userLectureRelationRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    public UserLectureServiceImpl(UserLectureRelationRepository userLectureRelationRepository, UserRepository userRepository, LectureRepository lectureRepository) {
        this.userLectureRelationRepository = userLectureRelationRepository;
        this.userRepository = userRepository;
        this.lectureRepository = lectureRepository;
    }


    @Override
    public List<UserLectureRelation> findAll() {
        return userLectureRelationRepository.findAll();
    }

    @Override
    public void addUserLectureRelation(String userId) {
        for (Lecture lecture:
             lectureRepository.findAll()) {
            UserLectureRelation userLectureRelation = new UserLectureRelation(userRepository.findByUsername(userId).orElse(null), lecture, false);
            userLectureRelationRepository.save(userLectureRelation);
        }
    }

    @Override
    public Page<UserLectureRelation> findAllByUsername(String userId, Integer page) {
        Page<UserLectureRelation> userLectureRelations = userLectureRelationRepository.findAllByUser(userRepository.findByUsername(userId).orElse(null),PageRequest.of(page-1,4, Sort.by("lecture")));
        return userLectureRelations;
    }


    @Override
    public void setStatus(String userId, Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElse(null);
        User user = userRepository.findByUsername(userId).orElse(null);
        if(lecture!=null && user!=null)
        {
            UserLectureRelation userLectureRelation = userLectureRelationRepository.findAllByLectureAndUser(lecture,user).stream().findFirst().orElse(null);
            if(userLectureRelation != null)
            {
                userLectureRelation.setStatus(!userLectureRelation.getStatus());
                userLectureRelationRepository.save(userLectureRelation);
            }

        }
    }

    @Override
    public boolean getStatus(String userId, Long lectureId) {
        UserLectureRelation userLectureRelation = userLectureRelationRepository.findAllByLectureAndUser(lectureRepository.findById(lectureId).orElse(null),userRepository.findByUsername(userId).orElse(null)).stream().findFirst().orElse(null);
        if(userLectureRelation != null)
        {
            return userLectureRelation.getStatus();
        }
        return false;
    }
}
