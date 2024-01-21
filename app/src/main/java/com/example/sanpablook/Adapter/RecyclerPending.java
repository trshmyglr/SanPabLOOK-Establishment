package com.example.sanpablook.Adapter;

import static java.security.AccessController.getContext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanpablook_establishment.R;

import java.util.List;
import java.util.Map;

public class RecyclerPending extends RecyclerView.Adapter<RecyclerPending.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    public RecyclerPending(List<Map<String, Object>> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_pending_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        holder.txtPendingBooking.setText(booking.get("place").toString());
        holder.txtPendingCustomerName.setText(booking.get("bookingID").toString());
        holder.txtPendingDate.setText(booking.get("bookingID").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        Button btnAccept;
        TextView txtPendingBooking, txtPendingCustomerName, txtPendingDate;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find other TextViews
            txtPendingBooking = itemView.findViewById(R.id.txtPendingBooking);
            txtPendingCustomerName = itemView.findViewById(R.id.txtPendingCustomerName);
            txtPendingDate = itemView.findViewById(R.id.txtPendingDate);

            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToRegistrationCompleteActivity(view);
                    goToRegistrationCompleteActivity(view);
                }

                private void goToRegistrationCompleteActivity(View view) {
//                Intent intent = new Intent(getActivity(), RegistrationCompleteActivity.class);
//                startActivity(intent);
                }
            });
        }
    }
}