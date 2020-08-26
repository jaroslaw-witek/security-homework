package jw.spring.training.securityexample;

import jw.spring.training.securityexample.entity.AppRole;
import jw.spring.training.securityexample.entity.AppUser;
import jw.spring.training.securityexample.repository.AppRoleRepository;
import jw.spring.training.securityexample.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecConfiguration extends WebSecurityConfigurerAdapter {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;

    @Autowired
    public SecConfiguration(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        User user = new User("user1", getPasswordEncoder().encode("user1"), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
//        User admin = new User("admin1", "{noop}admin1", Arrays.asList(
//                new SimpleGrantedAuthority("ROLE_USER"),
//                new SimpleGrantedAuthority("ROLE_ADMIN")
//        ));

//        auth.inMemoryAuthentication()
//                .withUser(user);
//
//        auth.inMemoryAuthentication()
//                .withUser("admin1").password("{noop}admin1")
//                .roles("ADMIN", "USER");
        populateRoles();
        populateUsers();

        auth.userDetailsService(new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser appUser = appUserRepository.findByName(username);
                return  appUser;
            }
        });



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
        appUserRepository.save(appUser);

        AppUser appUserAdmin = new AppUser();
        appUserAdmin.setName("admin2");
        appUserAdmin.setPassword(getPasswordEncoder().encode("admin2"));
        appUserAdmin.addRole(appRoleRepository.findAppRoleByName("ROLE_ADMIN").get());
        appUserAdmin.addRole(appRoleRepository.findAppRoleByName("ROLE_VIP").get());
        appUserRepository.save(appUserAdmin);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .antMatchers("/user/hello").hasRole("USER")
                .antMatchers("/admin/hello").hasAuthority("ROLE_ADMIN")
                .antMatchers("/vip/hello").hasRole("VIP")
                .anyRequest().permitAll()
//                .and().formLogin().loginPage("/login").permitAll() - wskazanie nie przygotowana, niedomyslna strone
                .and().formLogin().permitAll()
                .and().logout();

    }
}
