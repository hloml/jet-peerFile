package peerFile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import peerFile.wsdl.ServiceStub.Entity;
import peerFile.wsdl.ServiceStub.Get_contentResponse;

@Controller
@EnableAutoConfiguration
public class IndexController {

	private ServiceClient client = new ServiceClient();
	private final String URL_PEERFILE = "http://peerfile.eu:4000";

	@RequestMapping("/")
	public String home(Model model) {
		return "index.jsp";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session, Model model) {
		try {
			if (session.getAttribute("code") == null) {
				model.addAttribute("errorMessage", "Access denied. Please log in.");
				return "index.jsp";
			}
			client.logout(session.getAttribute("code").toString());
			session.removeAttribute("code");
		} catch (RemoteException e) {
			model.addAttribute("errorMessage", "Remote service can not be reached.");
			e.printStackTrace();
			return "index.jsp";
		}
		return "index.jsp";
	}

	@RequestMapping("/login")
	String login(@RequestParam(value = "username", required=false) String username,
			@RequestParam(value = "password", required=false) String password, HttpSession session, Model model) {
		String sessionCode = "";

		try {
			sessionCode = client.getLogin(username, password);
			if (sessionCode.equals(null) || sessionCode.equals("")) {
				model.addAttribute("errorMessage",
						"Username or password is wrong, please try it again.");
				return "index.jsp";
			}

			session.setAttribute("code", sessionCode);

			String folder = client.getHomeFolder(session.getAttribute("code").toString());
			Entity[] files = client.browse(session.getAttribute("code").toString(), folder);
			ArrayList<PathItem> path = client.getFullPath(session.getAttribute("code").toString(),
					folder);
			model.addAttribute("files", files);
			model.addAttribute("path", path);
		} catch (RemoteException e) {
			model.addAttribute("errorMessage", "Remote service can not be reached.");
			e.printStackTrace();
			return "index.jsp";
		}
		return "WEB-INF/mainPage.jsp";
	}

	
	
	@RequestMapping("/index")
	String index(HttpSession session, Model model) {
		try {
			String folder = client.getHomeFolder(session.getAttribute("code").toString());
			Entity[] files = client.browse(session.getAttribute("code").toString(), folder);
			ArrayList<PathItem> path = client.getFullPath(session.getAttribute("code").toString(),
					folder);
			model.addAttribute("files", files);
			model.addAttribute("path", path);
		} catch (RemoteException e) {
			model.addAttribute("errorMessage", "Remote service can not be reached.");
			e.printStackTrace();
		}
		return "WEB-INF/mainPage.jsp";
	}
	
	
	@RequestMapping("/browse")
	String browse(@RequestParam(value = "fileCode", required=false) String fileCode, HttpSession session,
			Model model, HttpServletResponse response) {

		Entity[] files;
		ArrayList<PathItem> path = null;
		
		try {
			if (session.getAttribute("code") == null) {
				model.addAttribute("errorMessage", "Access denied. Please log in.");
				return "index.jsp";
			}
			
			path = client.getFullPath(session.getAttribute("code").toString(),
					fileCode);
			files = client.browse(session.getAttribute("code").toString(), fileCode);
			model.addAttribute("files", files);
			model.addAttribute("path", path);
			model.addAttribute("parentCode", fileCode);
		
			if (session.getAttribute("errorMessage") != null) {
				model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
				session.removeAttribute("errorMessage");
			}
		} catch (RemoteException e) {
			e.printStackTrace();

			if (e.getLocalizedMessage().toLowerCase().contains("insufficient rights")) {
				session.setAttribute("errorMessage",
						"You don't have permission to access this folder.");		
				if (path != null && path.size() - 2 >= 0) {
					redirect(response, "/browse?fileCode=" + path.get(path.size() - 2).getFileCode());
					return "";
				}	
				else { 
					redirect(response, "/index");
					return "";
				}
			} else {
				session.setAttribute("errorMessage", "Remote service can not be reached.");
				redirect(response, "/index");
				return "";
			}
		}	
		return "WEB-INF/mainPage.jsp";
	}

	@RequestMapping("/download")
	public void download(@RequestParam(value = "fileCode", required=false) String fileCode,
			@RequestParam(value = "name", required=false) String fileName,
			@RequestParam(value = "parentCode", required=false) String parentCode, HttpSession session,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		Get_contentResponse file;
		try {
			if (session.getAttribute("code") == null) {
				request.setAttribute("errorMessage", "Access denied. Please log in.");
				rd.forward(request, response);
				return;
			}
			if (fileCode == null) {
				request.setAttribute("errorMessage", "Missing file code.");
				rd.forward(request, response);
				return;
			}
			if (fileName == null) {
				request.setAttribute("errorMessage", "Missing file name.");
				rd.forward(request, response);
				return;
			}

			file = client.getContent(session.getAttribute("code").toString(), fileCode);

			if (file.getContent_url().isEmpty()) {

				byte[] downloadedFile = Base64.decodeBase64(file.getContent().getBytes());
				response.getOutputStream().write(downloadedFile);
				response.setContentType("application/force-download");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			} else {
				response.sendRedirect(URL_PEERFILE + file.getContent_url());
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
			if (e1.getLocalizedMessage().toLowerCase().contains("entity has no content")) {
				session.setAttribute("errorMessage", "File does not have content.");
			} else {
				session.setAttribute("errorMessage", "Remote service can not be reached.");
			}
			redirect(response, "/browse?fileCode=" + parentCode);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	private void redirect(HttpServletResponse response, String address) {
		try {
			response.sendRedirect(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		SpringApplication.run(IndexController.class, args);
	}
}