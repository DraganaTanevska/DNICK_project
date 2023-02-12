package mk.ukim.finki.dnick_project.model;

import org.checkerframework.checker.units.qual.Length;

import javax.persistence.*;

@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String Title;
    @Column(length = 4000)
    private String Text;

    public Long getId()
    {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public String getText() {
        return Text;
    }
    public Lecture(){}
    public Lecture(String title, String text){
        this.Title = title;
        this.Text = text;
    }
}
