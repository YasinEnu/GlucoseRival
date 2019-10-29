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
import com.example.glucoserival.model.PendingAppointment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingListFragment extends Fragment {


    public static final String PENDING_KEY = "pending";
    RecyclerView recyclerView;

    public PendingListFragment() {
        // Required empty public constructor
    }

    public static PendingListFragment newInstance(PatientDashboard patientDashboard) {
        PendingListFragment fragment = new PendingListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PENDING_KEY, patientDashboard);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final PatientDashboard patientDashboard=(PatientDashboard) getArguments().getSerializable(
                PENDING_KEY);
        View view = inflater.inflate(R.layout.fragment_pending_list, container, false);
        recyclerView=view.findViewById(R.id.appointmentListRV);
        LinearLayout linearLayout=view.findViewById(R.id.noDataFound);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new UniversalRecyclerViewAdapter(getContext(), (ArrayList) patientDashboard.getPendingAppointment(), R.layout.pending_appointment_list_item_view, new UniversalRecyclerViewAdapter.OnRecyclerViewItemSingleViewSet() {
            @Override
            public void onItemViewSet(UniversalRecyclerViewAdapter.UniversalViewHolder universalViewHolder, Object itemData, int position) {
                ((TextView)universalViewHolder.itemView.findViewById(R.id.doctorNameTV)).setText(patientDashboard.getPendingAppointment().get(position).getDoctorName());
                ((TextView)universalViewHolder.itemView.findViewById(R.id.dateTV)).setText(patientDashboard.getPendingAppointment().get(position).getDateTime());
                ((TextView)universalViewHolder.itemView.findViewById(R.id.statusTV)).setText("Pending");
            }
        }));
        if (patientDashboard.getPendingAppointment().size()<1){
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        return view;
    }

}
