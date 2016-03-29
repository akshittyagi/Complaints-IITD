package com.example.akty7.assignmenttwo.HomeChildren;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.akty7.assignmenttwo.Activity_Login;
import com.example.akty7.assignmenttwo.HomeActivity.Fragment_ComplaintsList;
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_Complaint extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ViewPager viewPager;
    Bundle bundle;
    TabLayout tabLayout;
    JSONParser jp;
    Context context;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        jp = new JSONParser(this);
        context = this;
        bundle = getIntent().getExtras();
        id = bundle.getString("complaintid");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_complaint);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_complaint);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_complaint);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager_complaint);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_complaint);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

     /*   Fragment_ComplaintsList f2 = new Fragment_ComplaintsList();
        adapter.addFragment(f2, "INSTITUTE LEVEL"); */
        Fragment_ComplaintDetails fcd = new Fragment_ComplaintDetails();
        Bundle fcdbun = new Bundle();
        fcdbun.putString("complaintid", id);
        fcd.setArguments(fcdbun);
        adapter.addFragment(fcd,"DETAILS");

        Fragment_Comments fcc = new Fragment_Comments();
        fcc.setArguments(fcdbun);

        adapter.addFragment(fcc,"COMMENTS");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);

            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_complaint);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complaint, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            jp.logoutFromComplaint(Activity_Complaint.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_complaint);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoutCallBack(boolean logoutSuccess) {
        if (logoutSuccess) {
            startActivity(new Intent(Activity_Complaint.this, Activity_Login.class));
            Activity_Complaint.this.finish();
        } else {
            //  Snackbar.make(findViewById(R.id.), "Logout Failed", Snackbar.LENGTH_SHORT)
            //          .setAction("Action", null).show();
        }
    }
}
