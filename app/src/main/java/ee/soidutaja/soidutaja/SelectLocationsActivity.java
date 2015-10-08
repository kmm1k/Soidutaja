package ee.soidutaja.soidutaja;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SelectLocationsActivity extends AppCompatActivity {

    private String LOG_TAG = SelectLocationsActivity.class.getSimpleName();
    private Spinner startSpinner;
    private Spinner endSpinner;
    private Button nextBtn;
    private String fileContents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_locations);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://193.40.243.200/soidutaja/");

        startSpinner = (Spinner) findViewById(R.id.startSpinner);
        String[] startLocations = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapterStart = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, startLocations);
        startSpinner.setAdapter(adapterStart);

        endSpinner = (Spinner) findViewById(R.id.endSpinner);
        String[] endLocations = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapterEnd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, endLocations);
        endSpinner.setAdapter(adapterEnd);

        nextBtn = (Button) findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectLocationsActivity.this, "j2rgmine vaade", Toast.LENGTH_LONG).show();
                Log.d(LOG_TAG, "you should see next activity");
                Intent intent = new Intent(SelectLocationsActivity.this,ListOfAllTripsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_locations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            fileContents = downloadFile(params[0]);
            if(fileContents == null) {
                Log.d("DownloadData", "Error downloading");
            }
            return fileContents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadData", "Result was: " + result);
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
    }
}
