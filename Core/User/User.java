package Core.User;

import java.util.ArrayList;
import java.util.List;

import Core.Pizza.Pizza;

public class User {
    private String id;
    private String name;
    private List<Pizza> favorites;
    private int loyaltyPoints;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.favorites = new ArrayList<>();
        this.loyaltyPoints = 0;
    }

    public void addFavorite(Pizza pizza) {
        favorites.add(pizza);
    }

    public void addLoyaltyPoints(double amount) {
        this.loyaltyPoints += (int) (amount / 10) * 3;
    }

    public void deductLoyaltyPoints(int points) {
        this.loyaltyPoints -= points;
    }

    public String getId() {
        return id;
    }

    public List<Pizza> getFavorites() {
        return favorites;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public String getName() {
        return name;
    }
}
