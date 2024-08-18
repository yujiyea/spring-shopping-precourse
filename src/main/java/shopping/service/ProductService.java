package shopping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.NotFoundException;
import shopping.entity.Product;
import shopping.model.ProductDto;
import shopping.model.request.CreateProductRequest;
import shopping.model.request.UpdateProductRequest;
import shopping.model.response.ProductListResponse;
import shopping.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductDto createProduct(CreateProductRequest createProductRequest) {
        final Product product = Product.builder()
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .image(createProductRequest.getImageUrl())
                .build();

        productRepository.save(product);

        return product.toDto();
    }

    @Transactional(readOnly = true)
    public ProductListResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductListResponse.of(products);
    }

    @Transactional(readOnly = true)
    public ProductDto getProductById(Long productId) {
        final Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));
        return product.toDto();
    }

    @Transactional
    public ProductDto updateProduct(UpdateProductRequest updateProductRequest) {
        final Product product = productRepository.findById(updateProductRequest.getProductId()).orElseThrow(()-> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        product.update(updateProductRequest);

        return product.toDto();
    }
}
