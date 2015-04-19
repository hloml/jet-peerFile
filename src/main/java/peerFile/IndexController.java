package peerFile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
				client.logout(session.getAttribute("code").toString());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return "index.jsp";
	}
	
    @RequestMapping("/login")
    String index(@RequestParam(value="username", required=false) String username,@RequestParam(value="password", required=false) String password,HttpSession session, Model model) {
    	String sessionCode = "";
		
    	try {
			sessionCode = client.getLogin(username, password);	
			if(sessionCode.equals(null)|| sessionCode.equals("")){
    			return "WEB-INF/error_login.jsp";
    		}
    	
    		session.setAttribute("code", sessionCode);
    		String folder;
    	
			folder = client.getHomeFolder(session.getAttribute("code").toString());
			Entity[] files = client.browse(session.getAttribute("code").toString(), folder);
			ArrayList<PathItem> path = client.getFullPath(session.getAttribute("code").toString(), folder);
			model.addAttribute("files", files);
        	model.addAttribute("path", path);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "WEB-INF/mainPage.jsp";
    }
    
    
    @RequestMapping("/browse")
    String browse(@RequestParam(value="fileCode", required=false) String fileCode, HttpSession session, Model model) {
       
       Entity[] files;
       try {
		files = client.browse(session.getAttribute("code").toString() , fileCode);
		ArrayList<PathItem>  path = client.getFullPath(session.getAttribute("code").toString() , fileCode);
       	model.addAttribute("files", files);
       	model.addAttribute("path", path);
		} catch (RemoteException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return "WEB-INF/mainPage.jsp";
    }
    
    
    @RequestMapping("/download")
    public void download(@RequestParam(value="fileCode", required=true) String fileCode, @RequestParam(value="name", required=true) String fileName, HttpSession session, Model model,  HttpServletResponse response) {
       
    	Get_contentResponse file;
		try {
			file = client.getContent(session.getAttribute("code").toString(), fileCode);
		 	
	    	if (file.getContent_url().isEmpty()) {    		
	    		 try {
	    	       byte[] downloadedFile = Base64.decodeBase64(file.getContent().getBytes());
	    		   response.getOutputStream().write(downloadedFile);     	        	      
	    	       response.setContentType("application/force-download");
	    	       response.setHeader("Content-Disposition", "attachment; filename=" + fileName); 	 
	    	      
	    	    } catch (Exception ex) {
	    	    	ex.printStackTrace();
	    	      throw new RuntimeException("IOError writing file to output stream");
	    	    }
	  
	    	}
	    	else {
	    		try {
					response.sendRedirect(URL_PEERFILE + file.getContent_url());
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
      
    
    public static void main(String[] args) throws Exception {
    	
        SpringApplication.run(IndexController.class, args);
    }
}