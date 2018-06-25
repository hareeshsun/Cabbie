package com.suntechnologies.cabbie;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.suntechnologies.cabbie.Fragments.EmployeeFragment;

import java.util.regex.Pattern;

/**
 * Created by hareeshs on 20-06-2018.
 */

public class HelperMethods {


    public static void replaceFragment(Context context, int containerId, Fragment fragment, boolean addToBackStack)
    {
        try
        {
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (addToBackStack)
                fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(containerId, fragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (IllegalArgumentException | IllegalStateException iae)
        {
            iae.printStackTrace();
        }
    }

    public static void showDialog(Context context,String title, String message)
    {
        try
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            if (!(title.trim().equalsIgnoreCase("")) && (title.trim().length() > 0))
                alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static  boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

}
