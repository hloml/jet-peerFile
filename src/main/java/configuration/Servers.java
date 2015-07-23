package configuration;

import java.util.Map;

public class Servers {

	private Map<Object, Object> maps;

	public Map<Object, Object> getMaps() {
		return maps;
	}

	public void setMaps(Map<Object, Object> maps) {
		this.maps = maps;
	}


	@Override
	public String toString() {
		return "maps=" + maps;
	}

}