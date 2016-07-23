package com.guardarecords.door;

import android.app.Application;

import com.guardarecords.door.guest.Guest;
import com.memtrip.sqlking.database.SQLInit;
import com.memtrip.sqlking.database.SQLProvider;
import com.memtrip.sqlking.gen.Q;

public class App extends Application {

    private static App instance;

    private SQLProvider sqlProvider;

    private static final String DATABASE_NAME = "guarda_door";
    private static final int DATABASE_VERSION = 1;

    public static App getInstance() {
        return instance;
    }

    public SQLProvider getSQLProvider() {
        return sqlProvider;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

       sqlProvider = SQLInit.createDatabase(
                DATABASE_NAME,
                DATABASE_VERSION,
                new Q.DefaultResolver(),
                getApplicationContext(),
                Guest.class
        );
    }
}
