package com.example.webstore.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandContainer {

    private static final Map<String, Command> commands=new HashMap<>();

    static {
        commands.put("login", new LoginCommand());
        commands.put("login-post", new LoginPostCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("main", new MainCommand());
        commands.put("cart", new CartCommand());
        commands.put("cart-post", new CartPostCommand());
        commands.put("cart-delete", new CartDeleteCommand());
        commands.put("mainAddToCart", new MainPostCommand());
        commands.put("admin-panel", new AdminPanelCommand());
        commands.put("admin-panel/orders", new AdminOrdersCommand());
        commands.put("admin-panel/orders-change", new AdminOrdersPostCommand());
        commands.put("admin-panel/products", new AdminProductsCommand());
        commands.put("admin-panel/products-change", new AdminProductsChangeCommand());
        commands.put("admin-panel/products-change-post", new AdminProductsChangePostCommand());
        commands.put("admin-panel/users", new AdminUsersCommand());
        commands.put("admin-panel/users-post", new AdminUsersPostCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("registration-post", new RegistrationPostCommand());
        commands.put("profile", new ProfileCommand());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
