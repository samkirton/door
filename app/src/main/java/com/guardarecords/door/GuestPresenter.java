package com.guardarecords.door;

public class GuestPresenter {

    private GuestView view;

    public GuestPresenter(GuestView view) {
        this.view = view;
    }

    public void saveGuest(Gender gender, Entry entry, How how, double currency) {

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
