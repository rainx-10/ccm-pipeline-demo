package com.rain.model;

import java.math.BigDecimal;
import java.util.List;

public class Customer {

    private String id;
    private String name;
    private String email;
    private String dueDate;
    private List<LineItem> items;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public List<LineItem> getItems() { return items; }
    public void setItems(List<LineItem> items) { this.items = items; }

    /** Sum of all line-item prices — used by the template. */
    public BigDecimal getTotal() {
        if (items == null) return BigDecimal.ZERO;
        return items.stream()
                .map(LineItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static class LineItem {
        private String description;
        private BigDecimal price;

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
    }
}