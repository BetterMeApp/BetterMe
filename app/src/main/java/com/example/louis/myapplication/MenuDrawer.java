package com.example.louis.myapplication;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class MenuDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public ActionBarDrawerToggle mToggle;

    @BindView(R.id.drawer)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavView;

    // any class that extends this abstract class must define this.
    public abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.menu_drawer);
        int id = R.layout.menu_drawer;
        setContentView(id);
        ButterKnife.bind(this);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavView.setNavigationItemSelectedListener(this);

        int layoutId = this.getLayoutId();
        RelativeLayout rl = findViewById(R.id.foo);
        View.inflate(this, layoutId, rl);
    }



    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {

        } else if (id == R.id.home) {
            Intent homeIntent = new Intent(this, HomeTaskActivity.class);
            startActivity(homeIntent);
            return true;
        } else if (id == R.id.tasks) {
            Intent detailIntent = new Intent(this, DetailActivity.class);
            startActivity(detailIntent);
            return true;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
