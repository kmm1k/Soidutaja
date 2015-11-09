package ee.soidutaja.soidutaja;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmm on 31.10.2015.
 */
public class ProfileViewActivity extends AppCompatActivity {

    private List<String> locationsList;
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        List<Drive> driverList = getIntent().getParcelableArrayListExtra("driverList");
        locationsList = getIntent().getStringArrayListExtra("locationsList");
        List<Drive> passengerList = getIntent().getParcelableArrayListExtra("passengerList");
        Log.d("lammas", driverList.toString());
        userName = (TextView) findViewById(R.id.usernameTextView);

        if(getIntent().getParcelableExtra("user") != null) {
            User user = getIntent().getParcelableExtra("user");
            userName.setText(user.getName());
//            String id = user.getFacebookID();
//            URL image_value = new URL("https://graph.facebook.com/" + id + "/picture");
//            profPict = BitmapFactory.decodeStream(image_value.openConnection().getInputStream());
        }

        DriveAdapter adapter = new DriveAdapter(this, driverList);

        final ListView drivesListOutput=(ListView) findViewById(R.id.driverList);
        drivesListOutput.setAdapter(adapter);

        final ListView passengerListOutput=(ListView) findViewById(R.id.passengerList);
        passengerListOutput.setAdapter(adapter);

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
}
