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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import peerFile.wsdl.ServiceStub.Entity;
import peerFile.wsdl.ServiceStub.Get_contentResponse;

@Service
public class FileServiceImp implements FileService{

	@Autowired
	private ServiceClient client;
	
	private final String URL_PEERFILE = "http://peerfile.eu:4000";
	private final String code = "code";
	private final String error = "errorMessage";
	
	
	public String index(HttpSession session, Model model){
		try {
			String folder = client.getHomeFolder(session.getAttribute(code).toString());
			Entity[] files = client.browse(session.getAttribute(code).toString(), folder);
			ArrayList<PathItem> path = client.getFullPath(session.getAttribute(code).toString(),
					folder);
			model.addAttribute("files", files);
			model.addAttribute("path", path);
		} catch (RemoteException e) {
			model.addAttribute(error, "Remote service can not be reached.");
			e.printStackTrace();
		}
		return "WEB-INF/mainPage.jsp";
	}
	
	public String browse(HttpSession session, Model model, HttpServletResponse response, String fileCode){
		Entity[] files;
		ArrayList<PathItem> path = null;
		
		try {
			if (session.getAttribute(code) == null) {
				model.addAttribute("errorMessage", "Access denied. Please log in.");
				return "index.jsp";
			}
			if (fileCode == null) {
				model.addAttribute(error, "Missing file code.");
				return "/index";
			}
			path = client.getFullPath(session.getAttribute(code).toString(),
					fileCode);
			files = client.browse(session.getAttribute(code).toString(), fileCode);
			model.addAttribute("files", files);
			model.addAttribute("path", path);
			model.addAttribute("parentCode", fileCode);
		
			if (session.getAttribute(error) != null) {
				model.addAttribute(error, session.getAttribute(error));
				session.removeAttribute(error);
			}
		} catch (RemoteException e) {
			e.printStackTrace();

			if (e.getLocalizedMessage().toLowerCase().contains("insufficient rights")) {
				session.setAttribute(error,
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
				session.setAttribute(error, "Remote service can not be reached.");
				redirect(response, "/index");
				return "";
			}
		}	
		return "WEB-INF/mainPage.jsp";
	}
	
	public void download(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response, String fileCode, String fileName, String parentCode){
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		Get_contentResponse file;
		try {
			if (session.getAttribute(code) == null) {
				request.setAttribute(error, "Access denied. Please log in.");
				rd.forward(request, response);
				return;
			}
			if (fileCode == null) {
				 rd = request.getRequestDispatcher("/index");
				request.setAttribute(error, "Missing file code.");
				rd.forward(request, response);
				return;
			}
			if (fileName == null) {
				 rd = request.getRequestDispatcher("/index");
				request.setAttribute(error, "Missing file name.");
				rd.forward(request, response);
				return;
			}

			file = client.getContent(session.getAttribute(code).toString(), fileCode);

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
				session.setAttribute(error, "File does not have content.");
			} else {
				session.setAttribute(error, "Remote service can not be reached.");
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
}
