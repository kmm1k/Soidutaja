package ee.soidutaja.soidutaja;

import android.util.Log;

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
        List<Drive> driveList = new ArrayList<Drive>();

        try {
            JSONObject ar = new JSONObject(content);

            int i = 0;

            while(!ar.isNull(""+i)) {

                JSONObject obj = ar.getJSONObject("" + i);
                Drive drive = new Drive();
                Log.d("lammas", obj.toString());
                drive.setDestination(obj.getString("destination"));
                drive.setOrigin(obj.getString("origin"));
                drive.setUser(obj.getString("creator"));
                drive.setPrice(obj.getString("price"));
                drive.setDateTime(obj.getString("start_time"));
                drive.setId(obj.getString("id"));
                drive.setfId("1220781151");
                drive.setAvailableSlots(Integer.parseInt(obj.getString("open_slots")));

                driveList.add(drive);
                i++;
                Log.d("lammas", drive.toString());
            }
            if(driveList == null) {
                Log.d("lammas", "JSON drive object parser on perses");
            }
            else {
                Log.d("lammas", "listi suurus: " + driveList.size());
            }
            return driveList;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> parseLocations(String content) {
        List<String> locations = new ArrayList<String>();

        try {
            JSONObject ar = new JSONObject(content);

            int i = 0;

            while(!ar.isNull(""+i)) {

                JSONObject obj = ar.getJSONObject("" + i);
                Log.d("lammas", obj.toString());
                locations.add(obj.getString("location"));
                i++;
            }

            Log.d("lammas", locations.get(0));

            if(locations == null) {
                Log.d("lammas", "JSON location parser on perses");
            }
            else {
                Log.d("lammas", "listi suurus: " + locations.size());
            }
            return locations;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
