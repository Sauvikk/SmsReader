package com.example.souvik.pfmUi.cardHelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.souvik.pfmUi.R;

import java.util.ArrayList;
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

        List<ListObject> mObjects = new ArrayList<ListObject>();

        TransactionObject t1 = new TransactionObject(this);
        t1.transactionDescription = "Uber";
        t1.transactionImage = R.drawable.taxi;
        t1.transactionAmount = "xxxx5536 : Rs. 201.56";
        t1.transactionDate = "AUG 11";
        t1.setObjectId(t1.transactionDescription);

        TransactionObject t2 = new TransactionObject(this);
        t2.transactionDescription = "Imax";
        t2.transactionImage = R.drawable.movie;
        t2.transactionAmount = "xxxx7786 : Rs. 410.00";
        t2.transactionDate = "AUG 3";
        t2.setObjectId(t2.transactionDescription);

        TransactionObject t3 = new TransactionObject(this);
        t3.transactionDescription = "Dominos";
        t3.transactionImage = R.drawable.food;
        t3.transactionAmount = "xxxx5536 : Rs. 1106.46";
        t3.transactionDate = "JUL 27";
        t3.setObjectId(t3.transactionDescription);

        TransactionObject t4 = new TransactionObject(this);
        t4.transactionDescription = "Uber";
        t4.transactionImage = R.drawable.taxi;
        t4.transactionAmount = "xxxx5536 : Rs. 193.56";
        t4.transactionDate = "JUL 16";
        t4.setObjectId(t4.transactionDescription);

        TransactionObject t5 = new TransactionObject(this);
        t5.transactionDescription = "Amazon";
        t5.transactionImage = R.drawable.amazon;
        t5.transactionAmount = "xxxx7786 : Rs. 2300.55";
        t5.transactionDate = "JUL 10";
        t5.setObjectId(t5.transactionDescription);

        mObjects.add(t1);
        mObjects.add(t2);
        mObjects.add(t3);
        mObjects.add(t4);
        mObjects.add(t5);

        return mObjects;
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


    // -------------------------------------------------------------
    // Medicine Object
    // -------------------------------------------------------------

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

//            setOnItemSwipeListener(new OnItemSwipeListener() {
//                @Override
//                public void onItemSwipe(ListObject object, boolean dismissRight) {
//                    Toast.makeText(getContext(), "Swipe on " + object.getObjectId(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }

    }


}

