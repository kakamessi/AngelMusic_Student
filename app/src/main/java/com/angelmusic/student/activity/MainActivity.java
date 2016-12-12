package com.angelmusic.student.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.angelmusic.student.R;
import com.angelmusic.student.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setTAG() {
        TAG = "==MainActivity==";
    }

    @OnClick({R.id.button, R.id.activity_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Toast.makeText(this, "===========", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
