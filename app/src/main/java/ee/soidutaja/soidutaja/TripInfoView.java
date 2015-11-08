package ee.soidutaja.soidutaja;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class TripInfoView extends AppCompatActivity {

    private Button takeFreeSpaceButton;
    private RatingBar ratingBar;
    private float rating;
    private Drive obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_info_view);

        takeFreeSpaceButton = (Button) findViewById(R.id.takeSpaceInCar);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        obj = getIntent().getParcelableExtra("obj");
        TextView name = (TextView) findViewById(R.id.driverName);
        name.setText(obj.getUser());
        TextView origin = (TextView) findViewById(R.id.origin);
        origin.setText(obj.getOrigin());
        TextView destination = (TextView) findViewById(R.id.destination);
        destination.setText(obj.getDestination());
        TextView time = (TextView) findViewById(R.id.department);
        time.setText(obj.getDateTime());
        TextView slots = (TextView) findViewById(R.id.slots);
        slots.setText("" + obj.getAvailableSlots());
        TextView price = (TextView) findViewById(R.id.price);
        price.setText(obj.getPrice());
        TextView info = (TextView) findViewById(R.id.additionalInfo);
        if(obj.getInfo() != null) {
            info.setText(obj.getInfo());
        }

        takeFreeSpaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TripInfoView.this)
                        .setTitle("Kohakinnitus")
                        .setMessage("Kas kinnitan su koha?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                requestData();
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

    public void requestData() {
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://193.40.243.200/soidutaja_php/");
        p.setParam("driveIdTakeSlot", obj.getId());
        TakeSlot takeSlot = new TakeSlot();
        takeSlot.execute(p);

    }

    public boolean done() {
        Toast.makeText(this, "Sind on lisatud valitud sõidule!", Toast.LENGTH_LONG).show();
        obj.setAvailableSlots(obj.getAvailableSlots() - 1);
        TextView slots = (TextView) findViewById(R.id.slots);
        slots.setText("" + obj.getAvailableSlots());
        return true;
    }

    private class TakeSlot extends AsyncTask<RequestPackage, String, String> {
//tegin selle classi publicuks :D
        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
        }*/

        @Override
        protected void onPostExecute(String s) {
            done();
        }
    }
}
