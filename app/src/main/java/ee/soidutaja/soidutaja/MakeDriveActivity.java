package ee.soidutaja.soidutaja;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MakeDriveActivity extends AppCompatActivity {

    private Spinner startSpinner;
    private Spinner endSpinner;
    private TextView date;
    private TextView time;
    private Button nxtBtn;
    private EditText price;
    private EditText spots;
    private EditText info;
    private String dateString;
    private String timeString;
    private Drive drive;
    private String id;
    ArrayAdapter<String> adapterEnd;
    List<Drive> driveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_drive);

        startSpinner = (Spinner) findViewById(R.id.startSpinner);
        endSpinner = (Spinner) findViewById(R.id.endSpinner);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        price = (EditText) findViewById(R.id.priceField);
        spots = (EditText) findViewById(R.id.spotsField);

        nxtBtn = (Button) findViewById(R.id.nextButton);
        info = (EditText) findViewById(R.id.additionalInfo);

        List<String> locations = getIntent().getStringArrayListExtra("loc");
        adapterEnd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        endSpinner.setAdapter(adapterEnd);
        startSpinner.setAdapter(adapterEnd);

        driveList = getIntent().getParcelableArrayListExtra("info");
        if (driveList != null) {
            Drive localDrive = driveList.get(0);
            setDriveInfo(localDrive);

        }
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInputValid()) {
                    new AlertDialog.Builder(MakeDriveActivity.this)
                            .setTitle("Soidu kinnitus")
                            .setMessage("Kas kinnitan su sõidu?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    drive = new Drive();
                                    drive.setOrigin(startSpinner.getSelectedItem().toString());
                                    drive.setDestination(endSpinner.getSelectedItem().toString());
                                    drive.setDateTime(dateString + " " + timeString);
                                    drive.setAvailableSlots(Integer.parseInt(spots.getText().toString()));
                                    drive.setPrice(price.getText().toString());
                                    drive.setUser(SharedPreferencesManager.readData(getBaseContext())[0]);
                                    drive.setfId(SharedPreferencesManager.readData(getBaseContext())[1]);
                                    if(info.getText().toString().matches("")) {
                                        drive.setInfo("Puudub");
                                    }
                                    else {
                                        drive.setInfo(info.getText().toString());
                                    }
                                    pushDrive(drive);
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
            }
        });
    }

    private void setDriveInfo(Drive drive) {

        //date.setText(drive.getDateTime());
        String dateTime= drive.getDateTime();
        String formatDate =dateTime.substring(0,10);
        String formatTime= dateTime.substring(11);
        String id = drive.getId();
        date.setText(formatDate);
        time.setText(formatTime);
        price.setText(drive.getPrice());
        info.setText(drive.getInfo());
        spots.setText("" + drive.getAvailableSlots());
        String origin= drive.getOrigin();
        String destination= drive.getDestination();
        endSpinner.setAdapter(adapterEnd);
        startSpinner.setAdapter(adapterEnd);
        if (!origin.equals(null)) {
            int spinnerPosition = adapterEnd.getPosition(origin);
            startSpinner.setSelection(spinnerPosition);
        }
        if (!destination.equals(null)) {
            int spinnerPosition = adapterEnd.getPosition(destination);
            endSpinner.setSelection(spinnerPosition);
        }
    }

    public void pushDrive(Drive drive) {
        RequestPackage rp = new RequestPackage();
        rp.setMethod("POST");
        rp.setUri("http://193.40.243.200/soidutaja_php/");
        rp.setParam("origin", drive.getOrigin());
        rp.setParam("destination", drive.getDestination());
        rp.setParam("name", SharedPreferencesManager.readData(this.getBaseContext())[0]);
        rp.setParam("fbId", SharedPreferencesManager.readData(this.getBaseContext())[1]);
        rp.setParam("price", drive.getPrice());
        rp.setParam("openSlots", "" + drive.getAvailableSlots());
        rp.setParam("dateTime", drive.getDateTime());
        rp.setParam("description", drive.getInfo());
        if(driveList != null) {
            rp.setParam("editDrive", "yes");
        }
        PushPackage pb = new PushPackage();
        pb.execute(rp);

    }

    public boolean isInputValid() {
        if(areSpinnersValid() && isTimeValid() && isDateValid() && isPriceValid() && isSpotsValid() /* && isNameValid()*/) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean areSpinnersValid() {
        if(startSpinner.getSelectedItem().toString().equalsIgnoreCase(endSpinner.getSelectedItem().toString())) {
            Toast.makeText(MakeDriveActivity.this, "Algus ja sihtkoht ei tohi olla samad", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(startSpinner == null || endSpinner == null) {
            Toast.makeText(MakeDriveActivity.this, "Vali algus ja sihtkoht", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isTimeValid() {
        if(time.getText().equals("HOUR:MINUTE")) {
            Toast.makeText(MakeDriveActivity.this, "Vale kellaaeg", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isDateValid() {
        if(date.getText().equals("DD/MM")) {
            Toast.makeText(MakeDriveActivity.this, "Vale kuup2ev", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isPriceValid() {
        if(price.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(MakeDriveActivity.this, "Lisa hind", Toast.LENGTH_SHORT).show();
            price.setError("Lisa hind");
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isSpotsValid() {
        if(spots.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(MakeDriveActivity.this, "Lisa kohtade arv", Toast.LENGTH_SHORT).show();
            spots.setError("Lisa vabade kohtade arv");
            return false;
        }
        else {
            return true;
        }
    }
//    TODO do not DELETE!!
//    public boolean isNameValid() {
//        if(name.getText().toString().equalsIgnoreCase("")) {
//            Toast.makeText(MakeDriveActivity.this, "Lisa enda nimi", Toast.LENGTH_SHORT).show();
//            name.setError("Lisa enda nimi!");
//            return false;
//        }
//        else {
//            return true;
//        }
//    }

    public void showTimePickerDialog(View v) {
        Calendar currentTime = Calendar.getInstance();
        final int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(MakeDriveActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Log.d("lammas", "aeg on settitud");
                String hour = "" + selectedHour;
                String minute = "" + selectedMinute;

                if(hour.length() < 2) {
                    hour = 0 + hour;
                }
                if(minute.length() < 2) {
                    minute = 0 + minute;
                }

                timeString = hour + ":" + minute + ":00";
                Log.d("lammas", timeString);
                time.setText(timeString);
            }
        }, hour, minute, true);//Yes 24 hour time
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    public void showDatePickerDialog(View v) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePicker;
        datePicker = new DatePickerDialog(MakeDriveActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("lammas", "kuup2ev on settitud");
                String month = "" + monthOfYear;
                String day = "" + dayOfMonth;
                if(month.length() < 2) {
                    month = 0 + month;
                }
                if(day.length() < 2) {
                    day = 0 + day;
                }

                dateString = year + "-" + month + "-" + day;
                date.setText(dateString);
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.setTitle("Select Date");
        datePicker.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_make_drive, menu);
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
    private class PushPackage extends AsyncTask<RequestPackage, String, String> {

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Intent intent = new Intent(MakeDriveActivity.this, ProfileViewActivity.class);
            intent.putExtra("driveObj", drive);
            startActivity(intent);
        }
    }
}
