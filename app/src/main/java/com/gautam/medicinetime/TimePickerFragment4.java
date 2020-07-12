package com.gautam.medicinetime;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment4 extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current time as the default values for the picker

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user


        //Toast.makeText(getActivity(), "Done"+alarmStartTime, Toast.LENGTH_SHORT).show();
        TextView tv2=(TextView) getActivity().findViewById(R.id.clinictimepicker2);
        TextView tv3=(TextView) getActivity().findViewById(R.id.clinicmin1);
        tv2.setText(""+view.getCurrentHour());
        tv3.setText(""+view.getCurrentMinute());
    }
}
