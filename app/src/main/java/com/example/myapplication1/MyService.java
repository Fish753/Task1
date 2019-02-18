package com.example.myapplication1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract;

public class MyService extends Service {

    ExecutorService es;

    public void onCreate() {
        super.onCreate();
        es = Executors.newFixedThreadPool(1);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        MyRun mr = new MyRun(startId);
        es.execute(mr);
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyRun implements Runnable {
        int startId;

        public MyRun(int startId) {
            this.startId = startId;
        }

        public void run() {
            Intent intent = new Intent(TwoActivity.BROADCAST_ACTION);
            try {
                TimeUnit.SECONDS.sleep(3);
                String f = String.valueOf(getContactNames());
                intent.putExtra("rex", f);
                sendBroadcast(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stopSelfResult(startId);
        }
    }

    public List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        ContentResolver cr = this.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contacts;
    }
}