package peerFile;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import peerFile.wsdl.ServiceStub.Entity;

@Controller
@EnableAutoConfiguration
public class IndexController {

	private ServiceClient client = new ServiceClient();
	String sessionCode = "3KSDY5";
	
	@RequestMapping("/")
	public String home(Model model) {
	        return "index.jsp";
	}
	
    @RequestMapping("/login")
    String index(@RequestParam(value="username", required=false) String username,@RequestParam(value="password", required=false) String password,HttpSession session, Model model) {
    	sessionCode = client.getLogin(username, password);		//pro ziskani session code    	 	
    	if(sessionCode.equals(null)|| sessionCode.equals("")){
    		return "index.jsp";
    	}
    	
    	session.setAttribute("code", sessionCode);
    	String folder = client.getHomeFolder(session.getAttribute("code").toString());
    	Entity[] files = client.browse(session.getAttribute("code").toString(), folder);
        ArrayList<PathItem> path = client.getFullPath(session.getAttribute("code").toString(), folder);
        
        model.addAttribute("files", files);
        model.addAttribute("path", path);
        return "WEB-INF/mainPage.jsp";
    }
    
    
    @RequestMapping("/browse")
    String browse(@RequestParam(value="fileCode", required=false) String fileCode, HttpSession session, Model model) {
       
       Entity[] files = client.browse(session.getAttribute("code").toString() , fileCode);
       ArrayList<PathItem>  path = client.getFullPath(session.getAttribute("code").toString() , fileCode);
       model.addAttribute("files", files);
       model.addAttribute("path", path);
       return "WEB-INF/mainPage.jsp";
    }
    
    
    
    
    public static void main(String[] args) throws Exception {
    	
        SpringApplication.run(IndexController.class, args);
        
    }
}