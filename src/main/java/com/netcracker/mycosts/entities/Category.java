package com.netcracker.mycosts.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        if (users != null) {
            users.add(user);
        } else {
            System.out.println("pizdec 2");
        }
        user.getCategories().add(this);
    }
}
