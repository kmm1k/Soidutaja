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
    private TextView date;
    private TextView time;

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

        date = (TextView) findViewById(R.id.date);
        date.setText(driveInfo.getDate());

        time = (TextView) findViewById(R.id.time);
        time.setText(driveInfo.getTime());

        info = (TextView) findViewById(R.id.info);
        info.setText(driveInfo.getInfo());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_created_drive_view, menu);
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
