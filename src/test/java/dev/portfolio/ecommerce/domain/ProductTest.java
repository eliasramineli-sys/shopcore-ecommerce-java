package dev.portfolio.ecommerce.domain;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
class ProductTest {
 @Test void decreasesStock(){Product p=new Product("SKU","Mouse",new BigDecimal("39.90"),10);p.decreaseStock(3);assertEquals(7,p.getStock());}
 @Test void preventsOverselling(){Product p=new Product("SKU","Mouse",BigDecimal.TEN,2);assertThrows(IllegalStateException.class,()->p.decreaseStock(3));assertEquals(2,p.getStock());}
 @Test void rejectsInvalidQuantity(){Product p=new Product("SKU","Mouse",BigDecimal.TEN,2);assertThrows(IllegalArgumentException.class,()->p.decreaseStock(0));}
}
