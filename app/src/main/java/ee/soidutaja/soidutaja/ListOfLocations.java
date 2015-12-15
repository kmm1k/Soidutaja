package ee.soidutaja.soidutaja;

import java.util.ArrayList;

/**
 * Created by Virx on 15.12.2015.
 */
public class ListOfLocations {
    public static ArrayList<String> locations;

    public static void setLocations(ArrayList<String> list) {
        locations = list;
    }

    public static ArrayList<String> getLocations() {
        return locations;
    }
}
