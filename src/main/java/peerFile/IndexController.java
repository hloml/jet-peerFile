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
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @return Adresa přesměrování.
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
	 * Odhlášení uživatele.
	 * 
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @return Adresa přesměrování.
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
	 * Přihlášení uživatele.
	 * 
	 * @param username Uživatelské jméno.
	 * @param password Uživatelské heslo.
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @return Adresa přesměrování.
	 */
	@RequestMapping("/login")
	String login(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			HttpSession session, Model model) {

		return ls.login(session, model, username, password);
	}

	/**
	 * Vypsání domovského adresáře.
	 * 
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @return Adresa přesměrování.
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
	 * Procházení adresářové struktury.
	 * 
	 * @param fileCode Kód souboru/složky.
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @param response Http odpověď.
	 * @return Adresa přesměrování.
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
	 * Stažení souboru.
	 * 
	 * @param fileCode Kód souboru/složky.
	 * @param fileName Jméno souboru/složky.
	 * @param parentCode Kód nadřazené složky.
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @param request Http žádost.
	 * @param response Http odpověď.
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
	 * Ověření, že je uživatel již přihlášený.
	 * 
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @return Adresa přesměrování.
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
	 * Přesměrování.
	 * 
	 * @param response Http odpověď.
	 * @param address Adresa přesměrování.
	 */
	private void redirect(HttpServletResponse response, String address) {
		try {
			response.sendRedirect(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Hlavní metoda, která pustí aplikaci.
	 * 
	 * @param args Pole argumentů.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		SpringApplication.run(IndexController.class, args);
	}
}