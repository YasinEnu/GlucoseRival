package com.example.glucoserival.activity.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.AppointmentListActivity;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.model.PatientDashboard;

import java.util.ArrayList;

/**
 * Created by Yasin Hosain on 10/28/2019.
 * yasinenubd5@gmail.com
 */
public class AcceptedListFragment extends Fragment {


    public static final String ACCEPT_KEY = "accept";
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    Activity parentActivity;


    public static AcceptedListFragment newInstance(PatientDashboard patientDashboard) {
        AcceptedListFragment fragment = new AcceptedListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ACCEPT_KEY, patientDashboard);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final PatientDashboard patientDashboard=(PatientDashboard) getArguments().getSerializable(
                ACCEPT_KEY);
        View view = inflater.inflate(R.layout.fragment_pending_list, container, false);
        recyclerView=view.findViewById(R.id.appointmentListRV);
        linearLayout=view.findViewById(R.id.noDataFound);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        parentActivity=getActivity();
        recyclerView.setAdapter(new UniversalRecyclerViewAdapter(getContext(), (ArrayList) patientDashboard.getAcceptedAppointment(), R.layout.pending_appointment_list_item_view, new UniversalRecyclerViewAdapter.OnRecyclerViewItemSingleViewSet() {
            @Override
            public void onItemViewSet(UniversalRecyclerViewAdapter.UniversalViewHolder universalViewHolder, Object itemData, final int position) {
                ((TextView)universalViewHolder.itemView.findViewById(R.id.doctorNameTV)).setText(patientDashboard.getAcceptedAppointment().get(position).getDoctorName());
                ((TextView)universalViewHolder.itemView.findViewById(R.id.dateTV)).setText(patientDashboard.getAcceptedAppointment().get(position).getDateTime());
                ((TextView)universalViewHolder.itemView.findViewById(R.id.statusTV)).setText("Add Medicine");
                ((TextView)universalViewHolder.itemView.findViewById(R.id.statusTV)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                        AppointmentListActivity appointmentListActivity= (AppointmentListActivity) parentActivity;
                        appointmentListActivity.goToMedicineView(patientDashboard.getAcceptedAppointment().get(position).getId(),new AppData(getContext()).getUserID());
                    }
                });
            }
        }));
        if (patientDashboard.getAcceptedAppointment().size()<1){
            recyclerView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public interface OnAddMedicine{

        void goToMedicineView(String appointmentId,String userId);

    }
}
