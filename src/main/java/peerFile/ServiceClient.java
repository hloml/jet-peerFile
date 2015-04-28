package peerFile;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.axis2.AxisFault;

import peerFile.wsdl.ServiceStub.Entity;
import peerFile.wsdl.ServiceStub.Get_contentResponse;

public interface ServiceClient {
	
	public void getService() throws AxisFault;
	
	public String logout(String code) throws RemoteException;
	
	public String getLogin(String userName, String password) throws RemoteException;
	
	public Entity[] browse(String code, String fileCode) throws RemoteException;
	
	public String getHomeFolder(String code) throws RemoteException;
	
	public ArrayList<PathItem> getFullPath(String code, String fileCode) throws RemoteException;
	
	public Get_contentResponse getContent(String sessionCode, String fileCode) throws RemoteException; 

}
