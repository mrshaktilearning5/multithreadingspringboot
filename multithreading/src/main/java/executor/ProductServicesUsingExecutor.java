package executor;

import dto.Product;
import dto.ProductInfo;
import dto.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import services.ProductInfoServices;
import services.ReviewServices;

import java.util.concurrent.*;

@Service
@Slf4j
public class ProductServicesUsingExecutor {

    //here we will create static instance of ExecutorService and assign the no of cores in machine
    //TODO: here we have defines as static becasue we will call it from the static main method or no need

    static ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private ProductInfoServices productInfoService;

    private ReviewServices reviewServices;

    public ProductServicesUsingExecutor(ProductInfoServices productInfoService, ReviewServices reviewServices) {
        this.productInfoService = productInfoService;
        this.reviewServices = reviewServices;
    }

    public Product retriveProductDetails(String productId) throws InterruptedException, ExecutionException, TimeoutException {
        Long startTime=System.currentTimeMillis();
        Future<ProductInfo> productInfoFuture=executorService.submit(()->productInfoService.retrieveProductInfo(productId));
        Future<Review> reviewFuture=executorService.submit(()->reviewServices.retrieveReviews(productId));

       // ProductInfo productInfo=productInfoFuture.get();
        // Define for how much time the call should wait for result, so it will wait for 1 seconds
        ProductInfo productInfo=productInfoFuture.get(2, TimeUnit.SECONDS);

        Review review=reviewFuture.get();
        executorService.shutdown();
        Long endTime=System.currentTimeMillis();
        Long totalTimeTakenInMilliseconds=endTime-startTime;
        log.info("Total time take : {}" ,totalTimeTakenInMilliseconds);
        return  new Product(productId,productInfo, review);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        String productId="abc123";
        ProductInfoServices productInfoServices=new ProductInfoServices();
        ReviewServices reviewServices1=new ReviewServices();
        ProductServicesUsingExecutor productServices=new ProductServicesUsingExecutor(productInfoServices,reviewServices1);
        Product product=productServices.retriveProductDetails(productId);
        log.info("The product is  : {}",product);
    }
}
