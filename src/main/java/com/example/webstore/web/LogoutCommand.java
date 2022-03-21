package com.example.webstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.example.webstore.controller.Path.PAGE_LOGOUT;
import static com.example.webstore.controller.Path.PAGE_MAIN;

public class LogoutCommand extends Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        session.setAttribute("cart", null);
        session.setAttribute("userRole", null);
        return CommandContainer.get("main").execute(request, response);
    }
}
