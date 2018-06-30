package com.example.lekshmi.experiment;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class SMSReceiver extends BroadcastReceiver
{

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();
                    putSmsToDatabase(currentMessage,context);
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }




    private void putSmsToDatabase(SmsMessage sms, Context context )
    {
        DatabaseHelper dataBaseHelper = new DatabaseHelper(context);

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
// Create SMS row
        ContentValues values = new ContentValues();

        values.put("address", sms.getOriginatingAddress().toString() );
        values.put("date", mydate);
        values.put("body", sms.getMessageBody().toString());
// values.put( READ, MESSAGE_IS_NOT_READ );
// values.put( STATUS, sms.getStatus() );
// values.put( TYPE, MESSAGE_TYPE_INBOX );
// values.put( SEEN, MESSAGE_IS_NOT_SEEN );

        db.insert("datatable",null,values);

        db.close();

    }
}
