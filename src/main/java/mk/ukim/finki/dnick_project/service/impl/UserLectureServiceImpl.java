package mk.ukim.finki.dnick_project.service.impl;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.model.UserLectureRelation;
import mk.ukim.finki.dnick_project.model.exceptions.LectureNotFoundException;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.repository.UserLectureRelationRepository;
import mk.ukim.finki.dnick_project.repository.UserRepository;
import mk.ukim.finki.dnick_project.service.UserLectureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public Page<UserLectureRelation> findAllByUsername(String userId, Integer page) {
        Page<UserLectureRelation> userLectureRelations = userLectureRelationRepository.findAllByUser(userRepository.findByUsername(userId).orElse(null),PageRequest.of(page-1,4, Sort.by("lecture")));
        return userLectureRelations;
    }


    @Override
    public void setStatus(String userId, Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(()-> new LectureNotFoundException(lectureId));
        User user = userRepository.findByUsername(userId).orElse(null);
        if(user!=null)
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
        UserLectureRelation userLectureRelation = userLectureRelationRepository.findAllByLectureAndUser(lectureRepository.findById(lectureId).orElseThrow(()-> new LectureNotFoundException(lectureId)),userRepository.findByUsername(userId).orElse(null)).stream().findFirst().orElse(null);
        if(userLectureRelation != null)
        {
            return userLectureRelation.getStatus();
        }
        return false;
    }
}
