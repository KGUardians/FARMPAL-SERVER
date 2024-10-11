package farmeasy.server.util.exception;

import farmeasy.server.util.exception.post.experience.MaxParticipantsExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ResourceNotFoundException 처리
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    // 전역 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
    }

    // 유효성 검사 실패 시 처리 (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 잘못된 JSON 요청 처리 (HttpMessageNotReadableException)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        String uri = request.getDescription(false);
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        String errorMessage = mostSpecificCause.getMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청 형식입니다. 요청 URI: " + uri + ", 에러 메시지: " + errorMessage);
    }


    @ExceptionHandler(MaxParticipantsExceededException.class)
    public ResponseEntity<String> handleMaxParticipantsExceededException(MaxParticipantsExceededException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
