package by.bsu.nosti.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.bsu.nosti.entity.User;
import by.bsu.nosti.entity.UserModel;
import by.bsu.nosti.entity.UserRole;
import by.bsu.nosti.exception.DAOException;
import by.bsu.nosti.util.GoogleAuthenticator;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

@Controller
public class QRCodeController extends BaseController {

	@RequestMapping(value = "/verification", method = RequestMethod.GET)
	public void generateQRCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		postGenerateQRCode(req, resp);
	}

	@RequestMapping(value = "/verification", method = RequestMethod.POST)
	public void postGenerateQRCode(HttpServletRequest request, 
			HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");

		// Get the secret key
		String secretKey = GoogleAuthenticator.generateSecretKey();
		
		// set the session key to session scope
		// then we can recall it. (just for the demonstration purpose)
		request.getSession().setAttribute("secretKey", secretKey);

		// Here note that we must follow this url pattern
		// otherwise Google Authenticator app won't decode the info.
		// more in info @
		// https://code.google.com/p/google-authenticator/wiki/KeyUriFormat
		String s = "otpauth://totp/" + username + "?secret=" + secretKey;

		// Get the Qr Code png as a OutPutStream
		ByteArrayOutputStream outs = QRCode.from(s).to(ImageType.PNG).stream();

		response.setContentType("image/png");
		response.setContentLength(outs.size());

		OutputStream outStream = response.getOutputStream();

		outStream.write(outs.toByteArray());

		outStream.flush();
		outStream.close();
	}

	@RequestMapping(value = "/verifyLogin", method = RequestMethod.POST)
	public String verify(@RequestParam("code") String codestr, 
			@RequestParam("username") String username,
			@RequestParam("publicKey") String publicKey,
			HttpServletRequest req, HttpServletResponse resp, 
			Model model) throws IOException {
		System.out.println("Public Key + " + publicKey);
		try {
			User userDb = userDAO.getUser(username);
			long code = Long.parseLong(codestr);

			long t = System.currentTimeMillis();

			GoogleAuthenticator authenticator = new GoogleAuthenticator();

			authenticator.setWindowSize(5); // should give 5 * 30 seconds of
											// grace

			//String savedSecret = (String) req.getSession().getAttribute("secretKey");
			String savedSecret = (String) userDb.getK();			
			
			boolean success = authenticator.check_code(savedSecret, code, t);

			PrintWriter pw = resp.getWriter();

			if (success) {
				addCookie(username, resp);
				model.addAttribute("currentuser", userDb.getLogin());
				userDAO.addPublicKey(userDb, publicKey);
				
				if (userDb.getRole() == UserRole.Admin.ordinal()) {
					model.addAttribute("userResourceLinks", userResourceLinkDAO.retrieveAll());
					return "admin";
				}
				
				model.addAttribute("publicKey", publicKey);
				model.addAttribute("userResourceLinks",
						userResourceLinkDAO.getUserResourceLinkByUserId(userDb.getUserId()));
				return "index";
			} else {
				model.addAttribute("username", username);
				model.addAttribute("success", false);
				return "login";
			}

		} catch (DAOException exception) {
			return "error";
		}
	}
	
	@RequestMapping(value = "/verifyRegistration", method = RequestMethod.POST)
	public String verify(@RequestParam("code") String codestr, 
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("publicKey") String publicKey,
			HttpServletRequest req, HttpServletResponse resp, 
			Model model) throws IOException {
		try {
			long code = Long.parseLong(codestr);

			long t = System.currentTimeMillis();

			GoogleAuthenticator authenticator = new GoogleAuthenticator();

			authenticator.setWindowSize(5); // should give 5 * 30 seconds of
											// grace

			String savedSecret = (String) req.getSession().getAttribute("secretKey");
			
			boolean success = authenticator.check_code(savedSecret, code, t);

			PrintWriter pw = resp.getWriter();

			if (success) {
				try {
					User user = new User();
					user.setEmail(email);
					user.setLogin(username);
					user.setPassword(passwordEncoder.encode(password));
					user.setK((String)req.getSession().getAttribute("secretKey"));
					user.setRole(UserRole.User.ordinal());
					userDAO.create(user);
				} catch (DAOException exception) {
					model.addAttribute("message", exception.getMessage());
					return "error";
				}
				User userDb = userDAO.getUser(username);
				addCookie(username, resp);
				model.addAttribute("currentuser", userDb.getLogin());
				userDAO.addPublicKey(userDb, publicKey);
				
				if (userDb.getRole() == UserRole.Admin.ordinal()) {
					model.addAttribute("userResourceLinks", userResourceLinkDAO.retrieveAll());
					return "admin";
				}
				model.addAttribute("userResourceLinks",
						userResourceLinkDAO.getUserResourceLinkByUserId(userDb.getUserId()));
				return "index";
			} else {
				model.addAttribute("username", username);
				model.addAttribute("success", false);
				return "login";
			}

		} catch (DAOException exception) {
			return "error";
		}
	}
	
	
	
	
}
