package shopping.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateProductRequest {
    Long productId;
    String productName;
    int price;
    String imageUrl;
}
