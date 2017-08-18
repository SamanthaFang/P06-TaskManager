package com.myapplicationdev.android.p06_taskmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.NotificationCompat;

public class TaskReminderReceiver extends BroadcastReceiver {

    int notifReqCode = 123;

	@Override
	public void onReceive(Context context, Intent i) {

		Task task = (Task) i.getSerializableExtra("task");

		int id = i.getIntExtra("id", -1);
		String name = i.getStringExtra("name");
		String desc = i.getStringExtra("desc");

		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Action action = new
				NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher,"Launch Task Manager",pIntent).build();

		Intent intentreply = new Intent(context, MainActivity.class);
		intentreply.putExtra("task", task);
		PendingIntent pendingIntentReply = PendingIntent.getActivity(context, 0, intentreply, PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteInput ri = new RemoteInput.Builder("status")
				.setLabel("status report")
				.setChoices(new String []{"Completed", "Not yet"})
				.build();
		NotificationCompat.Action action2 = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Reply", pendingIntentReply)
				.addRemoteInput(ri)
				.build();






		Intent intentadd = new Intent(context, AddActivity.class);
		PendingIntent pendingIntentAdd = PendingIntent.getActivity(context, 0, intentadd, PendingIntent.FLAG_UPDATE_CURRENT);

		RemoteInput riadd = new RemoteInput.Builder("add")
				.setLabel("Add New Task")
				.build();


		NotificationCompat.Action action3 = new NotificationCompat.Action.Builder(
				R.mipmap.ic_launcher, "Add",
				pendingIntentAdd)
				.addRemoteInput(riadd)
				.build();
		NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
		extender.addAction(action);
		extender.addAction(action2);
		extender.addAction(action3);



		// build notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setContentTitle("Task Manager Reminder");
		builder.setContentText(name);
		builder.setSmallIcon(android.R.drawable.ic_dialog_info);
		builder.setContentIntent(pIntent);
		builder.setAutoCancel(true);

		Notification n = builder.build();

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// You may put an ID for the first parameter if you wish
		// to locate this notification to cancel
		notificationManager.notify(notifReqCode, n);
	}

}
