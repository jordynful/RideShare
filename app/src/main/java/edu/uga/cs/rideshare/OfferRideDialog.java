package edu.uga.cs.rideshare;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class OfferRideDialog extends DialogFragment {

    private Context mContext;

    private String timeString;
    private String dateString;
    private String destinationString;
    private String id;

    private EditText timeEdit;
    private EditText dateEdit;
    private EditText destinationEdit;

    private Rides hostFragment;
    private Button button;
    private String driverName;

    public interface OfferRideDialogListener {

        String time = "3:30 PM";
        String driver = "John Doe";
        String rider = "Jane Smith";
        String driverId = "abc123";
        String riderId = "def456";
        String destination = "123 Main St";
        String date = "2023-05-01";
        void updateRide(String rideId, String time, String destination, String date);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.fragment_offer_ride_dialog, null);
        Bundle args = getArguments();
        id= "";
        String time = "";
        String destination= "";
        String date= "";
        if (args !=null) {
            id = args.getString("id");
            System.out.println("getting id from arg");
            System.out.println(id);
            time = args.getString("time");
            destination = args.getString("destination");
            date = args.getString("date");

        }
        System.out.println(id);
        timeEdit = layout.findViewById(R.id.editTextTime0);
        destinationEdit = layout.findViewById(R.id.editTextDestination0);
        dateEdit = layout.findViewById(R.id.editTextDate20);

        //set texts
        timeEdit.setText(time);
        destinationEdit.setText(destination);
        dateEdit.setText(date);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "Update Ride" );
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        builder.setPositiveButton( "Update", new ButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class ButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            timeString = timeEdit.getText().toString();
            destinationString = destinationEdit.getText().toString();
            dateString = dateEdit.getText().toString();
            String rideId = id;

            // add the new job lead

            hostFragment.updateRide(rideId,timeString,destinationString,  dateString);
            // close the dialog
            dismiss();
        }
    }

    public void setHostFragment( Rides hostFragment )
    {
        this.hostFragment = hostFragment;
    }
}
