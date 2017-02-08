package com.prashantsolanki.blackshift.trans;


import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.prashantsolanki.blackshift.trans.model.Quote;
import com.prashantsolanki.blackshift.trans.widget.WidgetProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prsso on 30-01-2017.
 */

public class App extends Application {

    public static List<Quote> starred = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new MaterialModule());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference("/quotes/").keepSynced(true);

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseDatabase.getInstance().getReference("/starred/" + firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            starred.clear();
                            for (DataSnapshot d : dataSnapshot.getChildren())
                                starred.add(d.getValue(Quote.class));

                            Intent intent = new Intent(getApplicationContext(), WidgetProvider.class);
                            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                            // Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
                            // since it seems the onUpdate() is only fired on that:
                            int[] ids = AppWidgetManager.getInstance(getApplicationContext())
                                    .getAppWidgetIds(new ComponentName(getApplicationContext(), WidgetProvider.class));
                            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                            sendBroadcast(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

}
