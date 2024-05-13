package site.strangebros.nork.global.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionResponse {
    private int status;
    private String message;
}
