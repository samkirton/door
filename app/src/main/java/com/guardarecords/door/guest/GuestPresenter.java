package com.guardarecords.door.guest;

import com.guardarecords.door.App;
import com.memtrip.sqlking.operation.function.Insert;

import rx.functions.Action1;

public class GuestPresenter {

    private GuestView view;

    public GuestPresenter(GuestView view) {
        this.view = view;
    }

    public void saveGuest(Gender gender, Entry entry, How how, double price) {
        Guest guest = new Guest();
        guest.setGender(gender.toString());
        guest.setEntry(entry.toString());
        guest.setHow(how.toString());
        guest.setPrice(price);
        guest.setTime(System.currentTimeMillis());

        Insert.getBuilder()
                .values(guest)
                .rx(App.getInstance().getSQLProvider())
                .subscribe(
                        new Action1<Void>() {
                               @Override
                               public void call(Void aVoid) {
                                   view.done();
                               }
                           },
                        new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    view.error();
                                }
                        });
    }

    public void validateChanges(boolean gender, boolean entry, boolean how, boolean price) {

        if (gender && entry && how && price) {
            view.formReady();
        } else {
            view.formNotReady();
        }
    }

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum Entry {
        GUEST,
        CONCESSION,
        DOOR,
    }

    public enum How {
        SOCIAL_MEDIA,
        FRIEND,
        OTHER
    }
}
