package dev.portfolio.ecommerce.repository;
import dev.portfolio.ecommerce.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<PurchaseOrder,Long>{}
