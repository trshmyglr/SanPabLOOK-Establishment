package com.example.sanpablook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanpablook_establishment.R;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ReportsFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView valueTotalBookings, valuePendingBookings, valueCancelledBookings, valueConfirmedBookings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        valueTotalBookings = view.findViewById(R.id.valueTotalBookings);

        // Query Firestore
        db.collection("BookingPending")
                .whereEqualTo("establishmentID", "casaDine")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            int totalBookings = querySnapshot.size();
                            valueTotalBookings.setText(String.valueOf(totalBookings));
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

        valuePendingBookings = view.findViewById(R.id.valuePendingBookings);

        // Query Firestore
        db.collection("BookingPending")
                .whereEqualTo("establishmentID", "casaDine")
                .whereEqualTo("status", "Pending")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            int pendingBookings = querySnapshot.size();
                            valuePendingBookings.setText(String.valueOf(pendingBookings));
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

        valueCancelledBookings = view.findViewById(R.id.valueCancelledBookings);

        // Query Firestore
        db.collection("BookingPending")
                .whereEqualTo("establishmentID", "casaDine")
                .whereEqualTo("status", "Cancelled")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            int cancelledBookings = querySnapshot.size();
                            valueCancelledBookings.setText(String.valueOf(cancelledBookings));
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

        valueConfirmedBookings = view.findViewById(R.id.valueConfirmedBookings);

        // Query Firestore
        db.collection("BookingPending")
                .whereEqualTo("establishmentID", "casaDine")
                .whereEqualTo("status", "Confirmed")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            int confirmedBookings = querySnapshot.size();
                            valueConfirmedBookings.setText(String.valueOf(confirmedBookings));
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }
}
