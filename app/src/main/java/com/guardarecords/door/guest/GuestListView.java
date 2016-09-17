package com.guardarecords.door.guest;


import java.util.List;

public interface GuestListView {
    void showEarnings(String earnings);
    void showGuests(List<Guest> guests);
    void errorDeletingGuest();
    void guestDeleted(Guest guest);
}
