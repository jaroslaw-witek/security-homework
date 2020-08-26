package jw.spring.training.securityexample.repository;

import jw.spring.training.securityexample.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findAppRoleByName(String name);
}
