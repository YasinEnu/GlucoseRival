package com.example.glucoserival.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.fragment.AddMedicineFragment;
import com.example.glucoserival.activity.home.AcceptedListFragment;
import com.example.glucoserival.activity.home.PatientFragment;
import com.example.glucoserival.helper.AppData;

public class AppointmentListActivity extends AppCompatActivity implements AcceptedListFragment.OnAddMedicine{

    public static final String SENT_APPOINTMENT_ID = "appointment_data";
    public static final String SENT_USER_ID = "user_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_list);

        if (new AppData(this).getUserType().equalsIgnoreCase("P")){
            replaceFragment(new PatientFragment());

        }else {
           String appointmentId=getIntent().getStringExtra(SENT_APPOINTMENT_ID);
           String userId=getIntent().getStringExtra(SENT_USER_ID);
           goToMedicineView(appointmentId,userId);
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.patientListFragmentContainer, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    @Override
    public void goToMedicineView(String appointmentId,String userId) {
        replaceFragment(AddMedicineFragment.newInstance(appointmentId,userId));
    }
}
