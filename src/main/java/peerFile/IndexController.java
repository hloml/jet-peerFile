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
				if(session.getAttribute("code") == null){
					model.addAttribute("errorMessage", "Access denied. Please log in.");
					return "index.jsp";
	   			}
				client.logout(session.getAttribute("code").toString());
			} catch (RemoteException e) {
				model.addAttribute("errorMessage", "Remote service can not be reached.");
				e.printStackTrace();
				return "index.jsp";
			}
	        return "index.jsp";
	}
	
    @RequestMapping("/login")
    String index(@RequestParam(value="username", required=false) String username,@RequestParam(value="password", required=false) String password,HttpSession session, Model model) {
    	String sessionCode = "";
		
    	try {
			sessionCode = client.getLogin(username, password);	
			if(sessionCode.equals(null)|| sessionCode.equals("")){
				model.addAttribute("errorMessage", "Username or password is wrong, please try it again.");
    			return "index.jsp";
    		}
    	
    		session.setAttribute("code", sessionCode);
    		String folder;
    	
			folder = client.getHomeFolder(session.getAttribute("code").toString());
			Entity[] files = client.browse(session.getAttribute("code").toString(), folder);
			ArrayList<PathItem> path = client.getFullPath(session.getAttribute("code").toString(), folder);
			model.addAttribute("files", files);
        	model.addAttribute("path", path);
		} catch (RemoteException e) {
			model.addAttribute("errorMessage", "Remote service can not be reached.");
			e.printStackTrace();
			return "index.jsp";
		}
        return "WEB-INF/mainPage.jsp";
    }
    
    
    @RequestMapping("/browse")
    String browse(@RequestParam(value="fileCode", required=false) String fileCode, HttpSession session, Model model) {
       
       Entity[] files;
       try {
    	   if(session.getAttribute("code") == null){
				model.addAttribute("errorMessage", "Access denied. Please log in.");
				return "index.jsp";
   			}
			files = client.browse(session.getAttribute("code").toString() , fileCode);
			ArrayList<PathItem>  path = client.getFullPath(session.getAttribute("code").toString() , fileCode);
	       	model.addAttribute("files", files);
	       	model.addAttribute("path", path);
			} catch (RemoteException e) {
				model.addAttribute("errorMessage", "Remote service can not be reached.");
				e.printStackTrace();
				return "WEB-INF/mainPage.jsp";
			}
       		return "WEB-INF/mainPage.jsp";
    }
    
    
    @RequestMapping("/download")
    public void download(@RequestParam(value="fileCode", required=false) String fileCode, @RequestParam(value="name", required=false) String fileName, HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
    	RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
         
    	Get_contentResponse file;
		try {
			if(session.getAttribute("code") == null){
				request.setAttribute("errorMessage", "Access denied. Please log in.");
				rd.forward(request, response);
				return;
   			}
			if(fileCode == null){
				request.setAttribute("errorMessage", "Missing file code.");
				rd.forward(request, response);
				return;
			}
			if(fileName == null){
				request.setAttribute("errorMessage", "Missing file name.");
				rd.forward(request, response);
				return;
			}
		
			file = client.getContent(session.getAttribute("code").toString(), fileCode);
		 	
	    	if (file.getContent_url().isEmpty()) {    		

	    		byte[] downloadedFile = Base64.decodeBase64(file.getContent().getBytes());
 	        	      
	    	    response.setContentType("application/force-download");
	    	    response.setHeader("Content-Disposition", "attachment; filename=" + fileName); 	 
	    	}
	    	else {
				response.sendRedirect(URL_PEERFILE + file.getContent_url());
	    	}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
    }
    
      
    
    public static void main(String[] args) throws Exception {
    	
        SpringApplication.run(IndexController.class, args);
    }
}