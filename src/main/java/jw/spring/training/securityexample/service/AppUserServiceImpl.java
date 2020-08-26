package jw.spring.training.securityexample.service;

import jw.spring.training.securityexample.entity.AppUser;
import jw.spring.training.securityexample.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{
    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByName(username).get();
    }

    public void addAppUser(AppUser appUser){
        appUserRepository.save(appUser);
    }

//    public Optional<AppUser> getAppUserByName(String name){
//        return appUserRepository.findByName(name);
//    }
}
