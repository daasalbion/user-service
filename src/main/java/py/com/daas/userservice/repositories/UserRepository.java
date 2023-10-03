package py.com.daas.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import py.com.daas.userservice.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailEqualsAndIsActiveIsTrue(String email);
}
