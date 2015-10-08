package ee.soidutaja.soidutaja;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class TripInfoView extends AppCompatActivity {

    private Button takeFreeSpaceButton;
    private RatingBar ratingBar;
    private float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_info_view);

        takeFreeSpaceButton = (Button) findViewById(R.id.takeSpaceInCar);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        takeFreeSpaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TripInfoView.this)
                        .setTitle("Kohakinnitus")
                        .setMessage("Kas kinnitan su koha?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue to work with database
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothiogelng
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating = ratingBar.getRating();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_info_view, menu);
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
