package com.example.sanpablook.Adapter;

import static java.security.AccessController.getContext;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sanpablook_establishment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecyclerPending extends RecyclerView.Adapter<RecyclerPending.BookingViewHolder> {

    private List<Map<String, Object>> bookings;

    static FirebaseFirestore db;

    public RecyclerPending(List<Map<String, Object>> bookings) {
        this.bookings = new ArrayList<>();
        for (Map<String, Object> booking : bookings) {
            if (booking.get("status").toString().equals("Pending")) {
                this.bookings.add(booking);
            }
        }
        db = FirebaseFirestore.getInstance();
        Log.d("RecyclerPending", "Number of bookings: " + this.bookings.size());
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_pending_booking, parent, false);
        return new BookingViewHolder(view, bookings);
    }

    public interface OnBookingSelectedListener {
        void onBookingSelected(long dateInMillis);
    }

    private OnBookingSelectedListener listener;

    public void setOnBookingSelectedListener(OnBookingSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Map<String, Object> booking = bookings.get(position);
        Log.d("RecyclerPending", "onBindViewHolder called for position: " + position);
        holder.txtPendingBooking.setText(booking.get("bookingID").toString());
        holder.txtPendingCustomerName.setText(booking.get("fullName").toString());
        holder.txtPendingDate.setText(booking.get("date").toString());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {

        Button btnAccept;
        TextView txtPendingBooking, txtPendingCustomerName, txtPendingDate;
        List<Map<String, Object>> bookings;

        public BookingViewHolder(@NonNull View itemView, List<Map<String, Object>> bookings) {
            super(itemView);
            this.bookings = bookings;

            // Find other TextViews
            txtPendingBooking = itemView.findViewById(R.id.txtPendingBooking);
            txtPendingCustomerName = itemView.findViewById(R.id.txtPendingCustomerName);
            txtPendingDate = itemView.findViewById(R.id.txtPendingDate);

            // Accept booking btn
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        acceptBooking(view);
                }

                private void acceptBooking(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Map<String, Object> clickedBooking = bookings.get(position);
                        String bookingID = clickedBooking.get("bookingID").toString();

                        Log.d("RecyclerPending", "Booking ID: " + bookingID); // Add this line

                        DocumentReference bookingRef = db.collection("BookingPending").document(bookingID);
                        bookingRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        bookingRef.update("status", "Confirmed")
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(view.getContext(), "Booking confirmed", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(view.getContext(), "Error updating document", Toast.LENGTH_SHORT).show();
                                                        Log.w("RecyclerPending", "Error updating document", e);
                                                    }
                                                });
                                    } else {
                                        Log.d("RecyclerPending", "No such document");
                                    }
                                } else {
                                    Log.d("RecyclerPending", "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}