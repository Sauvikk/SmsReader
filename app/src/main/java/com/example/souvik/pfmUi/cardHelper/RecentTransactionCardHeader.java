package com.example.souvik.pfmUi.cardHelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.souvik.pfmUi.R;

import it.gmariotti.cardslib.library.internal.CardHeader;


public class RecentTransactionCardHeader extends CardHeader {

    public RecentTransactionCardHeader(Context context) {
        super(context, R.layout.custom_header_layout_for_recent_transaction_card);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {


    }
}