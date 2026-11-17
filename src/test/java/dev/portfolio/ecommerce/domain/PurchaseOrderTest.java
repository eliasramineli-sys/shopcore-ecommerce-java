package dev.portfolio.ecommerce.domain;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
class PurchaseOrderTest {
 @Test void calculatesTotalFromPriceSnapshot(){
  Product p=new Product("SKU","Teclado",new BigDecimal("59.90"),8);
  PurchaseOrder order=new PurchaseOrder("cliente@example.com");order.add(p,3);
  assertEquals(0,new BigDecimal("179.70").compareTo(order.getTotal()));
  assertEquals(3,order.getItems().get(0).getQuantity());
 }
}
