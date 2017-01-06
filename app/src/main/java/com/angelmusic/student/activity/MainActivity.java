package com.angelmusic.student.activity;

import android.content.Intent;
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

    @OnClick({R.id.button, R.id.button2, R.id.button3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Toast.makeText(this, "=====测试一下OK======", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                Intent intent3 = new Intent(MainActivity.this, UpdateTestActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
