import org.apache.commons.net.util.SubnetUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location {
    private static Map<String, String> locations;
    public Location() {

    }

    public static String getLocation(String ip) throws IOException {
        Location.getAllLocations();
        Map<String, String> locationsByPartOfIp = filterLocationsByPartOfIp(ip);

        for (Map.Entry<String, String> entry : locationsByPartOfIp.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            SubnetUtils utils = new SubnetUtils(key);
            String[] allIps = utils.getInfo().getAllAddresses();
            for (int i = 0; i < allIps.length; i++) {
                if(allIps[i].equals(ip)){
                    return value;
                }
            }
        }

        return null;
    }
    
    
    private static Map<String, String> filterLocationsByPartOfIp(String ip){
        Map<String, String> locationsByPartOfIp = new HashMap<>();

        for (Map.Entry<String, String> entry : locations.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(getPartOfIp(key).equals(getPartOfIp(ip))){
                locationsByPartOfIp.put(key, value);
            }
        }
        
        return locationsByPartOfIp;
    }

    private static String getPartOfIp(String ip){
        String[] ipS = ip.split(".");
        String partOfIp = "";
        for (String s : ipS) {
            partOfIp += s + ".";
        }

        return partOfIp;
    }
    
    
    private static void getAllLocations() throws IOException {
        locations = new HashMap<>();

        List<String> strings = Files.readAllLines(Paths.get("..\\subnetOffice.txt"));

        for (int i = 0; i < strings.size(); i++) {
            String line = strings.get(i);
            String[] lineArr = line.split(":");
            locations.put(lineArr[0], lineArr[1]);
        }
    }


}
