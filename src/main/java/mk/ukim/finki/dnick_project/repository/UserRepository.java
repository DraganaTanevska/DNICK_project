package mk.ukim.finki.dnick_project.repository;

import mk.ukim.finki.dnick_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
