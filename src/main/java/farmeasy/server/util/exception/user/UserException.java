package farmeasy.server.util.exception.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class UserException extends RuntimeException{
    private HttpStatus status;
    private String message;
    public UserException(String message,HttpStatus status){
        super(message);
        this.message = message;
        this.status = status;
    }
}
