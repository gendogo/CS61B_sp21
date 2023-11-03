package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: similar to the index file in git
 * @Author: whj
 * @Date: 2023-11-03 23:15
 */
public class Stage implements Serializable {
    private Map<String, String> pathToBlobID = new HashMap<>();
}
