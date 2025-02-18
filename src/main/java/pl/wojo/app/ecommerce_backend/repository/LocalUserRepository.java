package pl.wojo.app.ecommerce_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.wojo.app.ecommerce_backend.model.LocalUser;

@Repository
public interface LocalUserRepository extends JpaRepository<LocalUser, Long> {
    
}
