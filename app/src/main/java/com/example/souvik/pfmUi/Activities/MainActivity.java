package com.example.souvik.pfmUi.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.souvik.pfmUi.PFM;
import com.example.souvik.pfmUi.R;
import com.example.souvik.pfmUi.cardHelper.RecentTransactionCardRow;
import com.example.souvik.pfmUi.font.RobotoTextView;
import com.example.souvik.pfmUi.model.Account;
import com.example.souvik.pfmUi.model.Transaction;

import java.util.StringTokenizer;

import it.gmariotti.cardslib.library.view.CardViewNative;

public class MainActivity extends AppCompatActivity {

    private RecentTransactionCardRow medicineIndividualCard;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CardViewNative medicineIndividualCardView;
    private FloatingActionButton floatingActionButton;
    private RobotoTextView totalBalance;
    private ProgressDialog pDialog;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(checkPermission()){
            fetchSms();
        }else{
            requestPermission();
        }
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
                    } else {
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
        totalBalance = (RobotoTextView)findViewById(R.id.activity_main_balance_card_total_balance);
        totalBalance.setText("Rs. " +Account.getAccountTotalBalance(PFM.db));

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
        if (id == R.id.fetch_sms) {
//            fetchSms();
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

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)) {
            Toast.makeText(MainActivity.this, "Sms permission required. Please allow in PFM Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchSms();
                } else {
                    Toast.makeText(MainActivity.this, "Permission not granted.", Toast.LENGTH_LONG).show();
//                    TODO : set proper layout when permission is not granted
                }
                break;
        }
    }

    public void fetchSms() {
        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"}, null, null, null);
        new ProcessSms().execute(cursor);
    }


    class ProcessSms extends AsyncTask<Cursor, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Processing Sms");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Cursor... params) {
            Cursor cursor = params[0];
            while  (cursor.moveToNext())
            {
                int i = -1;
                String from = cursor.getString(1).toLowerCase();
                String date = cursor.getString(2).toLowerCase();
                String body = cursor.getString(3).toLowerCase();
                if(from.contains("im-yesbnk")){
                    if(body.startsWith("rs")) {
                        String amount = null;
                        String type = null;
                        String number = null;
                        StringTokenizer tokens = new StringTokenizer(body);
                        String totAvi = body.substring(body.indexOf("tot avbl bal-"));
                        String desc = body.substring(body.indexOf("pcd")+"pcd".length(), body.indexOf("tot avbl bal-"));
                        desc = desc.substring(desc.lastIndexOf(":")).toUpperCase();
                        Account account  = new Account();
                        Transaction transaction = new Transaction();
                        desc = desc.replace(":", "").trim();
                        totAvi = totAvi.substring(totAvi.indexOf("tot avbl bal-") + "tot avbl bal-".length(), totAvi.indexOf("on"));
                        totAvi = totAvi.replace("rs","").trim();
                        totAvi = totAvi.replace(",", "");
                        Log.d("TOT AVA : " , totAvi);
                        Log.d("DESC : ", desc);
                        account.setAccountBalance(Float.parseFloat(totAvi));
                        transaction.setTransactionDescription(desc);
                        transaction.setTransactionDate(date);
                        while (tokens.hasMoreTokens()) {
                            i++;
                            if (i == 0) {
                                amount = tokens.nextToken();
                                amount = amount.replace("rs","").trim();
                                transaction.setTransactionAmount(amount);
                                Log.d("AMOUNT", amount);
                            } else if (i == 1) {
                                type = tokens.nextToken();
                                if(type.contains("debit")){
                                    type =  "Debit";
                                }else{
                                    type = "Credit";
                                }
                                transaction.setTransactionType(type);
                                Log.d("TYPE", type);
                            } else if (i == 4) {
                                number = tokens.nextToken();
                                account.setAccountNumber(number);
                                Log.d("ACC", tokens.nextToken());
                            } else if(i>4){
                                break;
                            } else {
                                tokens.nextToken();
                            }
                        }

                        long id = account.save(PFM.db);
                        Log.d("ID ID ID : ", id+"" );
                        transaction.setBankNumber(number);
                        transaction.save(PFM.db);
                    }
                }
            }
//            Account.count(PFM.db);
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            pDialog.dismiss();
            initialization();
            setSupportActionBar(toolbar);
            initTransactionCard();
            listeners();
//            finish();
//            startActivity(getIntent());
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }



}
