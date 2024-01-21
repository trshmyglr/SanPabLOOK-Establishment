package com.example.sanpablook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sanpablook_establishment.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ReportsFragment extends Fragment {

    GraphView graphView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        // on below line we are initializing our graph view.
        graphView = view.findViewById(R.id.revenueGraphView);

        // on below line we are adding data to our graph view.
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                // on below line we are adding each point on our x and y axis.
                new DataPoint(12, 2600),
                new DataPoint(13, 9500),
                new DataPoint(14, 3200),
                new DataPoint(15, 14520),
                new DataPoint(16, 4300)
        });

        graphView.addSeries(series);

        return view;
    }
}
