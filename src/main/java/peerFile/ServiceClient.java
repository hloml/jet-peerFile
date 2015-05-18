package peerFile;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.axis2.AxisFault;

import peerFile.wsdl.ServiceStub.Entity;
import peerFile.wsdl.ServiceStub.Get_contentResponse;

/**
 * Rozhraní wsdl služeb. Metody sloužící ke komunikaci se servrem, který tyto služby poskytuje.
 * 
 * @author
 *
 */
public interface ServiceClient {
	
	/**
	 * Získání služeb ze serveru.
	 * 
	 * @throws AxisFault
	 */
	public void getService() throws AxisFault;
	
	/**
	 * Odhlášení uživatele.
	 * 
	 * @param code Identifikační kód uživatele.
	 * @return True pokud odhlášení proběhlo úspěšně jinak vrací False.
	 * @throws RemoteException
	 */
	public String logout(String code) throws RemoteException;
	
	/**
	 * Přihlášení uživatele.
	 * 
	 * @param userName Uživatelské jméno.
	 * @param password Uživatelské heslo.
	 * @return Identifikační kód uživatele.
	 * @throws RemoteException
	 */
	public String getLogin(String userName, String password) throws RemoteException;
	
	/**
	 * Procházení adresářové struktury.
	 * 
	 * @param code Identifikační kód uživatele.
	 * @param fileCode Kód souboru/složky.
	 * @return
	 * @throws RemoteException
	 */
	public Entity[] browse(String code, String fileCode) throws RemoteException;
	
	/**
	 * Získání domácího adresáře uživatele.
	 * 
	 * @param code Identifikační kód uživatele.
	 * @return Kód souboru/složky.
	 * @throws RemoteException
	 */
	public String getHomeFolder(String code) throws RemoteException;
	
	/**
	 * 
	 * 
	 * @param code Identifikační kód uživatele.
	 * @param fileCode Kód souboru/složky.
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<PathItem> getFullPath(String code, String fileCode) throws RemoteException;
	
	/**
	 * 
	 * 
	 * @param sessionCode Identifikační kód uživatele.
	 * @param fileCode Kód souboru/složky.
	 * @return Http odpověď.
	 * @throws RemoteException
	 */
	public Get_contentResponse getContent(String sessionCode, String fileCode) throws RemoteException; 

}
