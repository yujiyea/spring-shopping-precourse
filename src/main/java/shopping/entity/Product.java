package shopping.entity;

import jakarta.persistence.*;
import lombok.*;
import shopping.common.entity.BaseEntity;
import shopping.model.ProductDto;
import shopping.model.request.UpdateProductRequest;

import static org.springframework.util.StringUtils.hasText;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product extends BaseEntity {
    private static final int MAX_NAME_LENGTH = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Name name;
    private int price;
    @Embedded
    private Image image;

    @Builder
    public Product(String name, int price, String image) {
        this.name = new Name(name);
        this.price = price;
        this.image = new Image(image);
    }

    public ProductDto toDto() {
        return ProductDto.builder()
                .id(this.id)
                .name(this.name.getName())
                .price(this.price)
                .imageUrl(this.image.getImageUrl())
                .build();
    }

    public void update(UpdateProductRequest updateProductRequest){
        if(updateProductRequest.getProductName() != null && hasText(updateProductRequest.getProductName())){
            this.name = new Name(updateProductRequest.getProductName());
        }
        if(updateProductRequest.getPrice() != 0){
            this.price = updateProductRequest.getPrice();
        }
        if(updateProductRequest.getImageUrl() != null && hasText(updateProductRequest.getImageUrl())){
            this.image = new Image(updateProductRequest.getImageUrl());
        }
    }
}
