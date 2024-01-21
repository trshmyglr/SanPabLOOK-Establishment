package com.example.sanpablook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.sanpablook_establishment.R;

import java.util.Calendar;


public class HomeFragment extends Fragment {

    CalendarView calendarView;
    Calendar calendar;

    Button btnAccept;

    RecyclerView recyclerViewConfirmed, recyclerViewNextWeek, recyclerViewPastWeek, recyclerViewPending;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //CALENDAR VIEW
        calendarView = view.findViewById(R.id.calendarDashboard);
        calendar = Calendar.getInstance();
        calendar.getMinimalDaysInFirstWeek();
        //RECYCLER VIEW
        recyclerViewConfirmed = view.findViewById(R.id.recyclerViewConfirmed);
        recyclerViewConfirmed.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewNextWeek = view.findViewById(R.id.recyclerViewNextWeek);
        recyclerViewNextWeek.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewPastWeek = view.findViewById(R.id.recyclerViewPastWeek);
        recyclerViewPastWeek.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewPending = view.findViewById(R.id.recyclerViewPending);
        recyclerViewPending.setLayoutManager(new LinearLayoutManager(requireContext()));

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
            //Toast.makeText(HomeFragment.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });

        //BUTTON TEST TO REGISTRATION COMPLETE
//        btnAccept = view.findViewById(R.id.btnAccept);
//        btnAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goToRegistrationCompleteActivity(view);
//                goToRegistrationCompleteActivity(view);
//                Toast.makeText(getContext(), "Successful Booking", Toast.LENGTH_SHORT).show();
//            }
//
//            private void goToRegistrationCompleteActivity(View view) {
////                Intent intent = new Intent(getActivity(), RegistrationCompleteActivity.class);
////                startActivity(intent);
//            }
//        });

        return view;
    }

    public void setDate(int day, int month, int year){
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milli = calendar.getTimeInMillis();
        calendarView.setDate(milli);
    }


}