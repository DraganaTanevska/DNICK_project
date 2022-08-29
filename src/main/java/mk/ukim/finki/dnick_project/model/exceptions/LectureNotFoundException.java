package mk.ukim.finki.dnick_project.model.exceptions;

public class LectureNotFoundException extends RuntimeException {
    public LectureNotFoundException(Long id) {
        super(String.format("Lecture with id: %s not found", id));
    }
}
