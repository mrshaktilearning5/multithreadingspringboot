package services;

import dto.CartItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem) throws InterruptedException {
        int cartId=cartItem.getItemId();
        Thread.sleep(500);
        if(cartId==7 || cartId==9 || cartId==11){
            return true;
        }
        return false;
    }
}
