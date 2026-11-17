package dev.portfolio.ecommerce.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="products")
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=60) private String sku;
 @Column(nullable=false,length=150) private String name;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal price;
 @Column(nullable=false) private int stock;
 @Version private long version;
 protected Product() {}
 public Product(String sku,String name,BigDecimal price,int stock) {
  if(sku==null||sku.isBlank()||name==null||name.isBlank()||price==null||price.signum()<0||stock<0) throw new IllegalArgumentException("Produto inválido");
  this.sku=sku;this.name=name;this.price=price;this.stock=stock;
 }
 public void decreaseStock(int quantity) {
  if(quantity<=0) throw new IllegalArgumentException("Quantidade inválida");
  if(stock<quantity) throw new IllegalStateException("Estoque insuficiente");
  stock-=quantity;
 }
 public Long getId(){return id;} public String getSku(){return sku;} public String getName(){return name;}
 public BigDecimal getPrice(){return price;} public int getStock(){return stock;}
}
