package by.yankavets.typingtrainer.exception;

public class TrainingDataException extends RuntimeException {

    public TrainingDataException() {
        super();
    }

    public TrainingDataException(String message) {
        super(message);
    }

    public TrainingDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrainingDataException(Throwable cause) {
        super(cause);
    }

}
