package typeahead;

import android.content.Context;


public interface IActivityBaseView {
    void showProgressDialog(String msg);
    void dismissProgress();

    boolean isProgressVisible();

    void showToast(String msg);
    Context getContext();
}
