package dev.portfolio.ecommerce.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
@Entity @Table(name="orders") public class PurchaseOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=254) private String customerEmail;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal total=BigDecimal.ZERO;
 @Column(nullable=false,length=20) private String status="PLACED";
 @Column(nullable=false) private Instant createdAt=Instant.now();
 @OneToMany(mappedBy="order",cascade=CascadeType.ALL) private List<OrderItem> items=new ArrayList<>();
 protected PurchaseOrder(){}
 public PurchaseOrder(String email){customerEmail=email;}
 public void add(Product product,int quantity){
  if(quantity<=0)throw new IllegalArgumentException("Quantidade inválida");
  items.add(new OrderItem(this,product,quantity));
  total=total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
 }
 public Long getId(){return id;} public String getCustomerEmail(){return customerEmail;}
 public BigDecimal getTotal(){return total;} public String getStatus(){return status;}
 public Instant getCreatedAt(){return createdAt;} public List<OrderItem> getItems(){return Collections.unmodifiableList(items);}
}
