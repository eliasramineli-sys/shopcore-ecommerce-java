package dev.portfolio.ecommerce.api;
import dev.portfolio.ecommerce.api.Requests.*;
import dev.portfolio.ecommerce.api.Responses.*;
import dev.portfolio.ecommerce.service.CommerceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
@RestController @RequestMapping("/api/v1") public class CommerceController {
 private final CommerceService service;
 public CommerceController(CommerceService service){this.service=service;}
 @PostMapping("/products") public ResponseEntity<ProductView> createProduct(@Valid @RequestBody ProductCreate r){ProductView p=service.createProduct(r);return ResponseEntity.created(URI.create("/api/v1/products/"+p.id())).body(p);}
 @GetMapping("/products") public Page<ProductView> products(@PageableDefault(size=20,sort="id") Pageable pageable){return service.products(pageable);}
 @PostMapping("/carts") public ResponseEntity<CartView> createCart(@Valid @RequestBody CartCreate r){CartView c=service.createCart(r);return ResponseEntity.created(URI.create("/api/v1/carts/"+c.id())).body(c);}
 @GetMapping("/carts/{id}") public CartView cart(@PathVariable Long id){return service.cart(id);}
 @PostMapping("/carts/{id}/items") public CartView add(@PathVariable Long id,@Valid @RequestBody CartAdd r){return service.addItem(id,r);}
 @PostMapping("/carts/{id}/checkout") public ResponseEntity<OrderView> checkout(@PathVariable Long id){OrderView o=service.checkout(id);return ResponseEntity.created(URI.create("/api/v1/orders/"+o.id())).body(o);}
 @GetMapping("/orders/{id}") public OrderView order(@PathVariable Long id){return service.order(id);}
}
