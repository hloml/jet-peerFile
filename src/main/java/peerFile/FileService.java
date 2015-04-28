package peerFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public interface FileService {
	
	public String index(HttpSession session, Model model);
	
	public String browse(HttpSession session, Model model, HttpServletResponse response, String fileCode);
	
	public void download(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response, String fileCode, String fileName, String parentCode);
}
