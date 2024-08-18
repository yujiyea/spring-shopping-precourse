package shopping.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductDto {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    @Builder
    public ProductDto(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
