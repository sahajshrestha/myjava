package com.myjava.myjava;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;

@RestController
@RequestMapping("/api")
public class CookieController {

    @GetMapping("/setCookie")
    public String setCookies(HttpServletResponse response) {
        // Create a new cookie
        Cookie cookie = new Cookie("username", "john_doe");

        //PRO-TIPS: Cookie Path
        //  The URL path that must exist in the requested URL in order for the cookie to be sent from the client (browser) to the server.
        //  It acts as a restriction or scope for the cookie, determining which URLs within a domain can access the cookie.
        // Optional: Set the cookie path. If not specified, it defaults to the current request path.
        // cookie.setPath("/");

        // PRO-TIPS: Cookie Domain
        //  Cookie domain is the scope within which the cookie is accessible. It specifies the domain name of the server that set
        //  the cookie and to which the cookie should be sent in future requests.
        // Optional: Set the cookie domain. If not specified, it defaults to the domain of the server that set the cookie.
        // cookie.setDomain("example.com");
        // Here, all the subdomains of example.com like, blog.example.com, will have access to the cookie

        // Optional: Set the cookie max age. If not specified, the cookie is valid for the current session.
        cookie.setMaxAge(30); // 3600 seconds = 1 hour but here the cookie will only last for 30 seconds.

        // Add the cookie to the response
        response.addCookie(cookie);

        return "Cookie 'username' set successfully!";
    }


    // Since this is within the domain scope and the path scope, this will automatically get the cookie
    @GetMapping("/getCookie")
    public String getCookie(@CookieValue(name = "username", defaultValue = "Guest") String username) {
        return "Username from cookie: " + username;
    }
}