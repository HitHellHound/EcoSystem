package ecxeption;

public class WrongDataException extends Exception {
    public WrongDataException() {
    }

    public WrongDataException(String message) {
        super(message);
    }
}
