package cmu.sem.fridgely.object;

public class ShoppingListItem {
    public String itemTitle;
    public Double quantity;
    public boolean bought;

    public ShoppingListItem(String itemTitle, Double quantity) {
        this.itemTitle = itemTitle;
        this.quantity = quantity;
        this.bought = false;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
