package dev.portfolio.ecommerce.domain;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="carts") public class Cart {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=254) private String customerEmail;
 @Column(nullable=false,length=20) private String status="OPEN";
 @OneToMany(mappedBy="cart",cascade=CascadeType.ALL,orphanRemoval=true) private List<CartItem> items=new ArrayList<>();
 protected Cart(){}
 public Cart(String email){this.customerEmail=email;}
 public void add(Product product,int quantity){
  if(!"OPEN".equals(status)) throw new IllegalStateException("Carrinho fechado");
  if(quantity<=0) throw new IllegalArgumentException("Quantidade inválida");
  for(CartItem item:items) if(item.getProduct().getId().equals(product.getId())) {item.increase(quantity);return;}
  items.add(new CartItem(this,product,quantity));
 }
 public void close(){if(!"OPEN".equals(status))throw new IllegalStateException("Carrinho fechado");status="CHECKED_OUT";}
 public Long getId(){return id;} public String getCustomerEmail(){return customerEmail;} public String getStatus(){return status;}
 public List<CartItem> getItems(){return Collections.unmodifiableList(items);}
}
