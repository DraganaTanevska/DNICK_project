package mk.ukim.finki.dnick_project.model;

import javax.persistence.*;

@Entity
public class UserLectureRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Lecture lecture;
    private boolean status;

    public UserLectureRelation() {}

    public UserLectureRelation(User user, Lecture lecture, boolean status) {
        this.user = user;
        this.lecture = lecture;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
