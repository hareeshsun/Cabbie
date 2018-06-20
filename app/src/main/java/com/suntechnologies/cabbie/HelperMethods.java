package com.suntechnologies.cabbie;

import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by hareeshs on 20-06-2018.
 */

public class HelperMethods {

    public static void showDialog(String title, String content)
    {
        try
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SunCabbie.getCurrentSession().getCurrentActivity());
            if (!(title.trim().equalsIgnoreCase("")) && (title.trim().length() > 0))
                alertDialog.setTitle(title);
            alertDialog.setMessage(content);
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

}
