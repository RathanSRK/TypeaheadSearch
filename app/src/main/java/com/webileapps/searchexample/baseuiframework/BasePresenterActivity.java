package com.webileapps.searchexample.baseuiframework;


import typeahead.IPresenter;

public abstract class BasePresenterActivity extends BaseAppCompatActivity {

    @Override
    public void onStop() {
        super.onStop();
        for(IPresenter presenter : getPresenters()) {
            if(presenter != null)
                presenter.cancelServices();
        }
    }

    protected abstract IPresenter[] getPresenters();

    @Override
    public void onDestroy() {
        dismissProgress();
        super.onDestroy();
    }
}
