package shopping.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.common.exception.NotFoundException;
import shopping.entity.Product;
import shopping.model.ProductDto;
import shopping.model.request.CreateProductRequest;
import shopping.model.request.UpdateProductRequest;
import shopping.model.response.ProductListResponse;
import shopping.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void getProductList(){
        List<Product> products = Arrays.asList(new Product("쿠키", 1500, "http://cookie.jpg"),
                new Product("크로와상", 3500, "http://croissant.jpg"));

        given(productRepository.findAll()).willReturn(products);

        ProductListResponse results = productService.getAllProducts();

        assertThat(results.getProducts()).isEqualTo(products);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("특정 상품을 조회한다.")
    void getProductById(){
        Long productId = 1L;
        Product product = Product.builder()
                .name("쿠키")
                .price(1500)
                .image("http://cookie.jpg")
                .build();

        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        ProductDto result = productService.getProductById(productId);

        assertThat(result.getName()).isEqualTo(product.getName().getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    @DisplayName("특정 상품 조회 실패")
    void getProductById_Fail(){
        Long productId = 1L;
        Product product = Product.builder()
                .name("쿠키")
                .price(1500)
                .image("http://cookie.jpg")
                .build();

        given(productRepository.findById(productId)).willReturn(Optional.empty());

        assertThatThrownBy(() ->productService.getProductById(productId)).isInstanceOf(NotFoundException.class);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    @DisplayName("상품 수정")
    void updateProduct(){
        Long productId = 1L;
        Product product = Product.builder()
                .name("쿠키")
                .price(1500)
                .image("http://cookie.jpg")
                .build();

        Product updateProduct = Product.builder()
                .name("쿠키")
                .price(1800)
                .image("http://cookie.jpg")
                .build();

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(productRepository.save(product)).willReturn(updateProduct);

        ProductDto productDto = productService.updateProduct(UpdateProductRequest.builder().productId(1L).price(1800).build());

        assertEquals(productDto.getPrice(), updateProduct.getPrice());
    }

    @Test
    @DisplayName("상품 수정 실패")
    void updateProduct_Fail(){
        Long productId = 1L;
        Product product = Product.builder()
                .name("쿠키")
                .price(1500)
                .image("http://cookie.jpg")
                .build();

        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        assertThatThrownBy(() ->productService.updateProduct(UpdateProductRequest.builder().productId(1L)
                .productName("kkkkkkkkkkkkkkkkk").build())).isInstanceOf(IllegalArgumentException.class);
    }
}