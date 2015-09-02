package com.newworth.m_dictionary;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WordDetailFragment extends DialogFragment {

	private  EditText editText1;
	private  TextView imiTextView;
	private TextView wordTextView;
	private TextView yomiTextView;
	private TextView srcResultTextView;
	private Button okButton;
	private String imi;
	private String word;
	private String yomi;

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog view = new Dialog(getActivity());
        view.setContentView(R.layout.word_detail_view);
        // DialogFragmentのレイアウトを縦横共に全域まで広げます
        view.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

		editText1 = (EditText) view.findViewById(R.id.editText1);
		imiTextView = (TextView) view.findViewById(R.id.imiTextView);
		wordTextView = (TextView) view.findViewById(R.id.wordTextView);
		yomiTextView = (TextView) view.findViewById(R.id.yomiTextView);
		srcResultTextView = (TextView) view.findViewById(R.id.srcResultTextView);
		okButton = (Button)view.findViewById(R.id.ok_button);

		wordTextView.setTypeface(MainActivity.typefaceOriginal);

		imiTextView.setText(imi);
		wordTextView.setText(word);
		yomiTextView.setText(yomi);

		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		return view;
	}


	public void setWord(String word, String yomi, String imi) {
		// TODO 自動生成されたメソッド・スタブ
		this.word = word;
		this.yomi = yomi;
		this.imi = imi;

	}


}
