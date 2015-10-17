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
    private TextView dateMM;
    private TextView dateDD;
    private TextView timeHour;
    private TextView timeMinute;
    private Button nxtBtn;
    private EditText price;
    private EditText spots;
    private EditText name;
    private EditText info;
    private String date;
    private String time;
    private Drive drive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_drive);

        startSpinner = (Spinner) findViewById(R.id.startSpinner);
        endSpinner = (Spinner) findViewById(R.id.endSpinner);
        dateDD = (TextView) findViewById(R.id.dateDay);
        dateMM = (TextView) findViewById(R.id.dateMonth);
        timeHour = (TextView) findViewById(R.id.timeHour);
        timeMinute = (TextView) findViewById(R.id.timeMinute);
        price = (EditText) findViewById(R.id.priceField);
        spots = (EditText) findViewById(R.id.spotsField);
        name = (EditText) findViewById(R.id.name);
        nxtBtn = (Button) findViewById(R.id.nextButton);
        info = (EditText) findViewById(R.id.additionalInfo);

        List<String> locations = getIntent().getStringArrayListExtra("loc");
        ArrayAdapter<String> adapterEnd = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        endSpinner.setAdapter(adapterEnd);
        startSpinner.setAdapter(adapterEnd);

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
                                    drive.setDateTime(date + " " + time);
                                    drive.setAvailableSlots(Integer.parseInt(spots.getText().toString()));
                                    drive.setPrice(price.getText().toString());
                                    drive.setUser(name.getText().toString());
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

    public void pushDrive(Drive drive) {
        RequestPackage rp = new RequestPackage();
        rp.setMethod("POST");
        rp.setUri("http://193.40.243.200/soidutaja_php/");
        rp.setParam("origin", drive.getOrigin());
        rp.setParam("destination", drive.getDestination());
        rp.setParam("name", drive.getUser());
        rp.setParam("price", drive.getPrice());
        rp.setParam("openSlots", "" + drive.getAvailableSlots());
        rp.setParam("dateTime", drive.getDateTime());
        rp.setParam("description", drive.getInfo());
        PushPackage pb = new PushPackage();
        pb.execute(rp);

    }

    public boolean isInputValid() {
        if(areSpinnersValid() && isTimeValid() && isDateValid() && isPriceValid() && isSpotsValid() && isNameValid()) {
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
        if(timeHour.getText().equals("HOUR") || timeMinute.getText().equals("MIN")) {
            Toast.makeText(MakeDriveActivity.this, "Vale kellaaeg", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isDateValid() {
        if(dateDD.getText().equals("DD") || dateMM.getText().equals("MM")) {
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

    public boolean isNameValid() {
        if(name.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(MakeDriveActivity.this, "Lisa enda nimi", Toast.LENGTH_SHORT).show();
            name.setError("Lisa enda nimi!");
            return false;
        }
        else {
            return true;
        }
    }

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

                timeHour.setText(hour);
                timeMinute.setText(minute);
                time = hour + ":" + minute;
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
                dateDD.setText(day);
                dateMM.setText(month);
                date = year + "-" + month + "-" + day;
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
            Intent intent = new Intent(MakeDriveActivity.this, CreatedDriveView.class);
            intent.putExtra("driveObj", drive);
            startActivity(intent);
        }
    }
}
