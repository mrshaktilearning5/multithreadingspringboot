package dto;

import java.util.List;

public record ProductInfo(String productId, List<ProductOptions> productOptionsList) {
}
