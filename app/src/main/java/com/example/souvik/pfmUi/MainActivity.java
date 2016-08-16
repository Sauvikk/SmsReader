package com.example.souvik.pfmUi;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.souvik.pfmUi.cardHelper.RecentTransactionCardRow;

import it.gmariotti.cardslib.library.view.CardViewNative;

public class MainActivity extends AppCompatActivity {

    private RecentTransactionCardRow medicineIndividualCard;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CardViewNative medicineIndividualCardView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        setSupportActionBar(toolbar);
        initTransactionCard();
        listeners();
    }

    private void listeners() {

        // floating button lister => AddTransactionActivity
        if(floatingActionButton!=null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(MainActivity.this, floatingActionButton, "shared_fab");
                        startActivity(intent, options.toBundle());
                    }
                    else {
                        startActivity(intent);
                    }
                }
            });
        }

    }

    private void initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating_action_button);

        //  collapsingToolbar values
        collapsingToolbarLayout.setTitle("RICK");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));

        // floatingActionButton values
        floatingActionButton.setImageResource(R.drawable.add);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.material_light_green_400)));
        floatingActionButton.setRippleColor(getResources().getColor(R.color.material_light_green_400));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            Log.d("ACTION", "SPLASH");
//            Intent intent = new Intent(this, SplashScreensActivity.class);
//            intent.putExtra(SplashScreensActivity.SPLASH_SCREEN_OPTION, SplashScreensActivity.SPLASH_SCREEN_OPTION_3);
//            startActivity(intent);
            return true;
        }
        if (id == R.id.action_settings1) {
//            Log.d("ACTION", "WIZARD");
//            Intent intent = new Intent(this, WizardActivity.class);
//            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initTransactionCard() {
        medicineIndividualCard = new RecentTransactionCardRow(this);
        medicineIndividualCard.init();
        medicineIndividualCardView = (CardViewNative)findViewById(R.id.medicine_all_card);
        medicineIndividualCardView.setCard(medicineIndividualCard);
    }



}
