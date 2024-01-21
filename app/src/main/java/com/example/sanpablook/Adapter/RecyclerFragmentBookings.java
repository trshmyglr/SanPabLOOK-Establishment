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

public class RecyclerFragmentBookings extends RecyclerView.Adapter<RecyclerFragmentBookings.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public RecyclerFragmentBookings(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_fragment_bookings, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        holder.bookingStatus.setText(booking.get("place").toString());
        holder.valueOfBookingNumber.setText(booking.get("bookingID").toString());
        holder.valueOfCustomerName.setText(booking.get("bookingID").toString());
        holder.valueOfBookingDate.setText(booking.get("bookingID").toString());
        holder.valueOfBookingTime.setText(booking.get("bookingID").toString());
        holder.valueOfNumberOfGuests.setText(booking.get("bookingID").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        TextView bookingStatus, valueOfBookingNumber, valueOfCustomerName, valueOfBookingDate, valueOfBookingTime, valueOfNumberOfGuests;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            bookingStatus = itemView.findViewById(R.id.bookingStatus);
            valueOfBookingNumber = itemView.findViewById(R.id.valueOfBookingNumber);
            valueOfCustomerName = itemView.findViewById(R.id.valueOfCustomerName);
            valueOfBookingDate = itemView.findViewById(R.id.valueOfBookingDate);
            valueOfBookingTime = itemView.findViewById(R.id.valueOfBookingTime);
            valueOfNumberOfGuests = itemView.findViewById(R.id.valueOfNumberOfGuests);
        }
    }
}