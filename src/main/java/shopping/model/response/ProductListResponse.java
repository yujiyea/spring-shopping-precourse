package shopping.model.response;

import lombok.Builder;
import lombok.Getter;
import shopping.entity.Product;

import java.util.List;

@Getter
@Builder
public class ProductListResponse {
    private final List<Product> products;

    public static ProductListResponse of(List<Product> products) {
        return ProductListResponse.builder()
                .products(products)
                .build();
    }
}
