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

import by.bsu.nosti.entity.Resource;
import by.bsu.nosti.exception.DAOException;

@Controller
public class ResourceController extends BaseController {

	@RequestMapping(value = "/manageResources", method = RequestMethod.GET)
	public ModelAndView viewResources(HttpServletRequest req, Model model) {
		try {
			model.addAttribute("currentuser", getCurrentUser(req).getLogin());
			return new ModelAndView("resources", "resources", resourceDAO.retrieveAll());
		} catch (DAOException exception) {
			return new ModelAndView("error", "message", exception.getMessage());
		}
	}

	@RequestMapping(value = "/viewResource", method = RequestMethod.GET)
	public ModelAndView viewResource(@RequestParam("id") Integer id, HttpServletRequest req, Model model) {
		try {
			Resource resource = resourceDAO.retrieve(id);
			model.addAttribute("currentuser", getCurrentUser(req).getLogin());
			return new ModelAndView("resource", "resource", resource);
		} catch (DAOException exception) {
			return new ModelAndView("error", "message", exception.getMessage());
		}
	}

	@RequestMapping(value = "/addResource", method = RequestMethod.GET)
	public ModelAndView addResource(HttpServletRequest req, Model model) {
		try {
			model.addAttribute("currentuser", getCurrentUser(req).getLogin());
			return new ModelAndView("addResource", "resource", new Resource());
		} catch (DAOException exception) {
			return new ModelAndView("error", "message", exception.getMessage());
		}
	}

	@RequestMapping(value = "/addResource", method = RequestMethod.POST)
	public String addResourceToDb(@ModelAttribute("resource") Resource resource, HttpServletRequest req,
			HttpServletResponse resp, Model model) {
		try {
			resourceDAO.create(resource);
			return manageRequests(req, resp, model);
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/editResource", method = RequestMethod.GET)
	public ModelAndView editResource(@RequestParam("id") Integer id, HttpServletRequest req, Model model) {
		try {
			Resource resource = resourceDAO.retrieve(id);
			model.addAttribute("currentuser", getCurrentUser(req).getLogin());
			return new ModelAndView("addResource", "resource", resource);
		} catch (DAOException exception) {
			return new ModelAndView("error", "message", exception.getMessage());
		}
	}

	@RequestMapping(value = "/editResource", method = RequestMethod.POST)
	public String editResourceToDb(@ModelAttribute("resource") Resource resource, HttpServletRequest req,
			HttpServletResponse resp, Model model) {
		try {
			resourceDAO.update(resource);
			return manageRequests(req, resp, model);
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}

	@RequestMapping(value = "/deleteResource", method = RequestMethod.GET)
	public String deleteResource(@RequestParam("id") Integer id, HttpServletRequest req, HttpServletResponse resp,
			Model model) {
		try {
			resourceDAO.delete(id);
			return manageRequests(req, resp, model);
		} catch (DAOException exception) {
			model.addAttribute("message", exception.getMessage());
			return "error";
		}
	}
}
