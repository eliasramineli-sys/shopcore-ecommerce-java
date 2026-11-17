package dev.portfolio.ecommerce.repository;
import dev.portfolio.ecommerce.domain.Cart;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.Optional;
public interface CartRepository extends JpaRepository<Cart,Long>{
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select c from Cart c where c.id = :id")
 Optional<Cart> findLockedById(@Param("id") Long id);
}
