package com.guardarecords.door.guest;

import com.guardarecords.door.App;
import com.memtrip.sqlking.gen.Q;
import com.memtrip.sqlking.operation.function.Select;
import com.memtrip.sqlking.operation.keyword.OrderBy;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
                        view.showGuests(guests);
                        view.showEarnings(String.valueOf(calculateEarnings(guests)));
                    }
                });
    }

    private double calculateEarnings(Guest[] guests) {
        double total = 0;

        for (Guest guest : guests) {
            total += guest.getPrice();
        }

        return total;
    }
}
