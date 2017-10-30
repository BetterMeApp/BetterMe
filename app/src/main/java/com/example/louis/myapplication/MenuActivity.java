package com.example.louis.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private ListView mDrawerList;

    @BindView(R.id.drawer) DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        ButterKnife.bind(this);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.nav_menu, menu);
//
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        mToggle.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.tasks:
//                 Intent detailIntent = new Intent(this, DetailActivity.class);
//                 startActivity(detailIntent);
//                return true;
//            case R.id.home:
//                Intent homeIntent = new Intent(this, MainActivity.class);
//                startActivity(homeIntent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
