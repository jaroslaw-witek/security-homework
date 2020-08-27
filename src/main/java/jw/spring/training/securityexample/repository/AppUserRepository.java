package jw.spring.training.securityexample.repository;

import jw.spring.training.securityexample.dto.UserRolesDTO;
import jw.spring.training.securityexample.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    public Optional<AppUser> findByName(String name);

//    @Query("SELECT new jw.spring.training.securityexample.dto.UserRoleDTO(usr.name, role.name)" +
//            "FROM ")
//    public UserRolesDTO getUserDTO(String name);
}
