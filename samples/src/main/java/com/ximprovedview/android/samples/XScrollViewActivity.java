package com.ximprovedview.android.samples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ximprovedview.android.XScrollView;

/**
 * @author wuzhen
 * @version Version 1.0, 2016-11-01
 */
public class XScrollViewActivity extends BaseSampleActivity implements CompoundButton.OnCheckedChangeListener {

    CheckBox checkbox;
    XScrollView sv1;
    XScrollView sv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xscrollview);

        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(this);

        sv1 = (XScrollView) findViewById(R.id.sv1);
        sv2 = (XScrollView) findViewById(R.id.sv2);

        sv1.setBounceEnabled(true);
        sv2.setBounceEnabled(true);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sv1.setMaxViewHeight(600);
                sv2.setMaxViewHeight(600);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        sv1.setBounceEnabled(b);
        sv2.setBounceEnabled(b);
    }
}
