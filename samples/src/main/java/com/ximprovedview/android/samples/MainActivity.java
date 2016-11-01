package com.ximprovedview.android.samples;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layRoot = (LinearLayout) findViewById(R.id.lay_root);

        addButtons();
    }

    private void addButtons() {
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            ActivityInfo[] infos = pi.activities;
            for (ActivityInfo ai : infos) {
                String name = ai.name;
                if (name.startsWith(getPackageName()) && !name.equals(getClass().getName())) {
                    addButtonView(ai);
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void addButtonView(ActivityInfo ai) {
        Button btn = new Button(this);
        btn.setText(ai.nonLocalizedLabel);
        btn.setGravity(Gravity.CENTER);
        btn.setAllCaps(false);
        btn.setTag(ai.name);
        btn.setOnClickListener(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelOffset(R.dimen.button_height));
        lp.topMargin = getResources().getDimensionPixelOffset(R.dimen.button_margin_top);
        layRoot.addView(btn, lp);
    }

    @Override
    public void onClick(View v) {
        String className = v.getTag().toString();

        try {
            Class<?> clazz = Class.forName(className);
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
