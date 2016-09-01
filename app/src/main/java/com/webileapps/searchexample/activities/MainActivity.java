package com.webileapps.searchexample.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.webileapps.searchexample.R;

import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    private static final String COMPLETE = "complete";
    private static final String ERROR ="error" ;
    private static final String ON_NEXT = "on_next";
    private EditText editText;
    private ImageButton imageButton;

    private Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.editText);
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        doSearch();
    }
    public void doSearch(){
        subscription= RxTextView.textChangeEvents(editText)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(new Func1<TextViewTextChangeEvent, Boolean>() {
                    @Override
                    public Boolean call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        return isEditTextNullOrEmpty();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSearchObserver());
    }
    public boolean isEditTextNullOrEmpty(){
        if (!TextUtils.isEmpty(editText.getText().toString()) ){
            return true;
        }
        return false;
    }
    private Observer<TextViewTextChangeEvent> getSearchObserver(){
        return new Observer<TextViewTextChangeEvent>() {
            @Override
            public void onCompleted() {
                Log.d(COMPLETE,"text completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(ERROR,"error occurred");
            }

            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.d(ON_NEXT,format("Searching for %s", textViewTextChangeEvent.text().toString()));
            }
        };
    }
}
