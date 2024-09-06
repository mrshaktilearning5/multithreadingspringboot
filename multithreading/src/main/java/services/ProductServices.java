package services;

import dto.Product;
import dto.ProductInfo;
import dto.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServices {

    private ProductInfoServices productInfoService;

    private ReviewServices reviewServices;

    public ProductServices(ProductInfoServices productInfoService, ReviewServices reviewServices) {
        this.productInfoService = productInfoService;
        this.reviewServices = reviewServices;
    }

    public Product retriveProductDetails(String productId) throws InterruptedException {
        Long startTime=System.currentTimeMillis();

        ProductInfo productInfo=productInfoService.retrieveProductInfo(productId);
        Review review=reviewServices.retrieveReviews(productId);

        Long endTime=System.currentTimeMillis();
        Long totalTimeTakenInMilliseconds=endTime-startTime;
        log.info("Total time take : {}" ,totalTimeTakenInMilliseconds);
        return  new Product(productId,productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException {
        String productId="abc123";
        ProductInfoServices productInfoServices=new ProductInfoServices();
        ReviewServices reviewServices1=new ReviewServices();
        ProductServices productServices=new ProductServices(productInfoServices,reviewServices1);
        Product product=productServices.retriveProductDetails(productId);
        log.info("The product is  : {}",product);
    }
}
