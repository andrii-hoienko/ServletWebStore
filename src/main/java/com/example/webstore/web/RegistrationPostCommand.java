package com.example.webstore.web;

import com.example.webstore.exception.UserException;
import com.example.webstore.model.Role;
import com.example.webstore.model.User;
import com.example.webstore.model.dto.UserDTO;
import com.example.webstore.model.dto.Validator;
import com.example.webstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.example.webstore.controller.Path.*;

public class RegistrationPostCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        String email = request.getParameter("email");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String password = request.getParameter("password");
        Role role = Role.USER;
        User user;
        UserService service = UserService.getInstance();
        try {
            user = service.findUserByEmail(email);
        } catch (UserException e) {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            userDTO.setFirstName(firstName);
            userDTO.setLastName(lastName);
            userDTO.setPassword(password);
            List<String> validationList = Validator.validate(userDTO);
            if (validationList.size() != 0) {
                request.setAttribute("validationErrors", validationList);
                return PAGE_REGISTRATION;
            }
            try {
                service.create(userDTO);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return PAGE_LOGIN;
        }
        return PAGE_REGISTRATION;
    }
}