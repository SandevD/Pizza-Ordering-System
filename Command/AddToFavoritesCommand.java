package Command;

import Core.Pizza.Pizza;
import Core.User.User;

public class AddToFavoritesCommand implements OrderCommand {
    private User user;
    private Pizza pizza;

    public AddToFavoritesCommand(User user, Pizza pizza) {
        this.user = user;
        this.pizza = pizza;
    }

    @Override
    public void execute() {
        user.addFavorite(pizza);
    }
}
