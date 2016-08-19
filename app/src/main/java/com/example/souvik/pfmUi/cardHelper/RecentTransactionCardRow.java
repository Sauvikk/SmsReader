package com.example.souvik.pfmUi.cardHelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.souvik.pfmUi.PFM;
import com.example.souvik.pfmUi.R;
import com.example.souvik.pfmUi.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * Created by Souvik on 3/19/2016.
 */
public class RecentTransactionCardRow extends CardWithList {


    public RecentTransactionCardRow(Context context) {
        super(context);
    }

    @Override
    protected CardHeader initCardHeader() {
       CardHeader cardHeader = new RecentTransactionCardHeader(getContext());
        return cardHeader;
    }

    @Override
    protected void initCard() {
        setSwipeable(false);
    }


    @Override
    protected List<ListObject> initChildren() {

        List<ListObject> row = new ArrayList<ListObject>();

        List<Transaction> transactionList = Transaction.getRecentTransactions(PFM.db);

        SimpleDateFormat sf = new SimpleDateFormat("MMM dd");

        for(Transaction t : transactionList){
            TransactionObject trxn = new TransactionObject(this);
            String desc = t.getTransactionDescription();
            trxn.transactionDescription = t.getTransactionDescription();
            if(desc.contains("FATTOOS") || desc.contains("CAFE")){
                trxn.transactionImage = R.drawable.food;
            }else if(desc.contains("UBER")){
                trxn.transactionImage = R.drawable.taxi;
            }else{
                trxn.transactionImage = R.drawable.payment;
            }
            if(t.getTransactionType().equals("Debit")) {
                trxn.transactionAmount = "Debit Rs. " + t.getTransactionAmount();
            }else{
                trxn.transactionAmount = "Credit Rs. " + t.getTransactionAmount();
            }
            Date date = new Date(Long.parseLong(t.getTransactionDate()));
            trxn.transactionDate = sf.format(date);
            trxn.setObjectId(t.getTransactionId()+"");
            row.add(trxn);
        }

        return row;
    }



    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {
        TextView transactionDescription = (TextView) convertView.findViewById(R.id.transaction_description);
        TextView transactionAmount = (TextView) convertView.findViewById(R.id.transaction_amount);
        TextView transactionDate = (TextView) convertView.findViewById(R.id.transaction_date);
        ImageView transactionImage = (ImageView) convertView.findViewById(R.id.transaction_image);
        TransactionObject transactionObject = (TransactionObject) object;
        transactionImage.setImageResource(transactionObject.transactionImage);
        transactionDescription.setText(transactionObject.transactionDescription);
        transactionAmount.setText(transactionObject.transactionAmount);
        transactionDate.setText(transactionObject.transactionDate);
        return convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.recent_transaction_card_row_layout;
    }

    public class TransactionObject extends DefaultListObject {

        public String transactionDescription;
        public String transactionAmount;
        public String transactionDate;
        public int transactionImage;
        public TransactionObject(Card parentCard){
            super(parentCard);
            init();
        }


        private void init(){
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
                    Toast.makeText(getContext(), "Click on " + getObjectId(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}

