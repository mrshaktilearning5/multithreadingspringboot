package services;

import dto.ProductInfo;
import dto.ProductOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServices {
    public ProductInfo retrieveProductInfo(String productId) throws InterruptedException {
        Thread.sleep(1000);
        List<ProductOptions> productOptionsList=List.of(new ProductOptions("1","64GB","Red","1000"),
                new ProductOptions("2","128GB","white","2000"));
        return new ProductInfo(productId,productOptionsList);
    }
}
