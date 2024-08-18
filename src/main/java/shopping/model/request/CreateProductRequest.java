package shopping.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductRequest {
    @NotNull(message = "이름은 null일 수 없습니다.")
    String name;
    @NotNull(message = "가격은 null일 수 없습니다.")
    int price;
    @NotNull(message = "이미지는 null일 수 없습니다.")
    String imageUrl;
}
