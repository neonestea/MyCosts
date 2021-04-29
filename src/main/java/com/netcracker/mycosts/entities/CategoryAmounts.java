package com.netcracker.mycosts.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAmounts {
    private String categoryName;
    private List<Double> amounts = new ArrayList<>();
}
