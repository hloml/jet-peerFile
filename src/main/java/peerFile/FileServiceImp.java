package peerFile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis2.client.Stub;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import peerFile.wsdl.ServiceStub;
import peerFile.wsdl.ServiceStub.Attribute;
import peerFile.wsdl.ServiceStub.Entity;
import peerFile.wsdl.ServiceStub.Get_contentResponse;
import peerFile.wsdl.ServiceStub.Get_entity_attributesResponse;

/**
 * Implementace jednotlivých souborových služeb.
 * 
 * @author
 *
 */
@Service
public class FileServiceImp implements FileService {
	
	@Autowired
	private ServiceClient client;

	private final static Logger logger = Logger.getLogger(FileServiceImp.class);
	private final String URL_PEERFILE = "http://peerfile.eu:4000";
	private final String code = "code";
	private final String error = "errorMessage";

	/* (non-Javadoc)
	 * @see peerFile.FileService#index(javax.servlet.http.HttpSession, org.springframework.ui.Model)
	 */
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
		return "mainPage";
	}

	/* (non-Javadoc)
	 * @see peerFile.FileService#browse(javax.servlet.http.HttpSession, org.springframework.ui.Model, javax.servlet.http.HttpServletResponse, java.lang.String, java.util.ArrayList)
	 */
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
			
			ValidationsContainer valContainer = FileServiceValidations.validateBrowseService(e, response, path);
			session.setAttribute(error, valContainer.getErrors());
			return valContainer.getUrl();
		}
		return "mainPage";
	}

	/* (non-Javadoc)
	 * @see peerFile.FileService#download(javax.servlet.http.HttpSession, org.springframework.ui.Model, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String, java.lang.String, java.lang.String, java.util.ArrayList)
	 */
	public String download(HttpSession session, Model model, HttpServletRequest request,
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
			
			logger.info("File " + fileName + " was downloaded");
		} catch (RemoteException e1) {
			
			ValidationsContainer valContainer = FileServiceValidations.validateDownloadService(e1, response, parentCode);
			session.setAttribute(error, valContainer.getErrors());		
			return valContainer.getUrl();
		
		} catch (IOException e) {
			logger.error(e);
		}
		return "";
	}
	
	

	public String entityDetail(HttpSession session, Model model, HttpServletResponse response,
			String fileCode, ArrayList<String> errors) {

		ArrayList<PathItem> path = null;

		try {
			path = client.getFullPath(session.getAttribute(code).toString(), fileCode);
					
			Get_entity_attributesResponse entity_attributes = client.getEntityAttributes(session.getAttribute(code).toString(), fileCode);
			
			model.addAttribute("entity_code", entity_attributes.getEntity_code());		
			model.addAttribute("path", path);
			model.addAttribute("parentCode", fileCode);
			categorizeArrayAttributes(model, entity_attributes.getAttributes().getAttribute());
			
			if (session.getAttribute(error) != null) {
				model.addAttribute(error, session.getAttribute(error));
				session.removeAttribute(error);
			}
		} catch (RemoteException e) {
			System.out.println(e);
			ValidationsContainer valContainer = FileServiceValidations.validateEntityDetailService(e, response, path);
			session.setAttribute(error, valContainer.getErrors());
			return valContainer.getUrl();
		}
		return "entityDetail";
	}
	
	
	
	/** Metoda seradi atributy entity podle jejich pozice z form_possition, dale je rozdeli do dvou poli dle atributu type
	 * @param model
	 * @param array
	 */
	private void categorizeArrayAttributes(Model model, Attribute[] array) {
			
		Arrays.sort(array, new Comparator<Attribute>() {
		    @Override
		    public int compare(Attribute o1, Attribute o2) {
		        return ((Integer) o1.getForm_position()).compareTo(o2.getForm_position());
		    }
		});
		
		ArrayList<Attribute> basicAttr = new ArrayList<ServiceStub.Attribute>();
		ArrayList<Attribute> advancedAttr = new ArrayList<ServiceStub.Attribute>();
		
		for (Attribute attribute : array) {
			if (attribute.getType().equals("basic")) {
				basicAttr.add(attribute);
			}
			else {
				advancedAttr.add(attribute);
			}
		}
		
		model.addAttribute("advance_attr", advancedAttr);
		model.addAttribute("basic_attr", basicAttr);
	}
	
	
	
}
