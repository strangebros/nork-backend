package site.strangebros.nork.global.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class SuccessResponse<D> {

    private int status;
    private String message;
    private D data;

    @Builder
    public SuccessResponse(HttpStatus status, String message, D data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }
}
