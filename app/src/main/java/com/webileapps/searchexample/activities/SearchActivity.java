package com.webileapps.searchexample.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.webileapps.data.search.AlbumSearchResponse;
import com.webileapps.searchexample.R;
import com.webileapps.searchexample.adapters.AlbumsListAdapter;
import com.webileapps.searchexample.baseuiframework.BaseAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import typeahead.SearchPresenter;
import typeahead.ISearchPresenter;
import typeahead.ISearchView;


import static java.lang.String.format;


public class SearchActivity extends BaseAppCompatActivity implements ISearchView {

    @Bind(R.id.search_toolbar)
    Toolbar searchToolbar;

    @Bind(R.id.toolbar_search_editText)
    EditText toolbarSearchEditText;

    @Bind(R.id.recyclerView)
    RecyclerView albumsListView;

    @Bind(R.id.clear_editText_image)
    ImageView clearEditTextImage;

    @Bind(R.id.emptyDataStatusTextView)
    TextView emptyDataStatusTextView;

    @Bind(R.id.progressBar)
    View progressBar;

    private static final String COMPLETE = "complete";
    private static final String ERROR = "error";
    private static final String ON_NEXT = "on_next";

    private Subscription subscription;
    private ISearchPresenter searchPresenter;

    private AlbumsListAdapter albumsListAdapter;

    public SearchActivity() {
        searchPresenter = new SearchPresenter(this);
    }

    @OnClick(R.id.clear_editText_image)
    public void clickOnClearImage(View view) {
        toolbarSearchEditText.setText("");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(searchToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        albumsListView.setLayoutManager(new LinearLayoutManager(this));
        albumsListAdapter = new AlbumsListAdapter(this);
        albumsListView.setAdapter(albumsListAdapter);


        toolbarSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    clearEditTextImage.setVisibility(View.VISIBLE);
                } else {
                    clearEditTextImage.setVisibility(View.INVISIBLE);
                    albumsListAdapter.emptyData();
                    emptyDataStatusTextView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        doSearch();
    }

    public void doSearch() {
        subscription = RxTextView.textChangeEvents(toolbarSearchEditText)
                .debounce(600, TimeUnit.MILLISECONDS)
                .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        return isEditTextNullOrEmpty();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSearchObserver());
    }

    public boolean isEditTextNullOrEmpty() {
        return !TextUtils.isEmpty(toolbarSearchEditText.getText().toString());
    }

    private Observer<TextViewTextChangeEvent> getSearchObserver() {
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {
                Log.d(COMPLETE, "text completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(ERROR, "error occurred");
            }

            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d(ON_NEXT, format("Searching for %s", textViewTextChangeEvent.text().toString()));
                searchPresenter.doSearch(textViewTextChangeEvent.text().toString());
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
        ButterKnife.unbind(this);
    }

    @Override
    public int getActivityLayout() {
        return R.layout.search_activity;
    }

    @Override
    public String getToolbarTitle() {
        return null;
    }

    @Override
    protected boolean isToolBarEnabled() {
        return false;
    }

    @Override
    public void setData(AlbumSearchResponse response) {

        if (response.getResults().getAlbummatches().getAlbums().size() == 0) {
            emptyDataStatusTextView.setVisibility(View.VISIBLE);
            albumsListAdapter.emptyData();
        }
        else {
            emptyDataStatusTextView.setVisibility(View.GONE);
            albumsListAdapter.setData(response.getResults().getAlbummatches().getAlbums());
        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


}
