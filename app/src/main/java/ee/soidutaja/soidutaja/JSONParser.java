package ee.soidutaja.soidutaja;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Virx on 12.10.2015.
 */
public class JSONParser {
    public static List<Drive> parseDriveObjects(String content) {
        try {
            JSONArray ar = new JSONArray(content);
            List<Drive> driveList = new ArrayList<>();

            for(int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Drive drive = new Drive();
                //eeldades, et JSONi value nimed on destination ja origin,
                // peaks need muutujad k2tte saama nii:
                drive.setDestination(obj.getString("destination"));
                drive.setOrigin(obj.getString("origin"));

                driveList.add(drive);
            }
            return driveList;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
