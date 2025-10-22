package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class ShoppingBudgetService {

    private int budget = 0;

    public void setBudget(int amount) {
        this.budget = amount;
    }

    public int getBudget() {
        return budget;
    }
}
