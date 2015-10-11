package ee.soidutaja.soidutaja;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
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

        String[] locations = new String[]{"Tallinn", "Tartu", "Türi", "Pärnu", "Võru"};
        ArrayAdapter<String> adapterEnd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        endSpinner.setAdapter(adapterEnd);
        startSpinner.setAdapter(adapterEnd);

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInputValid()) {
                    new AlertDialog.Builder(MakeDriveActivity.this)
                            .setTitle("Soidu kinnitus")
                            .setMessage("Kas kinnitan su soidu?")
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
            }
        });
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
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isSpotsValid() {
        if(spots.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(MakeDriveActivity.this, "Lisa kohtade arv", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isNameValid() {
        if(name.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(MakeDriveActivity.this, "Lisa enda nimi", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public void showTimePickerDialog(View v) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(MakeDriveActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Log.d("lammas", "aeg on settitud");
                timeHour.setText("" + selectedHour);
                timeMinute.setText("" + selectedMinute);
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
                dateDD.setText("" + dayOfMonth);
                dateMM.setText("" + monthOfYear);
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.setTitle("Select Date");
        datePicker.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_drive, menu);
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
