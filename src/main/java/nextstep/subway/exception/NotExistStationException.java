package nextstep.subway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotExistStationException extends IllegalArgumentException {
    public NotExistStationException(String message) {
        super(message);
    }
}
