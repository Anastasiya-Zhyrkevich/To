
package by.bsu.nosti.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import by.bsu.nosti.dao.ResourceDAO;
import by.bsu.nosti.dao.UserDAO;
import by.bsu.nosti.dao.UserResourceLinkDAO;
import by.bsu.nosti.entity.User;
import by.bsu.nosti.entity.UserRole;
import by.bsu.nosti.exception.DAOException;

@Controller
public class BaseController {
	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected UserDAO userDAO;

	@Autowired
	protected ResourceDAO resourceDAO;

	@Autowired
	protected UserResourceLinkDAO userResourceLinkDAO;

	protected void addCookie(String cookieName, HttpServletResponse resp) {
		Cookie cookie = new Cookie("login", cookieName);
		cookie.setMaxAge(1000000);
		cookie.setPath("/");
		resp.addCookie(cookie);
	}

	protected void removeCookie(String cookieName, HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		if (cookies == null) {
			return;
		}
		for (Cookie cookie : cookies) {
			if ("login".equals(cookie.getName())) {
				cookie.setMaxAge(0);
				break;
			}
		}
	}

	public String manageRequests(HttpServletRequest req, HttpServletResponse resp, Model model) {
		try {
			User user = getCurrentUser(req);
			if (user == null) {
				return "index";
			}
			model.addAttribute("currentuser", user.getLogin());
			if (user != null && user.getRole() == UserRole.Admin.ordinal()) {
				model.addAttribute("userResourceLinks", userResourceLinkDAO.retrieveAll());
				return "admin";
			}
			model.addAttribute("userResourceLinks", userResourceLinkDAO.getUserResourceLinkByUserId(user.getUserId()));
			return "index";
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

	protected User getCurrentUser(HttpServletRequest req) throws DAOException {
		Cookie[] cookies = req.getCookies();
		String login = "";
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if ("login".equals(cookie.getName())) {
				login = cookie.getValue();
				break;
			}
		}
		User userDb = userDAO.getUser(login);
		return userDb;
	}
}
