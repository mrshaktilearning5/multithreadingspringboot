package services;

import dto.Cart;
import dto.CheckoutResponse;
import dto.CheckoutStatus;
import org.junit.jupiter.api.Test;
import util.Utils;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void checkout() {
        Cart cart = Utils.createCart(25);

        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void noOfCores() {
        System.out.println("no of cores =" + Runtime.getRuntime().availableProcessors());
    }
}