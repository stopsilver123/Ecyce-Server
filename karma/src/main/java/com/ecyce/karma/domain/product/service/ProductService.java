package com.ecyce.karma.domain.product.service;

import com.ecyce.karma.domain.product.dto.request.OptionRequestDto;
import com.ecyce.karma.domain.product.dto.response.ProductDetailResponse;
import com.ecyce.karma.domain.product.dto.request.ProductRequestDto;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import com.ecyce.karma.domain.product.repository.ProductOptionRepository;
import com.ecyce.karma.domain.product.repository.ProductRepository;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.global.exception.CustomException;
import com.ecyce.karma.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    /* 제품 상세 조회 */
    public ProductDetailResponse getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

       return ProductDetailResponse.from(product);
    }

    /* 제품 등록 */
    public ProductDetailResponse registerProduct(User user, ProductRequestDto dto) {
        Product product = ProductRequestDto.toEntity(user , dto);
        productRepository.save(product);
        // 옵션이 있다면
        if (dto.getOption() != null) {
            for (OptionRequestDto optionDto : dto.getOption()) {
                ProductOption productOption = optionDto.toEntity(product , optionDto);
                productOptionRepository.save(productOption);
            }
        }

        return ProductDetailResponse.from(product);
    }
}
