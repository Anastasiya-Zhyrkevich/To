package by.bsu.nosti.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import by.bsu.nosti.entity.User;
import by.bsu.nosti.exception.DAOException;

@Controller
public class UserController extends BaseController {

	@RequestMapping(value = "/manageUsers", method = RequestMethod.GET)
	public String manageUsers(HttpServletRequest req, Model model) {
		try {
			model.addAttribute("currentuser", getCurrentUser(req).getLogin());
			model.addAttribute("users", userDAO.retrieveAll());
			return "users";
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView addUser(HttpServletRequest req, Model model) {
		try {
			model.addAttribute("currentuser", getCurrentUser(req).getLogin());
			return new ModelAndView("addUser", "user", new User());
		} catch (DAOException exception) {
			return new ModelAndView("error", "message", exception.getMessage());
		}
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUserToDb(@ModelAttribute("user") User user, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		try {
			user.setRole(0);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userDAO.create(user);
			return manageRequests(req, resp, model);
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public ModelAndView editResource(@RequestParam("id") Integer id, HttpServletRequest req, Model model) {
		try {
			User user = userDAO.retrieve(id);
			model.addAttribute("currentuser", getCurrentUser(req).getLogin());
			return new ModelAndView("addUser", "user", user);
		} catch (DAOException exception) {
			return new ModelAndView("error", "message", exception.getMessage());
		}
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public String editUserToDb(@ModelAttribute("user") User user, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		try {
			user.setRole(0);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userDAO.update(user);
			return manageRequests(req, resp, model);
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteResource(@RequestParam("id") Integer id, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		try {
			userDAO.delete(id);
			return manageRequests(req, resp, model);
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

}
