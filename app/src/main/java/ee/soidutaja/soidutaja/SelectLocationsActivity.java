package ee.soidutaja.soidutaja;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
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

import java.util.ArrayList;
import java.util.List;

public class SelectLocationsActivity extends AppCompatActivity {

    private Spinner startSpinner;
    private Spinner endSpinner;
    private Button nextBtn;
    private ProgressBar progressBar;
    private Button allBtn;
    private List<Drive> drives;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_locations);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        List<String> locations = getIntent().getStringArrayListExtra("loc");

        allBtn = (Button) findViewById(R.id.allTrips);
        startSpinner = (Spinner) findViewById(R.id.startSpinner);
        endSpinner = (Spinner) findViewById(R.id.endSpinner);
        ArrayAdapter<String> adapterStart = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        startSpinner.setAdapter(adapterStart);
        endSpinner.setAdapter(adapterStart);

        nextBtn = (Button) findViewById(R.id.nextButton);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    RequestPackage rp = new RequestPackage();
                    rp.setMethod("POST");
                    rp.setUri("http://193.40.243.200/soidutaja_php/");
                    rp.setParam("origin", startSpinner.getSelectedItem().toString());
                    rp.setParam("destination", endSpinner.getSelectedItem().toString());

                    Task task = new Task();
                    task.execute(rp);
                }
                else {
                    Toast.makeText(SelectLocationsActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    RequestPackage rp = new RequestPackage();
                    rp.setMethod("POST");
                    rp.setUri("http://193.40.243.200/soidutaja_php/");
                    rp.setParam("allDrives", "yes");

                    Task task = new Task();
                    task.execute(rp);
                }
            }
        });
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_locations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
                drives = JSONParser.parseDriveObjects(result);
                Intent intent = new Intent(SelectLocationsActivity.this, ListOfAllTripsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) drives);
                startActivity(intent);
            }
        }
    }
}