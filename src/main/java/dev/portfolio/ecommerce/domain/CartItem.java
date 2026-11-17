package dev.portfolio.ecommerce.domain;
import jakarta.persistence.*;
@Entity @Table(name="cart_items") public class CartItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Cart cart;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Product product;
 @Column(nullable=false) private int quantity;
 protected CartItem(){}
 CartItem(Cart cart,Product product,int quantity){this.cart=cart;this.product=product;this.quantity=quantity;}
 void increase(int amount){quantity=Math.addExact(quantity,amount);}
 public Product getProduct(){return product;} public int getQuantity(){return quantity;}
}
