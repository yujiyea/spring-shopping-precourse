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
    @Column(length = 15)
    private String name;
    private int price;
    private String image;

    @Builder
    public Product(String name, int price, String image) {
        validateName(name);
        this.name = name;
        this.price = price;
        this.image = image;
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("상품 이름은 필수 입력 사항입니다.");
        }

        if(name.length() > MAX_NAME_LENGTH){
            throw new IllegalArgumentException("상품 이름은 최대 15자까지 입력 가능합니다.");
        }
    }


}
