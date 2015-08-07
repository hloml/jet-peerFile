package tools;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Metody pro práci s textovými daty.
 * @author Jara
 *
 */
public class Formatter {
	private static final String monitoringApiFolder = "/api/mon/";
	private static final String soapFolder = "/soap/action";
	
	private static final String[] fileSizeUnits = {"bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
	private static final String decimalPattern = "0.##";
	private static final String datePattern = "dd-MM-yyyy HH:mm:ss.SSS";
	
	private Formatter(){}
	
	/**
	 * Převede velikost v Byte na jednotky.
	 * @param bytes velikost v Byte
	 * @return velikost s jednotkami
	 */
	public static String convertBytes(long bytes){
		double bytesD = bytes;
	    int index = 0;
	    for(index = 0; index < fileSizeUnits.length; index++){
	        if(bytesD < 1024){
	            break;
	        }
	        bytesD = bytesD / 1024;
	    }
	    DecimalFormat decimalFormat = new DecimalFormat(decimalPattern);
		return decimalFormat.format(bytesD) + " " + fileSizeUnits[index];
	}

	/**
	 * Převede velikost v KiloByte na jednotky.
	 * @param kbytes velikost v KiloByte
	 * @return velikost s jednotkami
	 */
	public static String convertKiloBytes(long kbytes){
		return convertBytes(kbytes * 1024);
	}
	
	/**
	 * Převede čistý text na html
	 * @param s text
	 * @return text použitelný pro html
	 */
	public static String plainTextToHtml(String s) {
	    StringBuilder builder = new StringBuilder();
	    boolean previousWasASpace = false;
	    for( char c : s.toCharArray() ) {
	        if( c == ' ' ) {
	            if( previousWasASpace ) {
	                builder.append("&nbsp;");
	                previousWasASpace = false;
	                continue;
	            }
	            previousWasASpace = true;
	        } else {
	            previousWasASpace = false;
	        }
	        switch(c) {
	            case '<': builder.append("&lt;"); break;
	            case '>': builder.append("&gt;"); break;
	            case '&': builder.append("&amp;"); break;
	            case '"': builder.append("&quot;"); break;
	            case '\n': builder.append("<br>\n"); break;
	            // We need Tab support here, because we print StackTraces as HTML
	            case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;  
	            default:
	                if( c < 128 ) {
	                    builder.append(c);
	                } else {
	                    builder.append("&#").append((int)c).append(";");
	                }    
	        }
	    }
	    return builder.toString();
	}
	
	/**
	 * Převede datum ze String na Date
	 * @param s datum
	 * @return datum
	 */
	public static Date stringToDate(String s) {
		DateFormat formatter = new SimpleDateFormat(datePattern);
		try {
			return formatter.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Vrátí adresu monitorovacího API
	 * @param url adresa serveru
	 * @param port port serveru
	 * @return adresa monitorovacího API
	 */
	public static String getMonitoringApiUrl(String url, int port) {
		return url + ":" + port + monitoringApiFolder;
	}
	
	/**
	 * Vrátí adresu na soap
	 * @param url adresa serveru
	 * @param port port serveru
	 * @return adresa soap
	 */
	public static String getSoapUrl(String url, int port) {
		return url + ":" + port + soapFolder;
	}
	
	/**
	 * Vrátí zatížení serveru převedené na procenta
	 * @param load zatížení v rozsahu <0, inf>
	 * @return zatížení serveru <0, 1>
	 */
	public static double serverLoadPercent(double load) {
		// -1/(x+1) + 1 <0, inf>
		// OR
		// linear <0, 0.5> and -1/(x+1) + 1 <0.5, inf>
		if(load < 0) {
			return 0;
		}
		return (-1 / (load + 1)) + 1;
	}
	
	/**
	 * Get workload state from percent
	 * @param percent <0, 1>
	 * @return workload state (normal < 0.80, warning < 0.90, danger < 0.95, critical >= 0.95)
	 */
	public static String workloadState(double percent) {
		if(percent < 0.8) {
			return "normal";
		}
		if(percent < 0.9) {
			return "warning";
		}
		if(percent < 0.95) {
			return "danger";
		}
		return "critical";
	}
	
	/**
	 * Get workload state from value and maximum
	 * @param value
	 * @param maximum
	 * @return workload state (normal < 0.80, warning < 0.90, danger < 0.95, critical >= 0.95)
	 */
	public static String workloadState(double value, double maximum) {
		return workloadState(value/maximum);
	}
}
