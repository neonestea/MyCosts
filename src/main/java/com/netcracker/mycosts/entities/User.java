package com.netcracker.mycosts.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import com.netcracker.mycosts.entities.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User implements Serializable {

    @Id
    /*@GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

  /*  @NotBlank
    private String password;*/
    @JsonFormat(shapt=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;

    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_category",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private final Set<Category> categories = new HashSet<>();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setLastVisit(LocalDateTime lastVisit){
        this.lastVisit = lastVisit;
    }

    public LocalDateTime getLastVisit(){
        return lastVisit;
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.getUsers().add(this);
    }

    public void addCategories(List<Category> categories) {
        categories.forEach(this::addCategory);
    }

}
