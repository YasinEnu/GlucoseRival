package com.example.glucoserival.activity.home;

import android.app.MediaRouteButton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.glucoserival.R;
import com.example.glucoserival.model.PatientDashboard;

import java.util.ArrayList;

/**
 * Created by Yasin Hosain on 10/28/2019.
 * yasinenubd5@gmail.com
 */
public class CompleteListFragment extends Fragment {

    public static final String COMPLETE_KEY = "complete";
    RecyclerView recyclerView;

    public static CompleteListFragment newInstance(PatientDashboard patientDashboard) {
        CompleteListFragment fragment = new CompleteListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(COMPLETE_KEY, patientDashboard);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final PatientDashboard patientDashboard=(PatientDashboard) getArguments().getSerializable(
                COMPLETE_KEY);
        View view = inflater.inflate(R.layout.fragment_pending_list, container, false);
        recyclerView=view.findViewById(R.id.appointmentListRV);
        LinearLayout linearLayout=view.findViewById(R.id.noDataFound);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new UniversalRecyclerViewAdapter(getContext(), (ArrayList) patientDashboard.getCompletedAppointment(), R.layout.pending_appointment_list_item_view, new UniversalRecyclerViewAdapter.OnRecyclerViewItemSingleViewSet() {
            @Override
            public void onItemViewSet(UniversalRecyclerViewAdapter.UniversalViewHolder universalViewHolder, Object itemData, int position) {
                ((TextView)universalViewHolder.itemView.findViewById(R.id.doctorNameTV)).setText(patientDashboard.getCompletedAppointment().get(position).getDoctorName());
                ((TextView)universalViewHolder.itemView.findViewById(R.id.dateTV)).setText(patientDashboard.getAcceptedAppointment().get(position).getDateTime());
                ((TextView)universalViewHolder.itemView.findViewById(R.id.statusTV)).setText("Add Medicine");
                ((TextView)universalViewHolder.itemView.findViewById(R.id.statusTV)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }));
        if (patientDashboard.getCompletedAppointment().size()<1){
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        return view;    }
}
