package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS_FILTER = "SMS_FILTER";
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";

    // is called on new sms
    @Override
    public void onReceive(Context context, Intent intent) {

        //extract msgs into intent

        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        // iterates through each message

        for (int i = 0; i < messages.length; i++) {
            SmsMessage currentMessage = messages[i];
            String message = currentMessage.getDisplayMessageBody();

            // send broadcast with new msg

            Intent msgIntent = new Intent();
            msgIntent.setAction(SMS_FILTER);
            msgIntent.putExtra(SMS_MSG_KEY, message);
            context.sendBroadcast(msgIntent);
        }
    }
}


