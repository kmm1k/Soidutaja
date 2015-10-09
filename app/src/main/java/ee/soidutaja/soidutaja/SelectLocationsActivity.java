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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SelectLocationsActivity extends AppCompatActivity {

    private Spinner startSpinner;
    private Spinner endSpinner;
    private Button nextBtn;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_locations);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        String fileContents = intent.getStringExtra("fileContents");
        Log.d("lammas", fileContents);
        String[] startLocations = new String[]{"Tallinn", "Tartu", "Türi", "Pärnu", "Võru"};

        try {
            startLocations = convertToStringArray(new JSONObject(fileContents));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        startSpinner = (Spinner) findViewById(R.id.startSpinner);
        ArrayAdapter<String> adapterStart = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, startLocations);
        startSpinner.setAdapter(adapterStart);

        endSpinner = (Spinner) findViewById(R.id.endSpinner);
        String[] endLocations = startLocations;
        ArrayAdapter<String> adapterEnd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, endLocations);
        endSpinner.setAdapter(adapterEnd);

        nextBtn = (Button) findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                //TODO add uri !!!!!!!!!!!!!!!!!!
                p.setUri("http://193.40.243.200/soidutaja/restful.php");
                p.setParam("origin", startSpinner.getSelectedItem().toString());
                p.setParam("destination", endSpinner.getSelectedItem().toString());

                Task task = new Task();
                task.execute(p);

                Intent intent = new Intent(SelectLocationsActivity.this, ListOfAllTripsActivity.class);
                startActivity(intent);
            }
        });
    }

    public String[] convertToStringArray(JSONObject jsonString) throws JSONException {
        String[] stringArray = new String[jsonString.length()];
        for(int i = 0; i < jsonString.length(); i++){
            stringArray[i] = jsonString.getString(Integer.toString(i));
        }
        return stringArray;
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

    private class Task extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
            if(result == null) {
                Log.d("lammas", "somthing went wrong");
            }
            else {
                Log.d("lammas", result);
            }

        }
    }


}