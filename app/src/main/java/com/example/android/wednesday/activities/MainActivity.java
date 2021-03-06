package com.example.android.wednesday.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.android.wednesday.R;
import com.example.android.wednesday.fragments.MainAskNowFragment;
import com.example.android.wednesday.fragments.MainEventsFragment;
import com.example.android.wednesday.fragments.MainProfileFragment;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;


public class MainActivity extends AppCompatActivity implements FragNavController.RootFragmentListener, FragNavController.TransactionListener {

    Fragment fragment = null;
    private final int TAB_FIRST = FragNavController.TAB1;
    private final int TAB_SECOND = FragNavController.TAB2;
    private final int TAB_THIRD = FragNavController.TAB3;

    private FragNavController mNavController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomBar bottombar = (BottomBar) findViewById(R.id.bottomBar);
        bottombar.selectTabAtPosition(TAB_FIRST);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragment = new MainEventsFragmen        t();
//        fragmentManager.beginTransaction().add(R.id.container, fragment).commit();


        mNavController =
                new FragNavController(savedInstanceState, getSupportFragmentManager(), R.id.container,this,3, TAB_FIRST);
        mNavController.setTransactionListener(this);


        bottombar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {


                switch (tabId) {
                    case R.id.tab_events:
                        mNavController.switchTab(TAB_FIRST);
                        break;
                    case R.id.tab_ask_now:
                        mNavController.switchTab(TAB_SECOND);
                        break;
                    case R.id.tab_profile:
                        mNavController.switchTab(TAB_THIRD);
                        break;

                }


//                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, fragment).commit();


            }


        }

        );

        bottombar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                    mNavController.clearStack();

            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public Fragment getRootFragment(int i) {
        switch (i) {
            case TAB_FIRST:
                return new MainEventsFragment();
            case TAB_SECOND:
                return new MainAskNowFragment();
            case TAB_THIRD:
                return new MainProfileFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (mNavController != null) {
//            mNavController.onSaveInstanceState(outState);
//        }
//    }

    @Override
    public void onTabTransaction(Fragment fragment, int i) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }
    @Override
    public void onFragmentTransaction(Fragment fragment) {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            super.onBackPressed();
        }
    }


}
