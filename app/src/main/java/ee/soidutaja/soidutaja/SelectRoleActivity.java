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
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectRoleActivity extends AppCompatActivity {

    private Button driverBtn;
    private Button passengerBtn;
    private List<String> locations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        driverBtn = (Button) findViewById(R.id.driverButton);
        passengerBtn = (Button) findViewById(R.id.passengerButton);

        if(isOnline()) {
            Log.d("lammas", "nett on olemas");
            requestData();
        }
        else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void requestData() {
        RequestPackage p = new RequestPackage();
        p.setMethod("GET");
        p.setUri("http://193.40.243.200/soidutaja_php/");
        p.setParam("locations", "yes");
        DownloadData downloadData = new DownloadData();
        downloadData.execute(p);

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

    public void addButton() {
        passengerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    Intent intent = new Intent(SelectRoleActivity.this, SelectLocationsActivity.class);
                    intent.putExtra("loc", (ArrayList<String>) locations);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SelectRoleActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        driverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectRoleActivity.this, MakeDriveActivity.class);
                intent.putExtra("loc", (ArrayList<String>) locations);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first, menu);
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

    private class DownloadData extends AsyncTask<RequestPackage, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result == null) {
                Log.d("lammas", "somthing went wrong");
            }
            else {
                Log.d("lammas", result);
                locations = JSONParser.parseLocations(result);
                addButton();
            }
        }

    }


}
