package jw.spring.training.securityexample;

import jw.spring.training.securityexample.entity.AppRole;
import jw.spring.training.securityexample.entity.AppUser;
import jw.spring.training.securityexample.filter.JwtFilter;
import jw.spring.training.securityexample.repository.AppRoleRepository;
import jw.spring.training.securityexample.repository.AppUserRepository;
import jw.spring.training.securityexample.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecConfiguration extends WebSecurityConfigurerAdapter {

//    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final AppUserService appUserService;

    @Autowired
    public SecConfiguration(AppRoleRepository appRoleRepository, AppUserService appUserService) {
        this.appRoleRepository = appRoleRepository;
        this.appUserService = appUserService;
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        populateRoles();
        populateUsers();
        auth.userDetailsService(appUserService);
//        auth.userDetailsService(new UserDetailsService() {
//
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                AppUser appUser = appUserRepository.findByName(username).get();
//                return  appUser;
//            }
//        });



    }

    private void populateRoles(){
        AppRole appRoleAdmin = new AppRole();
        appRoleAdmin.setName("ROLE_ADMIN");
        appRoleRepository.save(appRoleAdmin);

        AppRole appRoleUser = new AppRole();
        appRoleUser.setName("ROLE_USER");
        appRoleRepository.save(appRoleUser);

        AppRole appRoleVIP = new AppRole();
        appRoleVIP.setName("ROLE_VIP");
        appRoleRepository.save(appRoleVIP);
    }

    private void populateUsers(){
        AppUser appUser = new AppUser();

        appUser.setName("user2");
        appUser.setPassword(getPasswordEncoder().encode("user2"));
        appUser.addRole(appRoleRepository.findAppRoleByName("ROLE_USER").get());
        appUserService.addAppUser(appUser);

        AppUser appUserAdmin = new AppUser();
        appUserAdmin.setName("admin2");
        appUserAdmin.setPassword(getPasswordEncoder().encode("admin2"));
        appUserAdmin.addRole(appRoleRepository.findAppRoleByName("ROLE_ADMIN").get());
        appUserAdmin.addRole(appRoleRepository.findAppRoleByName("ROLE_VIP").get());
        appUserService.addAppUser(appUserAdmin);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .antMatchers("/user/hello").hasRole("USER")
                .antMatchers("/admin/hello").hasAuthority("ROLE_ADMIN")
                .antMatchers("/vip/hello").hasRole("VIP")
                .anyRequest().permitAll()
                .and().formLogin().permitAll()
                .and().logout()
                .and().addFilterBefore(new JwtFilter(appUserService), UsernamePasswordAuthenticationFilter.class);;

    }
}
