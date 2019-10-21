package com.example.glucoserival.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.home.MainActivity;
import com.example.glucoserival.helper.SessionManager;


public class LoginActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private LoginFragment loginFragment;
    private RegistrationFragment registrationFragment;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager=new SessionManager(getApplicationContext());

        if (sessionManager.isLoggedIn()){
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        getAllWidgets();
        bindWidgetsWithAnEvent();
        setupTabLayout();

    }




    private void getAllWidgets() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }
    private void setupTabLayout() {
        loginFragment = new LoginFragment();
        registrationFragment = new RegistrationFragment();
        tabLayout.addTab(tabLayout.newTab().setText("Login"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Registration"));
    }
    private void bindWidgetsWithAnEvent()
    {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :
                replaceFragment(loginFragment);
                break;
            case 1 :
                replaceFragment(registrationFragment);
                break;
        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }




}
