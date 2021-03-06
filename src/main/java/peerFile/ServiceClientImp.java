package peerFile;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import configuration.Server;
import configuration.Servers;
import peerFile.wsdl.ServiceStub;
import peerFile.wsdl.ServiceStub.Browse;
import peerFile.wsdl.ServiceStub.BrowseResponse;
import peerFile.wsdl.ServiceStub.Entities;
import peerFile.wsdl.ServiceStub.Entity;
import peerFile.wsdl.ServiceStub.Get_entity_attributes;
import peerFile.wsdl.ServiceStub.Get_entity_attributesResponse;
import peerFile.wsdl.ServiceStub.Get_full_path_from_rootResponse;
import peerFile.wsdl.ServiceStub.Get_home_folder;
import peerFile.wsdl.ServiceStub.Get_home_folderResponse;
import peerFile.wsdl.ServiceStub.Login;
import peerFile.wsdl.ServiceStub.LoginResponse;
import peerFile.wsdl.ServiceStub.Logout;
import peerFile.wsdl.ServiceStub.LogoutResponse;
import tools.Formatter;
import peerFile.wsdl.ServiceStub.Get_full_path_from_root;
import peerFile.wsdl.ServiceStub.Get_content;
import peerFile.wsdl.ServiceStub.Get_contentResponse;

/**
 * Implementace klientských metod, které slouží ke komunikaci se servrem.
 * 
 * @author 
 *
 */
/**
 * @author user
 *
 */
@Service
public class ServiceClientImp implements ServiceClient {

	private final static Logger logger = Logger.getLogger(ServiceClientImp.class);
	
	// Seznam instancí serverů, které jsou k dispozici
	private Servers serversMap;

	/**
	 * Konstruktor.
	 */
	public ServiceClientImp() {
		try {
			getService();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			logger.error("Axis Fault", e);
		}
	}

	/* (non-Javadoc)
	 * @see peerFile.ServiceClient#getService()
	 */
	public void getService() throws AxisFault {
	//	service = new ServiceStub();
			
		ApplicationContext context = new ClassPathXmlApplicationContext("ServerConfBean.xml");		 
    	serversMap = (Servers)context.getBean("ServerBean");
		
	}
	
	
	public Servers getServers() {
		return serversMap;
	}
	
	
	/** Vytvoření klienta pro služby pro server zvoleny uzivatelem (ulozen v session pod server)
	 * @return
	 * @throws AxisFault
	 */
	private ServiceStub getClient() throws AxisFault {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session =   attr.getRequest().getSession(true); 
		
		if (session.getAttribute("server") != null) {
			String server = session.getAttribute("server").toString();	
			Server s = (Server) getServers().getMaps().get(server);	
			ServiceStub service = new ServiceStub(Formatter.getSoapUrl(s.getAddress(), s.getPort()));
			return service;
		} 
		else {
			throw new AxisFault("Service stub initialization failed");
		}	
	}
	
	

	/* (non-Javadoc)
	 * @see peerFile.ServiceClient#logout(java.lang.String)
	 */
	public String logout(String code) throws RemoteException {
		Logout logout = new Logout();
		logout.setSession_code(code);
		ServiceStub service = getClient();
		LogoutResponse response = service.logout(logout);
		String success = response.getSuccess();

		return success;
	}

	/* (non-Javadoc)
	 * @see peerFile.ServiceClient#getLogin(java.lang.String, java.lang.String)
	 */
	public String getLogin(String userName, String password)
			throws RemoteException {
		String code = "";

		Login login = new Login();
		login.setUser_name(userName);
		login.setPassword(password);

		ServiceStub service = getClient();
		LoginResponse response = service.login(login);
		code = response.getSession_code();
		return code;
	}

	/* (non-Javadoc)
	 * @see peerFile.ServiceClient#browse(java.lang.String, java.lang.String)
	 */
	public Entity[] browse(String code, String fileCode) throws RemoteException {
		Entity[] entity = null;
		peerFile.wsdl.ServiceStub.Browse browse = new Browse();
		browse.setSession_code(code);
		browse.setParent_code(fileCode);
		BrowseResponse res = new BrowseResponse();
		
		ServiceStub service = getClient();
		res = service.browse(browse);

		Entities ent = res.getEntities();
		entity = ent.getEntity();

		return entity;
	}

	/* (non-Javadoc)
	 * @see peerFile.ServiceClient#getHomeFolder(java.lang.String)
	 */
	public String getHomeFolder(String code) throws RemoteException {

		Get_home_folder h = new Get_home_folder();
		h.setSession_code(code);
		Get_home_folderResponse response = null;
		
		ServiceStub service = getClient();
		response = service.get_home_folder(h);

		return response.getFolder_code();
	}

	public ArrayList<PathItem> getFullPath(String code, String fileCode)
			throws RemoteException {
		String path = null;
		ArrayList<PathItem> pathItems = new ArrayList<PathItem>();
		Get_full_path_from_root fullPath = new Get_full_path_from_root();
		fullPath.setCode(fileCode);
		fullPath.setSession_code(code);
		
		ServiceStub service = getClient();
		Get_full_path_from_rootResponse response = service
				.get_full_path_from_root(fullPath);
		path = response.getPath();
		pathItems = parsePath(path);

		return pathItems;
	}

	/**
	 * Create path into arrayList for file from root
	 * @param path Cesta.
	 * @return
	 */
	private ArrayList<PathItem> parsePath(String path) {
		ArrayList<PathItem> pathItems = new ArrayList<PathItem>();
		String[] items = path.split("~\\\\");
		String[] item;
		for (int i = 0; i < items.length; i++) {
			item = items[i].split("~");
			if (item.length >= 2)
				pathItems.add(new PathItem(item[0], item[1]));
		}
		return pathItems;
	}

	/* (non-Javadoc)
	 * @see peerFile.ServiceClient#getContent(java.lang.String, java.lang.String)
	 */
	public Get_contentResponse getContent(String code, String fileCode)
			throws RemoteException {
		Get_content content = new Get_content();
		content.setSession_code(code);
		content.setCode(fileCode);

		Get_contentResponse response = null;
		
		ServiceStub service = getClient();
		response = service.get_content(content);
		return response;
	}
	
	
	public Get_entity_attributesResponse getEntityAttributes(String code, String fileCode) throws RemoteException {
		Get_entity_attributes request = new Get_entity_attributes();
		request.setSession_code(code);
		request.setCode(fileCode);
		
		Get_entity_attributesResponse response = null;
		ServiceStub service = getClient();
		response = service.get_entity_attributes(request);
		return response;
	}
	
	
	
}
