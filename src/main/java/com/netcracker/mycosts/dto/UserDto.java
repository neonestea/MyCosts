package com.netcracker.mycosts.dto;

import com.netcracker.mycosts.entities.Account;
import com.netcracker.mycosts.entities.Category;
import com.netcracker.mycosts.entities.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
public class UserDto {
    private String id;
    private String name;
    private String email;
    private boolean active;
    private Set<Role> roles;
    private final Set<Category> categories = new HashSet<>();
    private final Set<Account> accounts = new HashSet<>();
}
