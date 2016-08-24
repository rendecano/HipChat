package com.rendecano.hipchat.presentation.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rendecano.hipchat.R;
import com.rendecano.hipchat.presentation.AndroidApplication;
import com.rendecano.hipchat.presentation.internal.di.ApplicationComponent;
import com.rendecano.hipchat.presentation.presenter.MainPresenter;
import com.rendecano.hipchat.presentation.presenter.view.MainView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    MainPresenter presenter;

    private EditText editTextString;
    private TextView txtConvertedString;
    private FloatingActionButton fab;
    private ProgressDialog progress;

    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize component
        getApplicationComponent().inject(this);
        presenter.attachView(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        txtConvertedString = (TextView) findViewById(R.id.txtConvertedString);
        editTextString = (EditText) findViewById(R.id.etString);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.convertString(editTextString.getText().toString());
            }
        });

        txtConvertedString.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showConvertedString(String convertedString) {
        txtConvertedString.setText(convertedString);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progress = ProgressDialog.show(this, "",
                "Converting...", true);
    }

    @Override
    public void hideLoading() {
        if (progress != null) {
            progress.dismiss();
        }
    }
}
