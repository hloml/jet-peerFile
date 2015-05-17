package peerFile;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 *
 */
@Controller
@EnableAutoConfiguration
@ComponentScan("peerFile")
public class IndexController {

	@Autowired
	private FileService fs;

	@Autowired
	private LoginService ls;

	private final String error = "errorMessage";

	/**
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/*")
	public String home(HttpSession session, Model model) {
		if (session.getAttribute("code") == null) {
			return "index.jsp";
		}
		else {
			return "/index";
		}
	}

	/**
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		String url = isLogged(session, model);
		if (!url.isEmpty()){
			return url;
		}

		return ls.logout(session, model);
	}

	/**
	 * @param username
	 * @param password
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	String login(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			HttpSession session, Model model) {

		return ls.login(session, model, username, password);
	}

	/**
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	String index(HttpSession session, Model model) {
		String url = isLogged(session, model);
		if (!url.isEmpty()){
			return url;
		}

		return fs.index(session, model);
	}

	/**
	 * @param fileCode
	 * @param session
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("/browse")
	String browse(@RequestParam(value = "fileCode", required = false) String fileCode,
			HttpSession session, Model model, HttpServletResponse response) {

		String url = isLogged(session, model);
		if (!url.isEmpty()){
			return url;
		}

		ArrayList<String> errors = FileServiceValidations.validateBrowse(fileCode);
		if (errors.size() != 0) {
			session.setAttribute(error, errors);
			redirect(response, "index");
			return "";
		}

		return fs.browse(session, model, response, fileCode, errors);
	}

	/**
	 * @param fileCode
	 * @param fileName
	 * @param parentCode
	 * @param session
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping("/download")
	public void download(@RequestParam(value = "fileCode", required = false) String fileCode,
			@RequestParam(value = "name", required = false) String fileName,
			@RequestParam(value = "parentCode", required = false) String parentCode,
			HttpSession session, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String url = isLogged(session, model);
		if (!url.isEmpty()) {
			redirect(response, url);
		}

		ArrayList<String> errors = FileServiceValidations.validateDownload(fileCode, fileName);
		if (errors.size() != 0) {
			session.setAttribute(error, errors);
			redirect(response, "index");
			return;
		}

		fs.download(session, model, request, response, fileCode, fileName, parentCode, errors);
	}

	/**
	 * @param session
	 * @param model
	 * @return
	 */
	private String isLogged(HttpSession session, Model model) {
		if (session.getAttribute("code") == null) {
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("Access denied. Please log in.");
			model.addAttribute("errorMessage", errors);
			return "index.jsp";
		}
		return "";
	}

	/**
	 * @param response
	 * @param address
	 */
	private void redirect(HttpServletResponse response, String address) {
		try {
			response.sendRedirect(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		SpringApplication.run(IndexController.class, args);
	}
}