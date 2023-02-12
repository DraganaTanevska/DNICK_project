package mk.ukim.finki.dnick_project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Map_User")
public class User implements UserDetails {
    @Id

    private String username;

    private String Name;

    private String Surname;

    private String email;

    private String password;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
    private double testResults;
    @Enumerated(value = EnumType.STRING)

    private Role role;

    public User(String username, String name, String surname, String email, String password, Role role) {
        this.username = username;
        Name = name;
        Surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setTestResults(double testResults) {
        this.testResults = testResults;
    }

    public double getTestResults() {
        return testResults;
    }
}
