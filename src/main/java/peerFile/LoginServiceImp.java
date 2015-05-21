package peerFile;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Inplementace logovacích služeb.
 * 
 * @author
 *
 */
@Service
public class LoginServiceImp implements LoginService {

	@Autowired
	private ServiceClient client;

	private final static Logger logger = Logger.getLogger(LoginServiceImp.class);
	private final String code = "code";
	private final String error = "errorMessage";

	/* (non-Javadoc)
	 * @see peerFile.LoginService#logout(javax.servlet.http.HttpSession, org.springframework.ui.Model)
	 */
	public String logout(HttpSession session, Model model) {
		try {
			client.logout(session.getAttribute(code).toString());
			session.removeAttribute(code);
			logger.info("User logged out - " + code);

		} catch (RemoteException e) {
			ArrayList<String> errors = LoginServiceValidations.validateLogoutService(e);
			model.addAttribute(error, errors);
		}
		return "index";
	}

	/* (non-Javadoc)
	 * @see peerFile.LoginService#login(javax.servlet.http.HttpSession, org.springframework.ui.Model, java.lang.String, java.lang.String)
	 */
	public String login(HttpSession session, Model model, String username, String password) {
		String sessionCode = "";
		try {
			if (session.getAttribute(code) == null) {
				sessionCode = client.getLogin(username, password);
				if (sessionCode.equals(null) || sessionCode.equals("")) {
					ArrayList<String> errors = new ArrayList<String>();
					errors.add("Username or password is wrong, please try it again.");
					model.addAttribute(error, errors);
					logger.error("Wrong username or password: " + username);
					return "index";
				}
				logger.info("User " + username + " logged in - " + sessionCode);
				session.setAttribute("code", sessionCode);
			}

		} catch (RemoteException e) {
			ArrayList<String> errors = LoginServiceValidations.validateLoginService(e);
			model.addAttribute(error, errors);
			return "index";
		}

		return "redirect:/index";
	}
}
