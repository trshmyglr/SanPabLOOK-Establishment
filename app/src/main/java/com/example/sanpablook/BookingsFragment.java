package com.example.sanpablook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sanpablook.Adapter.RecyclerFragmentBookings;
import com.example.sanpablook_establishment.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BookingsFragment extends Fragment {

    RecyclerView recyclerViewFragmentBookings;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        //RECYCLER VIEW
        recyclerViewFragmentBookings = view.findViewById(R.id.recyclerViewFragmentBookings);
        recyclerViewFragmentBookings.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Query Firestore
        db.collection("BookingPending")
                .whereEqualTo("establishmentID", "casaDine")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Map<String, Object>> bookings = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            bookings.add(document.getData());
                        }
                        // Set adapter
                        RecyclerFragmentBookings adapter = new RecyclerFragmentBookings(bookings);
                        recyclerViewFragmentBookings.setAdapter(adapter);
                    } else {
                        Toast.makeText(requireContext(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }
}