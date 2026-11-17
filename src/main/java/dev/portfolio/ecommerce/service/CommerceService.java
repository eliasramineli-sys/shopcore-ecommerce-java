package dev.portfolio.ecommerce.service;
import dev.portfolio.ecommerce.api.Requests.*;
import dev.portfolio.ecommerce.api.Responses.*;
import dev.portfolio.ecommerce.domain.*;
import dev.portfolio.ecommerce.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service public class CommerceService {
 private final ProductRepository products;private final CartRepository carts;private final OrderRepository orders;
 public CommerceService(ProductRepository products,CartRepository carts,OrderRepository orders){this.products=products;this.carts=carts;this.orders=orders;}
 @Transactional public ProductView createProduct(ProductCreate r){
  if(products.existsBySku(r.sku().trim()))throw new IllegalArgumentException("SKU já existe");
  return ProductView.from(products.saveAndFlush(new Product(r.sku().trim(),r.name().trim(),r.price(),r.stock())));
 }
 @Transactional(readOnly=true) public Page<ProductView> products(Pageable pageable){return products.findAll(pageable).map(ProductView::from);}
 @Transactional public CartView createCart(CartCreate r){return CartView.from(carts.saveAndFlush(new Cart(r.customerEmail().trim())));}
 @Transactional public CartView addItem(Long cartId,CartAdd r){
  Cart cart=carts.findLockedById(cartId).orElseThrow(()->new NoSuchElementException("Carrinho não encontrado"));
  Product product=products.findById(r.productId()).orElseThrow(()->new NoSuchElementException("Produto não encontrado"));
  cart.add(product,r.quantity());return CartView.from(carts.saveAndFlush(cart));
 }
 @Transactional(readOnly=true) public CartView cart(Long id){return CartView.from(carts.findById(id).orElseThrow(()->new NoSuchElementException("Carrinho não encontrado")));}
 @Transactional public OrderView checkout(Long cartId){
  Cart cart=carts.findLockedById(cartId).orElseThrow(()->new NoSuchElementException("Carrinho não encontrado"));
  if(!"OPEN".equals(cart.getStatus()))throw new IllegalStateException("Carrinho já finalizado");
  if(cart.getItems().isEmpty())throw new IllegalStateException("Carrinho vazio");
  PurchaseOrder order=new PurchaseOrder(cart.getCustomerEmail());
  // Ordenacao deterministica; conflitos simultaneos entre produtos sao detectados por @Version.
  var sorted=cart.getItems().stream().sorted(Comparator.comparing(i->i.getProduct().getId())).toList();
  for(CartItem item:sorted){
   Product product=products.findById(item.getProduct().getId()).orElseThrow(()->new NoSuchElementException("Produto não encontrado"));
   product.decreaseStock(item.getQuantity());
   order.add(product,item.getQuantity());
  }
  products.flush();cart.close();carts.saveAndFlush(cart);
  return OrderView.from(orders.saveAndFlush(order));
 }
 @Transactional(readOnly=true) public OrderView order(Long id){return OrderView.from(orders.findById(id).orElseThrow(()->new NoSuchElementException("Pedido não encontrado")));}
}
