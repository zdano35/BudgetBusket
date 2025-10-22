package com.example.demo.service;

import com.example.demo.model.ShoppingItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {
    private final List<ShoppingItem> items = new ArrayList<>();
    private double budget = 100.0; // domyślny budżet
    private long currentId = 1;

    public List<ShoppingItem> getAllItems() {
        return items;
    }

    public ShoppingItem addItem(ShoppingItem item) {
        item.setId(currentId++);
        items.add(item);
        return item;
    }

    public Optional<ShoppingItem> updateItem(Long id, ShoppingItem updatedItem) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(existing -> {
                    existing.setName(updatedItem.getName());
                    existing.setPrice(updatedItem.getPrice());

                    // NOWE POLA:
                    existing.setDescription(updatedItem.getDescription());
                    existing.setExpirationDate(updatedItem.getExpirationDate());

                    return existing;
                });
    }

    public boolean deleteItem(Long id) {
        return items.removeIf(item -> item.getId().equals(id));
    }

    public double getTotalCost() {
        return items.stream().mapToDouble(ShoppingItem::getPrice).sum();
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public boolean isOverBudget() {
        return getTotalCost() > budget;
    }
}
