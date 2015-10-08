package ee.soidutaja.soidutaja;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kmmii on 09.10.2015.
 */
public class DownloadData extends AsyncTask<String, Void, String> {
    public String getFileContents() {
        return fileContents;
    }

    private String fileContents;
    private String finalResult;

    @Override
    protected String doInBackground(String... params) {
        fileContents = downloadFile(params[0]);
        if(fileContents == null) {
            Log.d("DownloadData", "Error downloading");
        }
        return fileContents;
    }

    public String getFinalResult() {
        return finalResult;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("DownloadData", "Result was: " + result);
        finalResult = result;

    }

    private String downloadFile(String urlPath) {
        StringBuilder tempBuffer = new StringBuilder();
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();
            Log.d("DownloadData", "The responsecode was: " + response);
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");

            int charRead;
            char[] inputBuffer = new char[500];

            while(true) {
                charRead = isr.read(inputBuffer);
                if(charRead <= 0) {
                    break;
                }
                tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));
            }

            return tempBuffer.toString();

        } catch (IOException e) {
            Log.d("DownloadData", "IOException reading data: " + e.getMessage());
        } catch (SecurityException e) {
            Log.d("DownloadData", "Security exception. Needs permission? " + e.getMessage());
        }

        return null;
    }

    public String[] convertToStringArray(JSONObject jsonString) throws JSONException {
        String[] stringArray = new String[jsonString.length()];
        for(int i = 0; i < jsonString.length(); i++){
            stringArray[i] = jsonString.getString(Integer.toString(i));
        }
        return stringArray;
    }
}