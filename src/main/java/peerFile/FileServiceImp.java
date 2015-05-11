package peerFile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
public class FileServiceImp implements FileService {

	@Autowired
	private ServiceClient client;

	private final String URL_PEERFILE = "http://peerfile.eu:4000";
	private final String code = "code";
	private final String error = "errorMessage";

	public String index(HttpSession session, Model model) {
		try {
			String folder = client.getHomeFolder(session.getAttribute(code).toString());
			Entity[] files = client.browse(session.getAttribute(code).toString(), folder);
			ArrayList<PathItem> path = client.getFullPath(session.getAttribute(code).toString(),
					folder);
			model.addAttribute("files", files);
			model.addAttribute("path", path);
		} catch (RemoteException e) {
			ArrayList<String> errors = FileServiceValidations.validateIndexService(e);
			model.addAttribute(error, errors);
		}
		return "WEB-INF/mainPage.jsp";
	}

	public String browse(HttpSession session, Model model, HttpServletResponse response,
			String fileCode, ArrayList<String> errors) {
		Entity[] files;
		ArrayList<PathItem> path = null;

		try {
			path = client.getFullPath(session.getAttribute(code).toString(), fileCode);
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
			errors = FileServiceValidations.validateBrowseService(e, response, path);
			session.setAttribute(error, errors);
			return "";
		}
		return "WEB-INF/mainPage.jsp";
	}

	public void download(HttpSession session, Model model, HttpServletRequest request,
			HttpServletResponse response, String fileCode, String fileName, String parentCode,
			ArrayList<String> errors) {
		Get_contentResponse file;
		try {
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
			errors = FileServiceValidations.validateDownloadService(e1, response, parentCode);
			session.setAttribute(error, errors);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
