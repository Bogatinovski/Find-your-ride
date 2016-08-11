package com.bsod.kidney.ui.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.TimePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.bsod.kidney.ForDriversActivity;

/**
 * Created by Gago on 3/7/2015.
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d("time",hourOfDay + " " + minute);

        Calendar c = Calendar.getInstance();
        c.set(0,0,0,hourOfDay,minute);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formattedDate = sdf.format(c.getTime());

        ForDriversActivity ma = (ForDriversActivity) getActivity();
        ma.updateTime(formattedDate);

    }
}
