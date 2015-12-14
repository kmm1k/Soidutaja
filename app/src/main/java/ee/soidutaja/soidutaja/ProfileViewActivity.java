package ee.soidutaja.soidutaja;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmm on 31.10.2015.
 */
public class ProfileViewActivity extends AppCompatActivity {

    private List<String> locationsList;
    private TextView userName;
    private ProfilePictureView picfield;
    private User user;
    private ListView drivesListOutput;
    private ListView passengerListOutput;
    private Context context;

    private List<Drive> creatorDrives;
    private List<Drive> passengerDrives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        context = getBaseContext();

        picfield = (ProfilePictureView) findViewById(R.id.pingu);
        locationsList = getIntent().getStringArrayListExtra("loc");
        Log.d("lammas", "locations: " + locationsList.toString());

        getCreatorList();
        getPassengerList();

        drivesListOutput=(ListView) findViewById(R.id.driverList);
        passengerListOutput=(ListView) findViewById(R.id.passengerList);

        userName = (TextView) findViewById(R.id.usernameTextView);

        Context context = getBaseContext();
        if(getIntent().getStringExtra("user")==null) {
            userName.setText(SharedPreferencesManager.readData(context)[0]);
            Log.d("lammas", SharedPreferencesManager.readData(context)[1]);
            picfield.setProfileId(SharedPreferencesManager.readData(context)[1]);
        }else{
            userName.setText(getIntent().getStringExtra("name"));
            picfield.setProfileId(getIntent().getStringExtra("fId"));
        }

        drivesListOutput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = drivesListOutput.getAdapter().getItem(position);
                String value = obj.toString();
                Log.d("lammas", "list item value: " + value);

                Intent intent = new Intent(ProfileViewActivity.this, TripInfoView.class);
                intent.putExtra("obj", (Parcelable) obj);
                intent.putExtra("loc", (ArrayList<String>) locationsList);
                intent.putExtra("Kristi", "kristi");
                startActivity(intent);
            }
        });
        passengerListOutput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = passengerListOutput.getAdapter().getItem(position);
                String value = obj.toString();
                Log.d("lammas", "list item value: " + value);

                Intent intent = new Intent(ProfileViewActivity.this, TripInfoView.class);
                intent.putExtra("obj", (Parcelable) obj);
                intent.putExtra("loc", (ArrayList<String>) locationsList);
                intent.putExtra("Tõnis", "tõnis");
                startActivity(intent);
            }
        });
    }

    public void getCreatorList() {
        RequestPackage rp = new RequestPackage();
        rp.setUri("http://193.40.243.200/soidutaja_php/");
        rp.setParam("inDriveCreator", "yes");
        rp.setMethod("POST");
        if(user != null) {
            rp.setParam("fbId", user.getFacebookID());
        } else {
            rp.setParam("fbId", SharedPreferencesManager.readData(getApplicationContext())[1]);
        }
        Task1 task = new Task1();
        task.execute(rp);
    }

    public void getPassengerList() {
        RequestPackage rp = new RequestPackage();
        rp.setUri("http://193.40.243.200/soidutaja_php/");
        rp.setParam("inDrivePassanger", "yes");
        rp.setMethod("POST");
        if(user != null) {
            rp.setParam("fbId", user.getFacebookID());
        } else {
            rp.setParam("fbId", SharedPreferencesManager.readData(getApplicationContext())[1]);
        }
        Task2 task = new Task2();
        task.execute(rp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_of_all_trips, menu);
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

    class Task1 extends AsyncTask<RequestPackage, String, List<Drive>> {

        @Override
        protected List<Drive> doInBackground(RequestPackage... params) {
            String result = HttpManager.getData(params[0]);
            Log.d("lammas1", result);
            return JSONParser.parseDriveObjects(result);
        }

        @Override
        protected void onPostExecute(List<Drive> drives) {
            creatorDrives = drives;
            DriveAdapter adapter = new DriveAdapter(context, creatorDrives);

            drivesListOutput.setAdapter(adapter);
        }
    }

    class Task2 extends AsyncTask<RequestPackage, String, List<Drive>> {

        @Override
        protected List<Drive> doInBackground(RequestPackage... params) {
            String result = HttpManager.getData(params[0]);
            Log.d("lammas2", result);
            return JSONParser.parseDriveObjects(result);
        }

        @Override
        protected void onPostExecute(List<Drive> drives) {
            passengerDrives = drives;
            DriveAdapter adapter = new DriveAdapter(context, passengerDrives);

            passengerListOutput.setAdapter(adapter);
        }
    }
}
