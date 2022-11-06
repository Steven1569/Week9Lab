package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;
/**
 *
 * @author Steven
 */
public class UserServlet extends HttpServlet { 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService userService = new UserService();
        RoleService roleService = new RoleService();
        String action = request.getParameter("action");

        try {
            List<User> users = userService.getAllUsers();
            List<Role> roles = roleService.getAllRoles();
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);

            if (action.equals("edit")) {
                request.setAttribute("userSelected", true);

                String email = request.getParameter("email");
                User user = userService.getUser(email);
                request.setAttribute("user", user);
            } else if (action.equals("delete")) {
                String email = request.getParameter("email");
                userService.deleteUser(email);
                users = userService.getAllUsers();
                request.setAttribute("users", users);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService userService = new UserService();
        String action = request.getParameter("action");

        String email = request.getParameter("email");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String password = request.getParameter("password");
        String userRole = request.getParameter("roles");
        
        Role role;
        if (userRole.equals("system admin")) {
            role = new Role(1, "system admin");
        } else {
            role = new Role(2, "regular user");
        }

        try {
            if (userService.isNotEmpty(email, fname, lname, password)) {
                switch (action) {
                    case "add":
                        userService.insertUser(email, fname, lname, password, role);
                        break;
                    case "Update":
                        userService.updateUser(email, fname, lname, password, role);
                        break;
                }
            } else {
                switch (action) {
                    case "add":
                        request.setAttribute("error", "All fields are required");
                        break;
                    case "Update":
                        request.setAttribute("user", userService.getUser(email));
                        request.setAttribute("error", "All fields are required");
                        request.setAttribute("userSelected", true);
                        break;
                    case "Cancel":
                        request.setAttribute("userSelected", false);
                        break;
                }
            }

            List<User> users = userService.getAllUsers();
            request.setAttribute("users", users);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        doGet(request, response);
    }

}
