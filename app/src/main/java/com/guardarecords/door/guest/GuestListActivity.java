package com.guardarecords.door.guest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.guardarecords.door.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestListActivity extends AppCompatActivity implements GuestListView {

    @BindView(R.id.guest_list_earnings)
    TextView earningsView;

    @BindView(R.id.guest_list_recyclerview)
    RecyclerView guestsView;

    private AlertDialog deleteAlertDialog;

    private GuestListPresenter presenter;

    private GuestAdapter guestAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_list_activity);
        ButterKnife.bind(this);

        presenter = new GuestListPresenter(this);

        guestAdapter = new GuestAdapter(this, new GuestAdapter.Listener() {
            @Override
            public void longPress(Guest guest) {
                showDeleteDialog(guest);
            }
        });

        guestsView.setLayoutManager(new LinearLayoutManager(this));
        guestsView.setAdapter(guestAdapter);
    }

    private void showDeleteDialog(final Guest guest) {
        deleteAlertDialog = new AlertDialog.Builder(this)
                .setTitle("Remove guest?")
                .setMessage("Would you like to remove the guest?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteGuest(guest);
                    }
                })
                .create();

        deleteAlertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guest_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.guest_list_add) {
            Intent intent = new Intent(this, GuestActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEarnings(String earnings) {
        earningsView.setText(getResources().getString(R.string.guest_list_earnings, earnings));
    }

    @Override
    public void showGuests(List<Guest> guests) {
        guestAdapter.addAll(guests);
    }

    @Override
    public void errorDeletingGuest() {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage("Could not delete the guest.")
                .setPositiveButton("Ok", null)
                .show();
    }

    @Override
    public void guestDeleted(final Guest guest) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                deleteAlertDialog.dismiss();
                guestAdapter.remove(guest);
                presenter.showCalculateEarnings(guestAdapter.getGuests());
            }
        });
    }
}
