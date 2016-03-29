package com.example.akty7.assignmenttwo.HomeActivity;


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
import com.example.akty7.assignmenttwo.JSONParser;
import com.example.akty7.assignmenttwo.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    Bundle bundle;
    TabLayout tabLayout;
    JSONParser jp;
    Context context;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        jp = new JSONParser(this);
        context = this;
        bundle = getIntent().getExtras();
        userid = bundle.getString("userid");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_home);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager_home);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_home);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Fragment_ComplaintsList f1 = new Fragment_ComplaintsList();
        Bundle fb1 = new Bundle();
        fb1.putString("level", "personal");
        f1.setArguments(fb1);
        adapter.addFragment(f1, "MY ACTIVITY");

        Fragment_ComplaintsList f2 = new Fragment_ComplaintsList();
        Bundle fb2 = new Bundle();
        fb2.putString("level", "hostel");
        f2.setArguments(fb2);
        adapter.addFragment(f2, "HOSTEL LEVEL");

        Fragment_ComplaintsList f3 = new Fragment_ComplaintsList();
        Bundle fb3 = new Bundle();
        fb3.putString("level", "insti");
        f3.setArguments(fb3);
        adapter.addFragment(f3, "INSTITUTE LEVEL");
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            jp.logoutFromHome(Activity_Home.this);
        }
        if (id == R.id.action_changepw) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Change Password");
            final EditText oldPass = new EditText(context);
            final EditText newPass = new EditText(context);
            final EditText newPassConfirm = new EditText(context);
            oldPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            newPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            newPassConfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(oldPass);
            builder.setView(newPass);
            builder.setView(newPassConfirm);
            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (newPass.getText().equals(newPassConfirm.getText())) {
                        jp.passwordReset(newPass.getText().toString(), oldPass.getText().toString());
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Snackbar.make(findViewById(R.id.drawer_layout_home), "Passwords do not match!", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoutCallBack(boolean logoutSuccess) {
        if (logoutSuccess) {
            startActivity(new Intent(Activity_Home.this, Activity_Login.class));
            Activity_Home.this.finish();
        } else {
            Snackbar.make(findViewById(R.id.drawer_layout_home), "Logout Failed", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

    public void passChangedCallBack(boolean success){
        if (success) {
            Snackbar.make(findViewById(R.id.drawer_layout_home), "Success! Logging Out", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            jp.logoutFromHome();
        } else {
            Snackbar.make(findViewById(R.id.drawer_layout_home), "Sorry! Wrong Password", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }
}
