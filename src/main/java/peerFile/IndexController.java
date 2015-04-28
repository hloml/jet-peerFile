package peerFile;

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

@Controller
@EnableAutoConfiguration
@ComponentScan("peerFile") 
public class IndexController {
	
	@Autowired
	private FileService fs;
	
	@Autowired
	private LoginService ls;

	@RequestMapping("/*")
	public String home(HttpSession session, Model model) {
		if (session.getAttribute("code") == null) {
			return "index.jsp";
		}
		else{
			return "/index";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		return ls.logout(session, model);
	}

	@RequestMapping("/login")
	String login(@RequestParam(value = "username", required=false) String username,
			@RequestParam(value = "password", required=false) String password, HttpSession session, Model model) {
		return ls.login(session, model, username, password);
	}

	
	
	@RequestMapping("/index")
	String index(HttpSession session, Model model) {
		return fs.index(session, model);
	}
	
	
	@RequestMapping("/browse")
	String browse(@RequestParam(value = "fileCode", required=false) String fileCode, HttpSession session,
			Model model, HttpServletResponse response) {

		return fs.browse(session, model, response, fileCode);
	}

	@RequestMapping("/download")
	public void download(@RequestParam(value = "fileCode", required=false) String fileCode,
			@RequestParam(value = "name", required=false) String fileName,
			@RequestParam(value = "parentCode", required=false) String parentCode, HttpSession session,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		
		fs.download(session, model, request, response, fileCode, fileName, parentCode);		
	}

	public static void main(String[] args) throws Exception {

		SpringApplication.run(IndexController.class, args);
	}
}