package com.guardarecords.door.guest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guardarecords.door.R;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> {
    private Guest[] guests;
    private Context context;

    public GuestAdapter(Context context) {
        this.context = context;
    }

    public void addAll(Guest[] guests) {
        this.guests = guests;
        notifyDataSetChanged();
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guest_adapter, parent, false);

        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        Guest comment = guests[position];
        holder.populate(comment, position, context);
    }

    @Override
    public int getItemCount() {
        return guests != null ? guests.length : 0;
    }

    public static class GuestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.guest_adapter_container)
        View container;

        @BindView(R.id.guest_adapter_entry)
        TextView entry;

        @BindView(R.id.guest_adapter_gender)
        TextView gender;

        @BindView(R.id.guest_adapter_timestamp)
        TextView timestamp;

        @BindView(R.id.guest_adapter_price)
        TextView price;

        private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("EEE d MMM hh:mm")
                .withLocale(Locale.UK);

        public GuestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populate(Guest guest, int position, Context context) {

            if (position % 2 == 0) {
                container.setBackgroundColor(context.getResources().getColor(R.color.guest_list_row_background_1));
            } else {
                container.setBackgroundColor(context.getResources().getColor(R.color.guest_list_row_background_2));
            }

            entry.setText(guest.getEntry());
            gender.setText(guest.getGender());
            timestamp.setText(dateTimeFormatter.print(guest.getTime()));
            price.setText(context.getResources().getString(R.string.guest_list_earnings, guest.getPrice()));
        }
    }
}
