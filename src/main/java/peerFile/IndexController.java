package peerFile;

import java.util.ArrayList;

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
    String index(@RequestParam(value="username", required=false) String username,@RequestParam(value="password", required=false) String password, Model model) {
    	sessionCode = client.getLogin(username, password);		//pro ziskani session code    	 	
    	if(sessionCode.equals(null)|| sessionCode.equals("")){
    		return "index.jsp";
    	}
    	
    	String folder = client.getHomeFolder(sessionCode);
    	Entity[] files = client.browse(sessionCode, folder);
        ArrayList<PathItem> path = client.getFullPath(sessionCode, folder);
        
        model.addAttribute("files", files);
        model.addAttribute("path", path);
        return "WEB-INF/mainPage.jsp";
    }
    
    
    @RequestMapping("/browse")
    String browse(@RequestParam(value="fileCode", required=false) String fileCode, Model model) {
       
       Entity[] files = client.browse(sessionCode , fileCode);
       ArrayList<PathItem>  path = client.getFullPath(sessionCode , fileCode);
       model.addAttribute("files", files);
       model.addAttribute("path", path);
       return "WEB-INF/mainPage.jsp";
    }
    
    
    
    
    public static void main(String[] args) throws Exception {
    	
        SpringApplication.run(IndexController.class, args);
        
    }
}