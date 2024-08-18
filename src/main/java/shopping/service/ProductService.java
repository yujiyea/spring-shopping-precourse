package shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.entity.Product;
import shopping.model.ProductDto;
import shopping.model.request.CreateProductRequest;
import shopping.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto createProduct(CreateProductRequest createProductRequest) {
        final Product product = Product.builder()
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .image(createProductRequest.getImageUrl())
                .build();

        productRepository.save(product);

        return product.toDto();
    }
}
