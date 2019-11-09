package cmu.sem.fridgely.object;


import java.util.List;

public class ShoppingListItem_QueryHead {
    public String name;
    public List<ShoppingListItem_Query> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShoppingListItem_Query> getItems() {
        return items;
    }

    public void setItems(List<ShoppingListItem_Query> items) {
        this.items = items;
    }
}
