package com.example.sanpablook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanpablook_establishment.R;

import java.util.List;
import java.util.Map;

public class RecyclerPastWeek extends RecyclerView.Adapter<RecyclerPastWeek.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public RecyclerPastWeek(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_next_week_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        holder.txtPastWeekBookingNumber.setText(booking.get("place").toString());
        holder.txtPastWeekCustomerName.setText(booking.get("bookingID").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView txtPastWeekBookingNumber, txtPastWeekCustomerName;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            txtPastWeekBookingNumber = itemView.findViewById(R.id.txtPastWeekBookingNumber);
            txtPastWeekCustomerName = itemView.findViewById(R.id.txtPastWeekCustomerName);
        }
    }
}