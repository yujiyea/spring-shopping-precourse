package shopping.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "email은 null일 수 없습니다.")
    private final String email;
    @NotNull(message = "password는 null일 수 없습니다.")
    private final String password;
}
