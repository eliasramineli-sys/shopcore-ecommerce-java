package dev.portfolio.ecommerce.api;
import dev.portfolio.ecommerce.domain.*;
import java.math.BigDecimal;
import java.util.List;
public final class Responses {
 private Responses(){}
 public record ProductView(Long id,String sku,String name,BigDecimal price,int stock){
  public static ProductView from(Product p){return new ProductView(p.getId(),p.getSku(),p.getName(),p.getPrice(),p.getStock());}
 }
 public record CartLine(Long productId,String name,int quantity){}
 public record CartView(Long id,String customerEmail,String status,List<CartLine> items){
  public static CartView from(Cart c){return new CartView(c.getId(),c.getCustomerEmail(),c.getStatus(),c.getItems().stream().map(i->new CartLine(i.getProduct().getId(),i.getProduct().getName(),i.getQuantity())).toList());}
 }
 public record OrderLine(Long productId,String name,BigDecimal unitPrice,int quantity){}
 public record OrderView(Long id,String customerEmail,String status,BigDecimal total,List<OrderLine> items){
  public static OrderView from(PurchaseOrder o){return new OrderView(o.getId(),o.getCustomerEmail(),o.getStatus(),o.getTotal(),o.getItems().stream().map(i->new OrderLine(i.getProductId(),i.getProductName(),i.getUnitPrice(),i.getQuantity())).toList());}
 }
}
