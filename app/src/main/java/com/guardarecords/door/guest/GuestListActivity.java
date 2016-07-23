package com.guardarecords.door.guest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.guardarecords.door.R;

public class GuestListActivity extends AppCompatActivity implements GuestListView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_list_activity);
    }

    @Override
    public void showEarnings() {

    }

    @Override
    public void showGuests() {

    }
}
