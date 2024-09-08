package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {

    Integer itemId; String itemName; double rate; Integer quantity; Boolean isExpired;
}
