package jw.spring.training.securityexample.service;

import jw.spring.training.securityexample.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface  AppUserService extends UserDetailsService {
    public void addAppUser(AppUser appUser);
}
