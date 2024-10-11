package farmeasy.server.util.exception.post.experience;

public class MaxParticipantsExceededException extends RuntimeException {
    public MaxParticipantsExceededException(String message) {
        super(message);
    }
}
