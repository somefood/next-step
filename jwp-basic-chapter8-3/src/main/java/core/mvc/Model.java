package core.mvc;

import java.util.HashMap;
import java.util.Map;

public class Model {

    private Map<String, Object> maps = new HashMap<>();

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void addAttribute(String key, Object value) {
        maps.put(key, value);
    }
}
