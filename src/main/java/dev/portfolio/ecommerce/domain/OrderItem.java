package dev.portfolio.ecommerce.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="order_items") public class OrderItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private PurchaseOrder order;
 @Column(nullable=false) private Long productId;
 @Column(nullable=false,length=150) private String productName;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal unitPrice;
 @Column(nullable=false) private int quantity;
 protected OrderItem(){}
 OrderItem(PurchaseOrder order,Product product,int quantity){
  this.order=order;this.productId=product.getId();this.productName=product.getName();this.unitPrice=product.getPrice();this.quantity=quantity;
 }
 public Long getProductId(){return productId;} public String getProductName(){return productName;}
 public BigDecimal getUnitPrice(){return unitPrice;} public int getQuantity(){return quantity;}
}
