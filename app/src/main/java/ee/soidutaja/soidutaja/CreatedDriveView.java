package ee.soidutaja.soidutaja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CreatedDriveView extends AppCompatActivity {

    private TextView origin;
    private TextView destination;
    private TextView user;
    private TextView price;
    private TextView slots;
    private TextView info;
    private TextView dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_drive_view);

        Drive driveInfo = getIntent().getParcelableExtra("driveObj");
        Log.d("lammas", "andis objekti edasi");

        origin = (TextView) findViewById(R.id.origin);
        origin.setText(driveInfo.getOrigin());

        destination = (TextView) findViewById(R.id.destination);
        destination.setText(driveInfo.getDestination());

        user = (TextView) findViewById(R.id.name);
        user.setText(driveInfo.getUser());

        price = (TextView) findViewById(R.id.price);
        price.setText(driveInfo.getPrice());

        slots = (TextView) findViewById(R.id.slots);
        slots.setText("" + driveInfo.getAvailableSlots());

        dateTime = (TextView) findViewById(R.id.dateTime);
        dateTime.setText(driveInfo.getDateTime());

        info = (TextView) findViewById(R.id.info);
        info.setText(driveInfo.getInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("menuprint", String.valueOf(menu));
        getMenuInflater().inflate(R.menu.menu_created_drive_view, menu);
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


}
