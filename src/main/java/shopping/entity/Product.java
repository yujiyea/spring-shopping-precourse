package shopping.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {
    private static final int MAX_NAME_LENGTH = 15;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
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
}
