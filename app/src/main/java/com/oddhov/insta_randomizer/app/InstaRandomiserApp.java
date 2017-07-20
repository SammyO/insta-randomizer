package com.oddhov.insta_randomizer.app;

import android.app.Application;

import com.oddhov.insta_randomizer.utils.DatabaseUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;

public class InstaRandomiserApp extends Application {
    private RealmConfiguration mRealmConfiguration;
    private DatabaseUtils mDatabaseUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        mDatabaseUtils = new DatabaseUtils(getApplicationContext());

        if (mRealmConfiguration == null) {
            Realm.init(this);
            mRealmConfiguration = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded() // TODO
                    .build();
            Realm.setDefaultConfiguration(mRealmConfiguration);
        }
        try {
            mDatabaseUtils.ensureUserDataIsSetup();
        } catch (RealmException e) {
            e.printStackTrace();
        }
    }
}
