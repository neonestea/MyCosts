package com.netcracker.mycosts.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user_categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;
    private boolean isDefault;

    private int nameHash;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private final Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.getCategories().add(this);
    }
}
