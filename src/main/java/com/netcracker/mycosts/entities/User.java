package com.netcracker.mycosts.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.List;
import com.netcracker.mycosts.entities.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private boolean active;

    private boolean accepted;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_category",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private final Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private final Set<Account> accounts = new HashSet<>();

    public void addCategory(Category category) {
        categories.add(category);
        category.getUsers().add(this);
    }

    public void removeCategory(Category category) {
        category.getUsers().remove(this);
        categories.remove(category);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addCategories(List<Category> categories) {
        categories.forEach(this::addCategory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
