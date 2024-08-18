package shopping.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.entity.Product;
import shopping.model.ProductDto;
import shopping.model.request.CreateProductRequest;
import shopping.model.response.ProductListResponse;
import shopping.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품을 생성한다.")
    void createProduct(){
        String name = "[과자] 엄마손파이";
        int price = 4500;
        String imageUrl = "https://test.com/cookie.jpg";

        ProductDto productDto = productService.createProduct(new CreateProductRequest(name, price, imageUrl));

        assertThat(productDto.getName()).isEqualTo(name);
        assertThat(productDto.getPrice()).isEqualTo(price);
        assertThat(productDto.getImageUrl()).isEqualTo(imageUrl);
    }

    @Test
    @DisplayName("모든 상품의 목록을 조회한다.")
    void getProduct(){
        List<Product> products = Arrays.asList(new Product("쿠키", 1500, "http://cookie.jpg"),
                new Product("크로와상", 3500, "http://croissant.jpg"));

        given(productRepository.findAll()).willReturn(products);

        ProductListResponse results = productService.getAllProducts();

        assertThat(results.getProducts()).isEqualTo(products);
        verify(productRepository, times(1)).findAll();
    }
}