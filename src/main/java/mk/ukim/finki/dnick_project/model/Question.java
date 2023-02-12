package mk.ukim.finki.dnick_project.model;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String question;
    private String answer;
    @ManyToOne
    Lecture lecture;

    public Question(){}
    public Question(String question, String answer, Lecture lecture) {
        this.question = question;
        this.answer = answer;
        this.lecture = lecture;
    }

    public Question(Long Id, String question, String answer, Lecture lecture) {
        this.Id = Id;
        this.question = question;
        this.answer = answer;
        this.lecture = lecture;
    }

    public Long getId() {
        return Id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Lecture getLecture() {
        return lecture;
    }
}
