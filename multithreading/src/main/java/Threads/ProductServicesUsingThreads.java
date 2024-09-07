package Threads;

import dto.Product;
import dto.ProductInfo;
import dto.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import services.ProductInfoServices;
import services.ReviewServices;

@Service
@Slf4j
public class ProductServicesUsingThreads {

    private ProductInfoServices productInfoService;

    private ReviewServices reviewServices;

    public ProductServicesUsingThreads(ProductInfoServices productInfoService, ReviewServices reviewServices) {
        this.productInfoService = productInfoService;
        this.reviewServices = reviewServices;
    }

    public Product retriveProductDetails(String productId) throws InterruptedException {
        Long startTime=System.currentTimeMillis();
        ProductInfoRunnable productInfoRunnable=new ProductInfoRunnable(productId);
        Thread productInfoThread=new Thread(productInfoRunnable);
        productInfoThread.start();

        ReviewRunnable reviewRunnable=new ReviewRunnable(productId);
        Thread reviewThread=new Thread(reviewRunnable);
        reviewThread.start();

        //now join method will wait until computation is completed

        productInfoThread.join();
        reviewThread.join();

        // now get the result
        ProductInfo productInfo=productInfoRunnable.getProductInfo();
        Review review=reviewRunnable.getReview();


        Long endTime=System.currentTimeMillis();
        Long totalTimeTakenInMilliseconds=endTime-startTime;
        log.info("Total time take : {}" ,totalTimeTakenInMilliseconds);
        return  new Product(productId,productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException {
        String productId="abc123";
        ProductInfoServices productInfoServices=new ProductInfoServices();
        ReviewServices reviewServices1=new ReviewServices();
        ProductServicesUsingThreads productServices=new ProductServicesUsingThreads(productInfoServices,reviewServices1);
        Product product=productServices.retriveProductDetails(productId);
        log.info("The product is  : {}",product);
    }

    private class ProductInfoRunnable implements Runnable {

        private ProductInfo productInfo;

        private String productId;
        public ProductInfoRunnable(String productId) {
            this.productId=productId;
        }

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        @Override
        public void run() {
            try {
                productInfo=productInfoService.retrieveProductInfo(productId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class ReviewRunnable implements Runnable{
        private Review review;
        private String productId;
        public ReviewRunnable(String productId) {
            this.productId=productId;
        }

        public Review getReview() {
            return review;
        }

        @Override
        public void run() {
            try {
                review=reviewServices.retrieveReviews(productId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
