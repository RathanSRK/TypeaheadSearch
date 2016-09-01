
package com.webileapps.searchexample.baseuiframework;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        View view = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public abstract int getLayoutId();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected ProgressDialog progressDialog;

    public void showProgressDialog(String msg) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }

        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showToast(String msg) {
        if (isUsable()) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isProgressVisible() {
        if (progressDialog.isShowing())
            return true;

        return false;
    }


    public boolean isUsable() {
        return getActivity() != null;
    }

    protected void goToActivity(Class clazz) {
        if (clazz != null) {
            Intent intent = new Intent(getActivity(), clazz);
            startActivity(intent);
        }
    }

    protected void goToActivity(Class clazz, Bundle args) {
        if (clazz != null) {
            Intent intent = new Intent(getActivity(), clazz);
            intent.putExtras(args);
            startActivity(intent);
        }
    }

    private String[] getPermissionsToAsk(String... permissions) {

        List<String> needToAskPermissions = new ArrayList<>();

        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED))
                needToAskPermissions.add(permission);
        }

        return needToAskPermissions.toArray(new String[]{});
    }

    protected boolean requestPermissions(int requestCode, String... permissions) {

        String[] permissionsToAsk = getPermissionsToAsk(permissions);

        if (permissionsToAsk.length == 0) {
            return true;
        }

        requestPermissions(permissionsToAsk,
                requestCode);

        return false;
    }

}
