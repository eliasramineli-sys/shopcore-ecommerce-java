package dev.portfolio.ecommerce.api;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public final class Requests {
 private Requests(){}
 public record ProductCreate(@NotBlank @Size(max=60) String sku,@NotBlank @Size(max=150) String name,
  @NotNull @DecimalMin("0.00") @Digits(integer=10,fraction=2) BigDecimal price,@Min(0) int stock){}
 public record CartCreate(@NotBlank @Email String customerEmail){}
 public record CartAdd(@NotNull @Positive Long productId,@Min(1) int quantity){}
}
