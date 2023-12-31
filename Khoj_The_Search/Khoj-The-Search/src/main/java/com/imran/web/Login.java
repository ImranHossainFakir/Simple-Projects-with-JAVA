package com.imran.web;

import com.imran.domain.User;
import com.imran.dto.LoginDTO;
import com.imran.exceptions.UserNotFoundException;
import com.imran.repository.JDBCUserRepositoryImpl;
import com.imran.service.UserService;
import com.imran.service.UserServiceImpl;
import com.imran.util.SecurityContext;
import com.imran.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Login.class);
    private UserService userService
            = new UserServiceImpl(new JDBCUserRepositoryImpl());
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Serving login page");
        String logout = req.getParameter("logout");

        if (Boolean.parseBoolean(logout))
            req.setAttribute("message", "You have been successfully logged out.");

        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/login.jsp");
        rd.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        var loginDTO
                = new LoginDTO(req.getParameter("username"),
                               req.getParameter("password"));
        var errors
                = ValidationUtil.getInstance().validate(loginDTO);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp")
                    .forward(req, resp);
        }

        try {
            login(loginDTO, req);
            resp.sendRedirect("/home");
        } catch (UserNotFoundException e) {

            errors.put("username", "Incorrect username/password");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp")
                    .forward(req, resp);
        }
    }

    private void login(LoginDTO loginDTO,
                       HttpServletRequest req)
            throws UserNotFoundException {

        User user = userService.verifyUser(loginDTO);
        SecurityContext.login(req, user);
    }
}
