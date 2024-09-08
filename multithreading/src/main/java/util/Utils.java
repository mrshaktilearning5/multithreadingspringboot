package util;

import dto.Cart;
import dto.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Utils {

    public static List<String> namesList()
    {
      return List.of("Bob","Jamie","Jill","Rick");
    }

    public static Cart createCart(int noOfItemsInCart){
        Cart cart=new Cart();
        List<CartItem> cartItemList=new ArrayList<>();

        IntStream.rangeClosed(1,noOfItemsInCart)
                .forEach((index)->{

                    String cartItem= "CartItem -".concat(index+"");
                    CartItem cartItem1=new CartItem(index,cartItem,generateRandomPrice(),index,false);
                    cartItemList.add(cartItem1);
                });
        cart.setCartItemList(cartItemList);
        return cart;
    }

    private static double generateRandomPrice() {
        int min =50;
        int max=100;
        return Math.random() * (max-min+1)+min;
    }

}
