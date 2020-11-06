package com.bradley.kasicuisines.SendNotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.bradley.kasicuisines.RestaurantPanel_BottomNavigation;
import com.bradley.kasicuisines.CustomerPanel_BottomNavigation;
import com.bradley.kasicuisines.DeliveryPanel_BottomNavigation;
import com.bradley.kasicuisines.MainActivity;
import com.bradley.kasicuisines.businessFoodPanel.RestaurantPreparedOrderView;
import com.bradley.kasicuisines.customerFoodPanel.PayableOrders;
import com.bradley.kasicuisines.R;

import java.util.Random;

public class ShowNotification {

    public static void ShowNotif(Context context, String title, String message, String page) {
        String CHANNEL_ID = "NOTICE";
        String CHANNEL_NAME = "NOTICE";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Intent acIntent = new Intent(context, MainActivity.class);
        acIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        if (page.trim().equalsIgnoreCase("Order")) {
            acIntent = new Intent(context, RestaurantPanel_BottomNavigation.class).putExtra("PAGE", "OrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Payment")) {
            acIntent = new Intent(context, PayableOrders.class);
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Home")) {
            acIntent = new Intent(context, CustomerPanel_BottomNavigation.class).putExtra("PAGE", "HomePage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Confirm")) {
            acIntent = new Intent(context, RestaurantPanel_BottomNavigation.class).putExtra("PAGE", "ConfirmPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Preparing")) {
            acIntent = new Intent(context, CustomerPanel_BottomNavigation.class).putExtra("PAGE", "PreparingPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Prepared")) {
            acIntent = new Intent(context, CustomerPanel_BottomNavigation.class).putExtra("PAGE", "PreparedPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("DeliveryOrder")) {
            acIntent = new Intent(context, DeliveryPanel_BottomNavigation.class).putExtra("PAGE", "DeliveryOrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("DeliverOrder")) {
            acIntent = new Intent(context, CustomerPanel_BottomNavigation.class).putExtra("PAGE", "DeliveryOrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("AcceptOrder")) {
            acIntent = new Intent(context, RestaurantPanel_BottomNavigation.class).putExtra("PAGE", "AcceptOrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("RejectOrder")) {
            acIntent = new Intent(context, RestaurantPreparedOrderView.class).putExtra("PAGE", "RejectOrderPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("ThankYou")) {
            acIntent = new Intent(context, CustomerPanel_BottomNavigation.class).putExtra("PAGE", "ThankYouPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (page.trim().equalsIgnoreCase("Delivered")) {
            acIntent = new Intent(context, RestaurantPanel_BottomNavigation.class).putExtra("PAGE", "DeliveredPage");
            pendingIntent = PendingIntent.getActivity(context, 0, acIntent, PendingIntent.FLAG_ONE_SHOT);
        }


        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_chef_hat_and_fork)
                .setColor(ContextCompat.getColor(context,R.color.colorAccent))
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        int random = new Random().nextInt(9999 - 1) + 1;
        notificationManagerCompat.notify(random, nBuilder.build());
    }
}
