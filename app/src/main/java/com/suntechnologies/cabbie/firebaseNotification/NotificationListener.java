package com.suntechnologies.cabbie.firebaseNotification;

import org.json.JSONObject;

/**
 * Created by mithulalr on 7/30/2018.
 */

public interface NotificationListener
{
   void notificationKey(JSONObject notificationValue);
   void error(String error);

}
