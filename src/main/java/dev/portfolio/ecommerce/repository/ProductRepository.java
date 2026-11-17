package dev.portfolio.ecommerce.repository;
import dev.portfolio.ecommerce.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product,Long>{boolean existsBySku(String sku);}
