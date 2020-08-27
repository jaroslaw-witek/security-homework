package jw.spring.training.securityexample.dto;

import java.util.HashSet;
import java.util.Set;

public class UserRolesDTO {
    private String userName;
    private Set<String> userRoles = new HashSet<>();

    public UserRolesDTO() {
    }

    public UserRolesDTO(String userName, Set<String> userRoles) {
        this.userName = userName;
        this.userRoles = userRoles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }
}
