package com.example.souvik.pfmUi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.souvik.pfmUi.R;


public class WizardFragment extends Fragment {

	private static final String ARG_POSITION = "position";

	private int position;
	private LinearLayout layout;
	private TextView icon;

	public static WizardFragment newInstance(int position) {
		WizardFragment f = new WizardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_wizard, container, false);
		layout = (LinearLayout) rootView.findViewById(R.id.fragment_wizard_layout);
		icon = (TextView) rootView.findViewById(R.id.fragment_wizard_icon);
		if (position == 0) {
			layout.setBackgroundColor(getResources().getColor(R.color.material_purple_300));
			layout.invalidate();
			icon.setText(R.string.material_icon_cloud_univ_first);
		} else if (position == 1) {
			layout.setBackgroundColor(getResources().getColor(R.color.material_purple_700));
			layout.invalidate();
			icon.setText(R.string.material_icon_cloud_univ_second);
		} else {
			layout.setBackgroundColor(getResources().getColor(R.color.material_purple_900));
			layout.invalidate();
			icon.setText(R.string.material_icon_cloud_univ_third);
		}
		ViewCompat.setElevation(rootView, 50);
		return rootView;
	}
}