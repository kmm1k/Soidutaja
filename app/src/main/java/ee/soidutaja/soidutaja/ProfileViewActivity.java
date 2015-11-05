package ee.soidutaja.soidutaja;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.AccessToken;

import java.util.List;

/**
 * Created by kmm on 31.10.2015.
 */
public class ProfileViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        List<Drive> driverList = getIntent().getParcelableArrayListExtra("driverList");
        List<Drive> passengerList = getIntent().getParcelableArrayListExtra("passengerList");
        Log.d("lammas", driverList.toString());
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
