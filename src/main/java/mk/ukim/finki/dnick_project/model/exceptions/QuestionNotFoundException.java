package mk.ukim.finki.dnick_project.model.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super(String.format("Question with id: %s not found", id));
    }
}

