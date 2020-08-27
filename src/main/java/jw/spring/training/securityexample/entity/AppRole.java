package jw.spring.training.securityexample.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
//@Table("app_roles")
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<AppUser> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AppRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
