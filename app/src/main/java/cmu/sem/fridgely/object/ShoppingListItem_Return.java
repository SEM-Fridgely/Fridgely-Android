package cmu.sem.fridgely.object;

import java.util.ArrayList;

public class ShoppingListItem_Return {
    public String id;
    public String name;
    public String userid;
    public ArrayList<ShoppingListItem_Query> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public ArrayList<ShoppingListItem_Query> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShoppingListItem_Query> items) {
        this.items = items;
    }
}
