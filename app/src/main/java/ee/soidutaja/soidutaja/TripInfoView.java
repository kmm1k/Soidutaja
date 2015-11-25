package ee.soidutaja.soidutaja;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TripInfoView extends AppCompatActivity {

    private Button takeFreeSpaceButton;
    private RatingBar ratingBar;
    private float rating;
    private Drive obj;
    private Button editTripButton;
    private Button removeTripButton;
    private List<String> locationsList;
    private List<Drive> drives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_info_view);

        takeFreeSpaceButton = (Button) findViewById(R.id.takeSpaceInCar);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        editTripButton = (Button) findViewById(R.id.editTrip);
        removeTripButton = (Button) findViewById(R.id.removeTrip);

        obj = getIntent().getParcelableExtra("obj");
        TextView name = (TextView) findViewById(R.id.driverName);
        name.setText(obj.getUser());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestPackage rp = new RequestPackage();
                rp.setMethod("POST");
                rp.setUri("http://193.40.243.200/soidutaja_php/");
                rp.setParam("allDrives", "yes");

                Task task = new Task();
                task.execute(rp);
            }
        });
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
        locationsList = getIntent().getStringArrayListExtra("loc");
        //Log.d("lammason", "asd" + locationsList.get(1));
        if(getIntent().getStringExtra("Virgo")!=null){
            Log.d("lammas", getIntent().getStringExtra("Virgo"));
            editTripButton.setVisibility(View.INVISIBLE);
            removeTripButton.setVisibility(View.INVISIBLE);
        }else if(getIntent().getStringExtra("Kristi")!=null){
            takeFreeSpaceButton.setVisibility(View.INVISIBLE);
            removeTripButton.setVisibility(View.INVISIBLE);
        }else if(getIntent().getStringExtra("Tõnis")!=null){
            editTripButton.setVisibility(View.INVISIBLE);
            takeFreeSpaceButton.setVisibility(View.INVISIBLE);
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

        editTripButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                ArrayList<Drive> drives = new ArrayList<>();
                drives.add(obj);
                Intent intent = new Intent(TripInfoView.this, MakeDriveActivity.class);
                intent.putExtra("info", (ArrayList<Drive>) drives);
                intent.putExtra("loc", (ArrayList<String>) locationsList);
                startActivity(intent);
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trip_info_view, menu);
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

    public void requestData() {
        RequestPackage p = new RequestPackage();
        p.setMethod("POST");
        p.setUri("http://193.40.243.200/soidutaja_php/");
        p.setParam("driveIdTakeSlot", obj.getId());
        TakeSlot takeSlot = new TakeSlot();
        takeSlot.execute(p);

    }

    public void done(String s) {
        Toast.makeText(this, "Sind on lisatud valitud sõidule!", Toast.LENGTH_LONG).show();
        obj.setAvailableSlots(obj.getAvailableSlots() - 1);
        TextView slots = (TextView) findViewById(R.id.slots);
        slots.setText("" + obj.getAvailableSlots());
    }

    private class TakeSlot extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            done(s);
        }
    }
    private class Task extends AsyncTask<RequestPackage, String, String> {

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
            if (result == null) {
                Log.d("lammas", "somthing went wrong");
            } else {
                Log.d("lammas", result);
                drives = JSONParser.parseDriveObjects(result);
                Intent intent = new Intent(TripInfoView.this, ProfileViewActivity.class);
                intent.putExtra("fId",obj.getfId());
                intent.putExtra("user", "anotheruser");
                intent.putExtra("name", obj.getUser());
                startActivity(intent);
                intent.putParcelableArrayListExtra("driverList", (ArrayList<? extends Parcelable>) drives);
//                intent.putExtra("locationsList", (ArrayList<String>) locations);
                intent.putParcelableArrayListExtra("passengerList", (ArrayList<? extends Parcelable>) drives);
                startActivity(intent);
                finish();
            }
        }
    }
}