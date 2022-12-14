package mk.ukim.finki.dnick_project.service.impl;

import mk.ukim.finki.dnick_project.model.Lecture;
import mk.ukim.finki.dnick_project.model.Role;
import mk.ukim.finki.dnick_project.model.User;
import mk.ukim.finki.dnick_project.model.UserLectureRelation;
import mk.ukim.finki.dnick_project.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.dnick_project.repository.LectureRepository;
import mk.ukim.finki.dnick_project.repository.UserLectureRelationRepository;
import mk.ukim.finki.dnick_project.repository.UserRepository;
import mk.ukim.finki.dnick_project.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LectureRepository lectureRepository;
    private final UserLectureRelationRepository userLectureRelationRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, LectureRepository lectureRepository, UserLectureRelationRepository userLectureRelationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.lectureRepository = lectureRepository;
        this.userLectureRelationRepository = userLectureRelationRepository;
    }

    @Override
    public User Register(String username, String name, String surname, String email, String password, Role role) {
        User exist = userRepository.findByUsername(username).orElse(null);

        if (exist != null) {
            throw new UsernameAlreadyExistsException(username);
        }
        User newUser = new User(username, name, surname, email, passwordEncoder.encode(password), role);
        User user = this.userRepository.save(newUser);

        for (Lecture lecture:
                lectureRepository.findAll()) {
            UserLectureRelation userLectureRelation = new UserLectureRelation(newUser, lecture, false);
            userLectureRelationRepository.save(userLectureRelation);
        }
        return user;
    }

    @Override
    public boolean CheckIfExistLogin(String username, String password) {
        User existingUser = this.userRepository.findByUsernameAndPassword(username, password);
        return existingUser != null;
    }

    public User Login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void DeleteUser(String username) {
        userRepository.delete(userRepository.getById(username));
    }

    @Override
    public User findUserByUsername(String userId) {
        return userRepository.findByUsername(userId).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(s));
    }

}