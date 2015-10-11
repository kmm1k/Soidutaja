package ee.soidutaja.soidutaja;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Virx on 9.10.2015.
 */
public class HttpManager {
    public static String getData(RequestPackage p) {
        BufferedReader reader = null;
        String uri = p.getUri();

        if(p.getMethod().equals("GET")) {
            uri += "?" + p.getEncodedParams();
        }

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(p.getMethod());
            Log.d("lammas", "urli sai k2tte");

            if(p.getMethod().equals("POST")) {
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                Log.d("lammas", "hakkas POSTima");
                writer.write(p.getEncodedParams());
                writer.flush();
            }

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Log.d("lammas", "stringbuilderi tegi ka 2ra");
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();
        } catch (Exception e) {
            Log.d("lammas", "http manager on putsis");
            e.printStackTrace();
            return null;
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
