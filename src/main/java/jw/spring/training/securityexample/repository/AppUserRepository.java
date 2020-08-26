package jw.spring.training.securityexample.repository;

import jw.spring.training.securityexample.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByName(String name);
}
