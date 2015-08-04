package peerFile;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

/**
 * Rozhraní souborových služeb. Zobrazení domovského adresáře, procházení adresářovou strukturou a stahování souboru.
 * 
 * @author
 *
 */
public interface FileService {
	
	/**
	 * Zobrazení domovského adresáře.
	 * 
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @return Adresa přesměrování.
	 */
	public String index(HttpSession session, Model model);
	
	/**
	 * Procházení adresářovou strukturou.
	 * 
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @param response Http odpověď.
	 * @param fileCode Kód souboru/složky.
	 * @param errors Seznam chyb.
	 * @return Adresa přesměrování.
	 */
	public String browse(HttpSession session, Model model, HttpServletResponse response, String fileCode, ArrayList<String> errors);
	
	/**
	 * Stažení malého souboru a přesměrování pokud se jedná o soubor větší.
	 * 
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @param request Http požadavek.
	 * @param response Http odpověď.
	 * @param fileCode Kód souboru/složky.
	 * @param fileName Jméno souboru/složky.
	 * @param parentCode Kód nadřazeného souboru/složky.
	 * @param errors Seznam chyb.
	 */
	public String download(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response, String fileCode, String fileName, String parentCode, ArrayList<String> errors);



	/**
	 * Detail entity
	 * 
	 * @param session Aktuální session.
	 * @param model Model aplikace.
	 * @param response Http odpověď.
	 * @param fileCode Kód souboru/složky.
	 * @param errors Seznam chyb.
	 * @return Adresa přesměrování.
	 */
	public String entityDetail(HttpSession session, Model model, HttpServletResponse response, String fileCode, ArrayList<String> errors);

}
