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

import by.bsu.nosti.entity.UserResourceLink;
import by.bsu.nosti.exception.DAOException;

@Controller
public class HomeController extends BaseController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHome(HttpServletRequest req, HttpServletResponse resp, Model model) {
		return manageRequests(req, resp, model);
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String getAdminPage(HttpServletRequest req, HttpServletResponse resp, Model model) {
		return manageRequests(req, resp, model);
	}

	@RequestMapping(value = "/addLink", method = RequestMethod.GET)
	public ModelAndView addLink(HttpServletRequest req, Model model) {
		try {
			model.addAttribute("users", userDAO.retrieveAll());
			model.addAttribute("resources", resourceDAO.retrieveAll());
			model.addAttribute("currentuser", getCurrentUser(req).getLogin());
			return new ModelAndView("addLink", "link", new UserResourceLink());
		} catch (DAOException exception) {
			return new ModelAndView("error", "message", exception.getMessage());
		}
	}

	@RequestMapping(value = "/addLink", method = RequestMethod.POST)
	public String addUserToDb(@ModelAttribute("link") UserResourceLink link, HttpServletRequest req,
			HttpServletResponse resp, Model model) {
		try {
			userResourceLinkDAO.create(link);
			return manageRequests(req, resp, model);
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/editLink", method = RequestMethod.GET)
	public ModelAndView editLink(@RequestParam("id") Integer id, HttpServletRequest req, Model model) {
		try {
			UserResourceLink link = userResourceLinkDAO.retrieve(id);
			model.addAttribute("users", userDAO.retrieveAll());
			model.addAttribute("resources", resourceDAO.retrieveAll());
			model.addAttribute("currentuser", getCurrentUser(req).getLogin());
			return new ModelAndView("addLink", "link", link);
		} catch (DAOException exception) {
			return new ModelAndView("error", "message", exception.getMessage());
		}
	}

	@RequestMapping(value = "/editLink", method = RequestMethod.POST)
	public String editUserToDb(@ModelAttribute("link") UserResourceLink link, HttpServletRequest req,
			HttpServletResponse resp, Model model) {
		try {
			userResourceLinkDAO.update(link);
			return manageRequests(req, resp, model);
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/deleteLink", method = RequestMethod.GET)
	public String editUserToDb(@RequestParam("id") Integer id, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		try {
			userResourceLinkDAO.delete(id);
			return manageRequests(req, resp, model);
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

}
