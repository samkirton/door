package com.guardarecords.door.guest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import com.guardarecords.door.R;

public class GuestActivity extends AppCompatActivity implements GuestView {

    @BindView(R.id.guest_gender_male)
    View genderMale;

    @BindView(R.id.guest_gender_female)
    View genderFemale;


    @BindView(R.id.guest_entry_guest)
    View entryGuest;

    @BindView(R.id.guest_entry_concession)
    View entryConcession;

    @BindView(R.id.guest_entry_door)
    View entryDoor;


    @BindView(R.id.guest_how_social_media)
    View howSocialMedia;

    @BindView(R.id.guest_how_friend)
    View howFriend;

    @BindView(R.id.guest_how_other)
    View howOther;


    @BindView(R.id.guest_price_3)
    View entryPrice3;

    @BindView(R.id.guest_price_5)
    View entryPrice5;


    @BindView(R.id.guest_done)
    Button done;


    private GuestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_activity);
        ButterKnife.bind(this);

        presenter = new GuestPresenter(this);

        entryDoor.setSelected(true);
        howOther.setSelected(true);
        entryPrice5.setSelected(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.guest_gender_male)
    public void clickMale() {
        toggleView(genderMale, genderFemale);
    }

    @OnClick(R.id.guest_gender_female)
    public void clickFemale() {
        toggleView(genderFemale, genderMale);
    }

    @OnClick(R.id.guest_entry_guest)
    public void clickGuest() {
        toggleView(entryGuest,
                entryConcession, entryDoor);

    }

    @OnClick(R.id.guest_entry_concession)
    public void clickConcession() {
        toggleView(entryConcession,
                entryGuest, entryDoor);

    }

    @OnClick(R.id.guest_entry_door)
    public void clickDoor() {
        toggleView(entryDoor,
                entryGuest, entryConcession);

    }

    @OnClick(R.id.guest_how_social_media)
    public void clickSocial() {
        toggleView(howSocialMedia,
                howFriend, howOther);
    }

    @OnClick(R.id.guest_how_friend)
    public void clickFriend() {
        toggleView(howFriend,
                howSocialMedia, howOther);
    }

    @OnClick(R.id.guest_how_other)
    public void clickOther() {
        toggleView(howOther,
                howFriend, howSocialMedia);
    }

    @OnClick(R.id.guest_price_3)
    public void clickPrice3() {
        toggleView(entryPrice3,
                entryPrice5);
    }

    @OnClick(R.id.guest_price_5)
    public void clickPrice5() {
        toggleView(entryPrice5,
                entryPrice3);
    }

    private void toggleView(View selected, View ... off) {
        selected.setSelected(true);

        for (View view : off) {
            view.setSelected(false);
        }
    }

    @OnClick(R.id.guest_done)
    public void doneClick() {
        presenter.saveGuest(
                getGender(genderMale),
                getEntry(entryGuest, entryConcession),
                getHow(howSocialMedia, howFriend),
                getCurrency(entryPrice3)
        );
    }

    @OnFocusChange(R.id.guest_gender_male)
    public void switchMale() {
        genderFemale.setSelected(false);
        genderMale.setSelected(true);
    }

    @OnFocusChange(R.id.guest_gender_female)
    public void switchFemale() {
        genderFemale.setSelected(true);
        genderMale.setSelected(false);
    }

    @OnClick({
            R.id.guest_gender_male,
            R.id.guest_gender_female,

            R.id.guest_entry_guest,
            R.id.guest_entry_concession,
            R.id.guest_entry_door,

            R.id.guest_how_social_media,
            R.id.guest_how_friend,
            R.id.guest_how_other,

            R.id.guest_price_3,
            R.id.guest_price_5
    })
    public void validateChanges() {
        presenter.validateChanges(
                hasGender(genderMale, genderFemale),
                hasEntry(entryGuest, entryConcession, entryDoor),
                hasHow(howSocialMedia, howFriend, howOther),
                hasPrice(entryPrice3, entryPrice5)
        );
    }

    private boolean hasGender(View male, View female) {
        return male.isSelected() || female.isSelected();
    }

    private boolean hasEntry(View guest, View concession, View door) {
        return guest.isSelected() || concession.isSelected() || door.isSelected();
    }

    private boolean hasHow(View socialMedia, View friend, View other) {
        return socialMedia.isSelected() || friend.isSelected() || other.isSelected();
    }

    private boolean hasPrice(View entryPrice3, View entryPrice5) {
        return entryPrice3.isSelected() || entryPrice5.isSelected();
    }

    @Override
    public void formReady() {
        done.setEnabled(true);
    }

    @Override
    public void formNotReady() {
        done.setEnabled(false);
    }

    @Override
    public void done() {
        finish();
    }

    @Override
    public void error() {
        Toast.makeText(this, "App failure....", Toast.LENGTH_LONG);
    }

    private double getCurrency(View entryPrice5) {
        if (entryPrice5.isSelected()) {
            return 5.0;
        } else {
            return 7.0;
        }
    }

    private GuestPresenter.Gender getGender(View male) {
        if (male.isSelected()) {
            return GuestPresenter.Gender.MALE;
        }

        return GuestPresenter.Gender.FEMALE;
    }

    private GuestPresenter.Entry getEntry(View guest, View concession) {
        if (guest.isSelected()) {
            return GuestPresenter.Entry.GUEST;
        } else if (concession.isSelected()) {
            return GuestPresenter.Entry.CONCESSION;
        } else {
            return GuestPresenter.Entry.DOOR;
        }
    }

    private GuestPresenter.How getHow(View socialMedia, View friend) {
        if (socialMedia.isSelected()) {
            return GuestPresenter.How.SOCIAL_MEDIA;
        } else if (friend.isSelected()) {
            return GuestPresenter.How.FRIEND;
        } else {
            return GuestPresenter.How.OTHER;
        }
    }
}
