package cmu.sem4.listprototype;

public class ApiSettings {
    String query;// Required
    String specificRecipeUrl;// Required
    int indexFrom;
    int indexTo;
    int maxIngredient;
    String[] dietTags;
    String[] healthTags;
    String[] cuisineType;
    String[] mealType;
    String[] dishType;
    int calorieFrom;
    int calorieTo;
    int prepTimeFrom;
    int prepTimeTo;
    String[] exclude;

    // Minimum settings
    public ApiSettings(String query, String specificRecipeUrl) {
        this.query = query;
        this.specificRecipeUrl = specificRecipeUrl;
    }

    // Full settings
    public ApiSettings(String query, String specificRecipeUrl, int indexFrom, int indexTo, int maxIngredient, String[] dietTags, String[] healthTags, String[] cuisineType, String[] mealType, String[] dishType, int calorieFrom, int calorieTo, int prepTimeFrom, int prepTimeTo, String[] exclude) {
        this.query = query;
        this.specificRecipeUrl = specificRecipeUrl;
        this.indexFrom = indexFrom;
        this.indexTo = indexTo;
        this.maxIngredient = maxIngredient;
        this.dietTags = dietTags;
        this.healthTags = healthTags;
        this.cuisineType = cuisineType;
        this.mealType = mealType;
        this.dishType = dishType;
        this.calorieFrom = calorieFrom;
        this.calorieTo = calorieTo;
        this.prepTimeFrom = prepTimeFrom;
        this.prepTimeTo = prepTimeTo;
        this.exclude = exclude;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSpecificRecipeUrl() {
        return specificRecipeUrl;
    }

    public void setSpecificRecipeUrl(String specificRecipeUrl) {
        this.specificRecipeUrl = specificRecipeUrl;
    }

    public int getIndexFrom() {
        return indexFrom;
    }

    public void setIndexFrom(int indexFrom) {
        this.indexFrom = indexFrom;
    }

    public int getIndexTo() {
        return indexTo;
    }

    public void setIndexTo(int indexTo) {
        this.indexTo = indexTo;
    }

    public int getMaxIngredient() {
        return maxIngredient;
    }

    public void setMaxIngredient(int maxIngredient) {
        this.maxIngredient = maxIngredient;
    }

    public String[] getDietTags() {
        return dietTags;
    }

    public void setDietTags(String[] dietTags) {
        this.dietTags = dietTags;
    }

    public String[] getHealthTags() {
        return healthTags;
    }

    public void setHealthTags(String[] healthTags) {
        this.healthTags = healthTags;
    }

    public String[] getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String[] cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String[] getMealType() {
        return mealType;
    }

    public void setMealType(String[] mealType) {
        this.mealType = mealType;
    }

    public String[] getDishType() {
        return dishType;
    }

    public void setDishType(String[] dishType) {
        this.dishType = dishType;
    }

    public int getCalorieFrom() {
        return calorieFrom;
    }

    public void setCalorieFrom(int calorieFrom) {
        this.calorieFrom = calorieFrom;
    }

    public int getCalorieTo() {
        return calorieTo;
    }

    public void setCalorieTo(int calorieTo) {
        this.calorieTo = calorieTo;
    }

    public int getPrepTimeFrom() {
        return prepTimeFrom;
    }

    public void setPrepTimeFrom(int prepTimeFrom) {
        this.prepTimeFrom = prepTimeFrom;
    }

    public int getPrepTimeTo() {
        return prepTimeTo;
    }

    public void setPrepTimeTo(int prepTimeTo) {
        this.prepTimeTo = prepTimeTo;
    }

    public String[] getExclude() {
        return exclude;
    }

    public void setExclude(String[] exclude) {
        this.exclude = exclude;
    }
}
