package ee.soidutaja.soidutaja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Virx on 12.10.2015.
 */
public class DriveAdapter extends ArrayAdapter<Drive> {

    private final Context context;
    private List<Drive> drives;

    public DriveAdapter(Context context, List<Drive> drives) {
        super(context, 0, drives);
        this.context = context;
        this.drives = drives;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Drive drive = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drive_list_layout, parent, false);
        }
        // Lookup view for data population
        TextView origin = (TextView) convertView.findViewById(R.id.origin);
        TextView destination  = (TextView) convertView.findViewById(R.id.destination);
        TextView user = (TextView) convertView.findViewById(R.id.user);
        // Populate the data into the template view using the data object
        origin.setText(drive.getOrigin());
        destination.setText(drive.getDestination());
        user.setText(drive.getUser());
        // Return the completed view to render on screen
        return convertView;
    }
}
