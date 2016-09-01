package com.webileapps.searchexample.baseuiframework;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.webileapps.searchexample.R;


public abstract class BaseFragmentActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, getFragment()).commit();
    }

    protected abstract Fragment getFragment();

    @Override
    public int getActivityLayout() {
        return R.layout.fragment_base;
    }
}
