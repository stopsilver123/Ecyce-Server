package com.ecyce.karma.domain.product.service;

import com.ecyce.karma.domain.bookmark.repository.BookmarkRepository;
import com.ecyce.karma.domain.product.dto.request.ModifyOptionRequest;
import com.ecyce.karma.domain.product.dto.request.ModifyProductRequest;
import com.ecyce.karma.domain.product.dto.request.OptionRequest;
import com.ecyce.karma.domain.product.dto.response.OptionResponse;
import com.ecyce.karma.domain.product.dto.response.ProductDetailResponse;
import com.ecyce.karma.domain.product.dto.request.ProductRequest;
import com.ecyce.karma.domain.product.dto.response.ProductSimpleResponse;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import com.ecyce.karma.domain.product.repository.ProductOptionRepository;
import com.ecyce.karma.domain.product.repository.ProductRepository;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.global.exception.CustomException;
import com.ecyce.karma.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final BookmarkRepository bookmarkRepository;

    /* 제품 상세 조회 */
    public ProductDetailResponse getProductDetail(User user , Long productId) {

        Product product = findProduct(productId);

        if (user == null){
            return ProductDetailResponse.from(product , false);
        }

        Boolean isMarked = bookmarkRepository.isBookmarked(productId , user.getUserId());


       return ProductDetailResponse.from(product , isMarked);
    }

    /* 제품 등록 */
    public ProductDetailResponse registerProduct(User user, ProductRequest dto) {
        Product product = ProductRequest.toEntity(user , dto);
        productRepository.save(product);
        // 옵션이 있다면
        if (dto.getOption() != null) {
            for (OptionRequest optionDto : dto.getOption()) {
                ProductOption productOption = optionDto.toEntity(product , optionDto);
                productOptionRepository.save(productOption);
            }
        }

        List<ProductOption> productOptions = productOptionRepository.findByProductId(product.getProductId());
        List<OptionResponse> optionResponseDtos = productOptions.stream()
                .map(productOption -> OptionResponse.from(productOption))
                .collect(Collectors.toList());

        return ProductDetailResponse.of(product , optionResponseDtos);
    }

    /*상품 리스트 조회 */
    public List<ProductSimpleResponse> getProductList(User user) {
        List<Product> productList = productRepository.findAll();
        return getProductSimpleResponses(user, productList);
    }

    /* 특정 작가의 작품 리스트 반환 */
    public List<ProductSimpleResponse> getProductListOfArtist(Long artistId ,User user) {
        List<Product> productList = productRepository.findByUserId(artistId);
        return getProductSimpleResponses(user, productList);
    }

    /* 요청을 한 사용자의 북마크 여부를 표시하기 위한 메서드 */
    private List<ProductSimpleResponse> getProductSimpleResponses(User user, List<Product> productList) {
        if(user == null){ // 로그인하지 않은 사용자
            List<ProductSimpleResponse> productListwithoutBookmark = productList.stream()
                    .map(product -> ProductSimpleResponse.from(product , false))
                    .collect(Collectors.toList());
            return productListwithoutBookmark;
        }
        else { //로그인한 사용자
            List<ProductSimpleResponse> productListwithBookmark = productList.stream()
                    .map(product -> {
                        // 북마크 여부 확인
                        Boolean isMarked = bookmarkRepository.isBookmarked(product.getProductId(), user.getUserId());
                        return ProductSimpleResponse.from(product, isMarked);
                    })
                    .collect(Collectors.toList());
            return productListwithBookmark;
        }
    }


    /* 상품 정보만 수정*/
    public ProductDetailResponse modifyProduct(User user, Long productId , ModifyProductRequest dto) {
        Product product = findProduct(productId);
        // 수정하려는 사용자와 상품의 판매자가 일치하지 않을 때
        if(!user.getUserId().equals(product.getUser().getUserId())){
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }

        product.updateInfo(dto);

        productRepository.save(product);

        List<ProductOption> productOptions = productOptionRepository.findByProductId(product.getProductId());
        List<OptionResponse> optionResponseDtos = productOptions.stream()
                .map(productOption -> OptionResponse.from(productOption))
                .collect(Collectors.toList());

        return ProductDetailResponse.of(product, optionResponseDtos);

    }

    /* 상품 정보 삭제 */
    public void deleteProduct(User user, Long productId) {
        Product product = findProduct(productId);

        // 삭제하려는 사용자와 상품의 판매자가 일치하지 않을 때
        if(!user.getUserId().equals(product.getUser().getUserId())){
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }
        productRepository.delete(product);
    }

    /* 상품 옵션 정보 수정 */
    public OptionResponse modifyOptions(User user, Long productId, Long optionId, ModifyOptionRequest dto) {
        ProductOption productOption = productOptionRepository.findByProductIdAndOptionId(productId , optionId);

        // 수정하려는 사용자와 상품의 판매자가 일치하지 않을 때
        if(!user.getUserId().equals(productOption.getProduct().getUser().getUserId())){
            throw new CustomException(ErrorCode.INVALID_ACCESS);
        }

        productOption.updateOption(dto);

        productOptionRepository.save(productOption);

        OptionResponse optionResponse = OptionResponse.from(productOption);
        return optionResponse;
    }

    /* DB에서 product 찾기 */
    private Product findProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        return  product;
    }


}
