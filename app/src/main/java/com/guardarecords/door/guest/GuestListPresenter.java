package com.guardarecords.door.guest;

import com.guardarecords.door.App;
import com.memtrip.sqlking.gen.Q;
import com.memtrip.sqlking.operation.clause.Where;
import com.memtrip.sqlking.operation.function.Delete;
import com.memtrip.sqlking.operation.function.Select;
import com.memtrip.sqlking.operation.keyword.OrderBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.memtrip.sqlking.operation.clause.Where.where;

public class GuestListPresenter {

    private GuestListView view;

    public GuestListPresenter(GuestListView view) {
        this.view = view;
    }

    public void start() {
        Select.getBuilder()
                .orderBy(Q.Guest.TIME, OrderBy.Order.DESC)
                .rx(Guest.class, App.getInstance().getSQLProvider())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Guest[]>() {
                    @Override
                    public void call(Guest[] guests) {
                        List<Guest> guestList = new ArrayList<>(Arrays.asList(guests));
                        view.showGuests(guestList);
                        view.showEarnings(String.valueOf(calculateEarnings(guestList)));
                    }
                });
    }

    public void showCalculateEarnings(List<Guest> guestList) {
        view.showEarnings(String.valueOf(calculateEarnings(guestList)));
    }

    public void deleteGuest(final Guest guest) {
        Delete.getBuilder()
                .where(where(Q.Guest.ID, Where.Exp.EQUAL_TO, guest.getId()))
                .rx(Guest.class, App.getInstance().getSQLProvider())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                               @Override
                               public void call(Integer integer) {
                                   view.guestDeleted(guest);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                view.errorDeletingGuest();
                            }
                        });
    }

    private double calculateEarnings(List<Guest> guests) {
        double total = 0;

        for (Guest guest : guests) {
            total += guest.getPrice();
        }

        return total;
    }
}
