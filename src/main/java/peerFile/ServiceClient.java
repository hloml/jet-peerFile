package peerFile;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.axis2.AxisFault;

import peerFile.wsdl.ServiceStub;
import peerFile.wsdl.ServiceStub.Browse;
import peerFile.wsdl.ServiceStub.BrowseResponse;
import peerFile.wsdl.ServiceStub.Entities;
import peerFile.wsdl.ServiceStub.Entity;
import peerFile.wsdl.ServiceStub.Get_full_path_from_rootResponse;
import peerFile.wsdl.ServiceStub.Get_home_folder;
import peerFile.wsdl.ServiceStub.Get_home_folderResponse;
import peerFile.wsdl.ServiceStub.Login;
import peerFile.wsdl.ServiceStub.LoginResponse;
import peerFile.wsdl.ServiceStub.Logout;
import peerFile.wsdl.ServiceStub.LogoutResponse;
import peerFile.wsdl.ServiceStub.Get_full_path_from_root;
import peerFile.wsdl.ServiceStub.Get_content;
import peerFile.wsdl.ServiceStub.Get_contentResponse;;
public class ServiceClient {

	ServiceStub service = null;
	
	
	public ServiceClient(){
		try {
			getService();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getService() throws AxisFault {
		service = new ServiceStub();

	}
	
	public String logout(String code) throws RemoteException{
		String success = "";
		Logout logout = new Logout();
		logout.setSession_code(code);
		LogoutResponse response = service.logout(logout);
		System.out.println(response.getSuccess());
		
		return success;
	}
	
	public String getLogin(String userName, String password) throws RemoteException {
		String code = "";
		
		Login login = new Login();
		login.setUser_name(userName);
		login.setPassword(password);
		
		LoginResponse response = service.login(login);
		code = response.getSession_code();
		System.out.println(response.getSession_code());
		System.out.println(response.getSuccess());
		
		return code;
	}
	
	
	public Entity[] browse(String code, String fileCode) throws RemoteException {
		Entity[] entity = null;
		peerFile.wsdl.ServiceStub.Browse browse = new Browse();
		browse.setSession_code(code);
		browse.setParent_code(fileCode);
		BrowseResponse res = new BrowseResponse();
		res = service.browse(browse);
	
		Entities ent = res.getEntities();
		entity = ent.getEntity();
		
		return entity;
	}
	
	
	public String getHomeFolder(String code) throws RemoteException {
	
		Get_home_folder h = new Get_home_folder();  
		h.setSession_code(code);
		Get_home_folderResponse response = null;
		response = service.get_home_folder(h);
	
		return response.getFolder_code();
	}
	
	public ArrayList<PathItem> getFullPath(String code, String fileCode) throws RemoteException {
		String path = null;
		ArrayList<PathItem> pathItems= new ArrayList<PathItem>();
		Get_full_path_from_root fullPath = new Get_full_path_from_root();
		fullPath.setCode(fileCode);
		fullPath.setSession_code(code);
		Get_full_path_from_rootResponse response = service.get_full_path_from_root(fullPath);
		path = response.getPath();
		pathItems = parsePath(path);
		
		return pathItems;
	}
	
	private ArrayList<PathItem> parsePath(String path) {
		 ArrayList<PathItem> pathItems= new ArrayList<PathItem>();
		 String[] items = path.split("~\\\\");
		 String[] item;
		 
		 for (int i=0; i< items.length; i++) {
			 item = items[i].split("~");
			 pathItems.add(new PathItem(item[0], item[1]));
		 }
		 
		 return pathItems;
	}
	
	public Get_contentResponse  getContent(String sessionCode, String fileCode) throws RemoteException {
		Get_content content = new Get_content();
		content.setSession_code(sessionCode);
		content.setCode(fileCode);
		Get_contentResponse response = null;
		response = service.get_content(content);

		return response;
	}
	
	
}
