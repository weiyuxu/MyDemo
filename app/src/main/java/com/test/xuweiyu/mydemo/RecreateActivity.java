package com.test.xuweiyu.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by xuweiyu on 19-2-14.
 * Email:xuweiyu@xiaomi.com
 */
public class RecreateActivity extends Activity implements View.OnClickListener {
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recreate);
        mButton = findViewById(R.id.recreate_activity_btn);
        mButton.setOnClickListener(this);
    }

    private void recreateActivity() {
        setResult(RESULT_OK);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recreate_activity_btn:
                recreateActivity();
                finish();
                break;
        }
    }
}
