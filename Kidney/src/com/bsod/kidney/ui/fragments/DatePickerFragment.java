package com.bsod.kidney.ui.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.bsod.kidney.ForDriversActivity;

/**
 * Created by Gago on 3/7/2015.
 */
public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


    public static String DATE_STRING = "date_string";
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);



// Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(c.getTime());

            ForDriversActivity ma = (ForDriversActivity) getActivity();
            ma.updateDate(formattedDate);
        }

		
    }
