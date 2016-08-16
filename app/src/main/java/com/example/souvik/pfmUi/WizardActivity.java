package com.example.souvik.pfmUi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.souvik.pfmUi.fragment.WizardFragment;


public class WizardActivity extends AppCompatActivity {

    private MyPagerAdapter adapter;
    private ViewPager pager;
    private TextView skipButton;
    private TextView nextButton;
    private TextView navigator;
    private int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wizard);
        currentItem = 0;

        pager = (ViewPager) findViewById(R.id.activity_wizard_pager);
        skipButton = (TextView) findViewById(R.id.activity_wizard_previous);
        nextButton = (TextView) findViewById(R.id.activity_wizard_next);
        navigator = (TextView) findViewById(R.id.activity_wizard_position);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(currentItem);

        setNavigator();

        pager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int position) {
                setNavigator();
            }
        });

        skipButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                /*if (pager.getCurrentItem() != 0) {
					pager.setCurrentItem(pager.getCurrentItem() - 1);
				}
				setNavigator();*/
                Toast.makeText(WizardActivity.this, "Skip", Toast.LENGTH_SHORT).show();
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() != (pager.getAdapter().getCount() - 1)) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                } else {
//                    Toast.makeText(WizardActivity.this, "Finish",
//                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(WizardActivity.this, MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
                setNavigator();
            }
        });

    }

    public void setNavigator() {
        String navigation = "";
        for (int i = 0; i < adapter.getCount(); i++) {
            if (i == pager.getCurrentItem()) {
                navigation += getString(R.string.material_icon_point_full)
                        + "  ";
            } else {
                navigation += getString(R.string.material_icon_point_empty)
                        + "  ";
            }
        }
        navigator.setText(navigation);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return WizardFragment.newInstance(position);
            } else if (position == 1) {
                return WizardFragment.newInstance(position);
            } else {
                return WizardFragment.newInstance(position);
            }
        }
    }
}