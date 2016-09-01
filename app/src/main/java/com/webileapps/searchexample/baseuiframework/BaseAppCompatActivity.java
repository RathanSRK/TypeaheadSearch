package com.webileapps.searchexample.baseuiframework;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import typeahead.IActivityBaseView;
import com.webileapps.searchexample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;



public abstract class BaseAppCompatActivity extends AppCompatActivity implements IActivityBaseView {

    private ProgressDialog progressDialog;

    public abstract int getActivityLayout();

    public abstract String getToolbarTitle();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ViewStub viewStub = (ViewStub) findViewById(R.id.layout_activity);
        viewStub.setLayoutResource(getActivityLayout());
        viewStub.inflate();

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


//        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        if (isToolBarEnabled()) {
            if (TextUtils.isEmpty(getToolbarTitle()))
                getSupportActionBar().setTitle(" ");
            else
                getSupportActionBar().setTitle(getToolbarTitle());
        }
        else {
            toolbar.setVisibility(View.GONE);
        }

        if (showUpNavigation()){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (showCloseIcon()){
//            toolbar.setNavigationIcon(R.drawable.close);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected abstract boolean isToolBarEnabled();

    public  boolean showUpNavigation(){
        return false;
    }

    public boolean showCloseIcon(){
        return false;
    }

    @Override
    public void showProgressDialog(String msg) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);

        }
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public boolean isProgressVisible() {
        if (progressDialog.isShowing())
            return true;

        return false;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }


    private String[] getPermissionsToAsk(String... permissions) {

        List<String> needToAskPermissions = new ArrayList<>();

        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED))
                needToAskPermissions.add(permission);
        }

        return needToAskPermissions.toArray(new String[]{});
    }

    protected boolean requestPermissions(int requestCode, String... permissions) {

        String[] permissionsToAsk = getPermissionsToAsk(permissions);

        if (permissionsToAsk.length == 0) {
            return true;
        }

        ActivityCompat.requestPermissions(this,
                permissionsToAsk,
                requestCode);

        return false;
    }

    protected boolean hasGrantPermissions(int[] grantResults) {
        if(grantResults.length > 0) {
            for(int grantResult : grantResults) {
                if(grantResult != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
