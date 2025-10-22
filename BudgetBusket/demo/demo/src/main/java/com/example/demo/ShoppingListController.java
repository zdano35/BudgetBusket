package com.example.demo;

import com.example.demo.model.ShoppingItem;
import com.example.demo.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/shopping")
public class ShoppingListController {

    @Autowired
    private ShoppingListService service;

    // GET /shopping/items
    @GetMapping("/items")
    public ResponseEntity<List<ShoppingItem>> getItems() {
        return ResponseEntity.ok(service.getAllItems());
    }

    // POST /shopping/items
    @PostMapping("/items")
    public ResponseEntity<ShoppingItem> addItem(@RequestBody ShoppingItem item) {
        return ResponseEntity.ok(service.addItem(item));
    }

    // PUT /shopping/items/{id}
    @PutMapping("/items/{id}")
    public ResponseEntity<ShoppingItem> updateItem(@PathVariable Long id, @RequestBody ShoppingItem item) {
        Optional<ShoppingItem> updated = service.updateItem(id, item);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /shopping/items/{id}
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        boolean removed = service.deleteItem(id);
        return removed ? ResponseEntity.ok("Deleted")
                : ResponseEntity.status(404).body("Item not found");
    }

    // GET /shopping/total
    @GetMapping("/total")
    public ResponseEntity<Map<String, Double>> getTotalCost() {
        double total = service.getTotalCost();
        Map<String, Double> response = new HashMap<>();
        response.put("total", total);
        return ResponseEntity.ok(response);
    }

    // GET /shopping/budget
    @GetMapping("/budget")
    public ResponseEntity<Map<String, Object>> getBudgetStatus() {
        double total = service.getTotalCost();
        double budget = service.getBudget();

        Map<String, Object> response = new HashMap<>();
        response.put("budget", budget);
        response.put("total", total);
        response.put("overBudget", total > budget);

        return ResponseEntity.ok(response);
    }

    // POST /shopping/budget
    @PostMapping("/budget")
    public ResponseEntity<String> setBudget(@RequestBody Map<String, Double> request) {
        if (request.containsKey("budget")) {
            double budget = request.get("budget");
            service.setBudget(budget);
            return ResponseEntity.ok("Budget set to: " + budget);
        } else {
            return ResponseEntity.badRequest().body("Missing 'budget' value");
        }
    }
}
