package configuration;

import java.util.Map;

public class Servers {

	private Map<Object, Server> maps;

	public Map<Object, Server> getMaps() {
		return maps;
	}

	public void setMaps(Map<Object, Server> maps) {
		this.maps = maps;
		// initialize monitoring
		for (Map.Entry<Object, Server> m : maps.entrySet()) {
            m.getValue().monitoring();
        }
	}


	@Override
	public String toString() {
		return "maps=" + maps;
	}

}