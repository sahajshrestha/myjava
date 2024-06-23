package com.myjava.myjava;

import com.myjava.myjava.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SessionController {

    /**
     * PRO-TIP:
     * We could have had a global class level variable here like:
     * public User sharedAttribute;
     * This will work fine if a single user is using the application.
     * However, in a multi-user environment, the following situation will occur:
     * SessionController is singleton and will have only one instance and the sharedAttribute
     * value will be shared amongst all user. If a user logs in first, and another user logs in second,
     * the second user will override the value for the first user.
     * In that regard, HTTPSession are like a global class level variable but with the important distinction.
     * */
    private static final String SESSION_USER_KEY = "authenticatedUser";

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // Simulate authentication process (replace with your actual authentication logic)
        if (
                ("john_doe".equals(username) && "password123".equals(password))
                        || ("sam".equals(username) && "pwd123".equals(password))
        )
        {
            User user = new User();
            user.setUsername(username);
            user.setRole("USER"); // Example: Set user role based on authentication
            session.setAttribute(SESSION_USER_KEY, user);
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logout successful!";
    }

    @GetMapping("/profile")
    public String getProfile(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_KEY);
        if (user != null) {
            return "Username: " + user.getUsername() + ", Role: " + user.getRole();
        } else {
            return "User not authenticated!";
        }
    }

    @GetMapping("/admin")
    public String adminPage(HttpSession session) {
        User user = (User) session.getAttribute(SESSION_USER_KEY);
        if (user != null && "ADMIN".equals(user.getRole())) {
            return "Welcome to Admin Page, " + user.getUsername() + "!";
        } else {
            return "Unauthorized access!";
        }
    }
}