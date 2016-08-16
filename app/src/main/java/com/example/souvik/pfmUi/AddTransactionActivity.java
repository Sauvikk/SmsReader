package com.example.souvik.pfmUi;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.NumberPicker;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {


    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton floatingActionButton;

    private MaterialEditText dateEditText ;
    private MaterialEditText accountEditText ;
    private MaterialEditText paymentTypeEditText ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction);
        initialization();
        setSupportActionBar(toolbar);
        listeners();
    }

    private void listeners() {

        // floating button => back to MainActivity
        if(floatingActionButton!=null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }


        // date field => opens date picker
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AddTransactionActivity.this,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(Color.parseColor("#c0392b"));
                dpd.show(getFragmentManager(), "Calendar");
            }
        });


        // accounts field => alert dialog for account selection
        accountEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence accounts[] = new CharSequence[] {"Cash", "xxxx5536 (HDFC)", "xxxx2287 (Axis)"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTransactionActivity.this);
                builder.setTitle("Select an account");
                builder.setItems(accounts, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        accountEditText.setText(accounts[which]);
                    }
                });
                builder.show();
            }
        });



        // payment type field => Alert dialog for selecting debit/credit
        paymentTypeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence type[] = new CharSequence[] {"Debit", "Credit"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTransactionActivity.this);
                builder.setTitle("Payment Type");
                builder.setItems(type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        paymentTypeEditText.setText(type[which]);
                    }
                });
                builder.show();
            }
        });

    }

    private void initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        dateEditText = (MaterialEditText) findViewById(R.id.add_transaction_form_date);
        accountEditText = (MaterialEditText) findViewById(R.id.add_transaction_form_account);
        paymentTypeEditText = (MaterialEditText) findViewById(R.id.add_transaction_form_payment_type);

        // collapsingToolbar values
        collapsingToolbarLayout.setTitle("RICK");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255, 255, 255));

        // floating button values
        floatingActionButton.setImageResource(R.drawable.done);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.material_light_green_400)));
        floatingActionButton.setRippleColor(getResources().getColor(R.color.material_light_green_400));

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(dateEditText != null){
            dateEditText.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }
}