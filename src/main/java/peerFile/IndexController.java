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
	String sessionCode = "3E7HJG";
	
    @RequestMapping("/index")
    String index(Model model) {
    //	client.login("", "");		pro ziskani session code
    	
    	
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