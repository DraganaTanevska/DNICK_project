package mk.ukim.finki.dnick_project.model.exceptions;

public class InvalidUsernameOrPassword extends RuntimeException {
    public InvalidUsernameOrPassword() {
        super(String.format("Invalid username or password"));
    }
}
