package cmu.sem.fridgely.object;

public class FilterObject {
    public String category;
    public String filterTitle;

    public FilterObject(String category, String filterTitle) {
        this.category = category.toLowerCase();
        this.filterTitle = filterTitle.toLowerCase();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }
}
