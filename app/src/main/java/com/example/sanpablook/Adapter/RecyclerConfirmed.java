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

public class RecyclerConfirmed extends RecyclerView.Adapter<RecyclerConfirmed.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public RecyclerConfirmed(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_confirmed_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        holder.txtBookingNumber.setText(booking.get("place").toString());
        holder.txtCustomerName.setText(booking.get("bookingID").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView txtBookingNumber, txtCustomerName;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            txtBookingNumber = itemView.findViewById(R.id.txtBookingNumber);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
        }
    }
}