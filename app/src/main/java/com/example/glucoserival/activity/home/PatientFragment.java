package com.example.glucoserival.activity.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.model.PatientDashboard;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonParseException;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientFragment extends Fragment {


    private TextView totalAppointMentTV,nextAppointMent;
    private TextView pendingAppointment;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PatientDashboard patientDashboard;
    private AcceptedListFragment acceptedListFragment;
    private PendingListFragment pendingListFragment;
    private CompleteListFragment completeListFragment;

    public PatientFragment() {
        // Required empty public constructor
    }

    AppData appData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appData=new AppData(getContext());
        patientDashboard=new PatientDashboard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patient, container, false);
        totalAppointMentTV=view.findViewById(R.id.totalComplete);
        nextAppointMent=view.findViewById(R.id.nextAppointmentDate);
        pendingAppointment=view.findViewById(R.id.totalPending);
        getPatientDashboardData(appData.getUserID());


        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getChildFragmentManager());


        return view;
    }

    private void getPatientDashboardData(String userId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.getPatientDashboardData(userId).enqueue(new Callback<PatientDashboard>() {
            @Override
            public void onResponse(Call<PatientDashboard> call, Response<PatientDashboard> response) {

                if (response.isSuccessful()) {

                    try {
                        patientDashboard = response.body();
                        if (patientDashboard.getStatus().equals("success")) {

                            totalAppointMentTV.setText(String.valueOf(patientDashboard.getDashboardData().getTotalAppointment()));
                            nextAppointMent.setText(patientDashboard.getNextAppointmentDate());
                            pendingAppointment.setText(String.valueOf(patientDashboard.getDashboardData().getTotalPendingAppointment()));
                            appData.setUserDOB(patientDashboard.getUserInfo().getDateOfBirth());
                            acceptedListFragment=AcceptedListFragment.newInstance(patientDashboard);
                            completeListFragment=CompleteListFragment.newInstance(patientDashboard);
//                            pendingListFragment=PendingListFragment.newInstance(patientDashboard);
                            adapter.addFragment(acceptedListFragment, "Accept");
                            adapter.addFragment(completeListFragment, "Complete");
//                            adapter.addFragment(pendingListFragment, "Pending");
                            viewPager.setAdapter(adapter);
                            tabLayout.setupWithViewPager(viewPager);
                        } else {
                            Toast.makeText(getContext(), "No Data Found!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JsonParseException e) {
                        Toast.makeText(getContext(), "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<PatientDashboard> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(getContext(), "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


    public class TabAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        TabAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }



}
