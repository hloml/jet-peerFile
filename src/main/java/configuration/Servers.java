package configuration;

import java.util.Map;

public class Servers {

	private Map<Object, Server> maps;

	public Map<Object, Server> getMaps() {
		return maps;
	}

	public void setMaps(Map<Object, Server> maps) {
		this.maps = maps;
	}


	@Override
	public String toString() {
		return "maps=" + maps;
	}

}