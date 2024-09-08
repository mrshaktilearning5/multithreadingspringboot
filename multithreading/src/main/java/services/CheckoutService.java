package services;

import dto.Cart;
import dto.CartItem;
import dto.CheckoutResponse;
import dto.CheckoutStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CheckoutService {

    private PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart){
        Long startTime=System.currentTimeMillis();
        List<CartItem> cartItemList=cart.getCartItemList()
               // .stream()
                .parallelStream()
                .map(cartItem -> {
                    try {
                        boolean isPriceInvalid=priceValidatorService.isCartItemInvalid(cartItem);
                        cartItem.setIsExpired(isPriceInvalid);
                        return cartItem;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(cartItem -> cartItem.getIsExpired())
                .collect(Collectors.toList());
        Long endTime=System.currentTimeMillis();
        Long totalTimeTakenInMilliseconds=endTime-startTime;
        log.info("Total time take : {}" ,totalTimeTakenInMilliseconds);
        if(cartItemList.size()>0){
            return new CheckoutResponse(CheckoutStatus.FAILURE,cartItemList);
        }

        return new CheckoutResponse(CheckoutStatus.SUCCESS);

    }

}
