package com.newworth.m_dictionary;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SrchFragmentTablet extends SrchFragment {

	private  EditText editText1;
	private  TextView imiTextView;
	private TextView wordTextView;
	private TextView yomiTextView;
	private TextView srcResultTextView;
	private Button okButton;
	private String imi;
	private String word;
	private String yomi;

	public SrchFragmentTablet(MainActivity activity) {
		super(activity);

	}


	@Override
	public int getLayoutID() {
		return R.layout.srch_view_tablet;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle saveInstanceState) {
		View view = super.onCreateView(inflater, container, saveInstanceState);

		editText1 = (EditText) view.findViewById(R.id.editText1);
		imiTextView = (TextView) view.findViewById(R.id.imiTextView);
		wordTextView = (TextView) view.findViewById(R.id.wordTextView);
		yomiTextView = (TextView) view.findViewById(R.id.yomiTextView);
		srcResultTextView = (TextView) view.findViewById(R.id.srcResultTextView);
		okButton = (Button)view.findViewById(R.id.ok_button);

		wordTextView.setTypeface(MainActivity.typefaceOriginal);

		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 自動生成されたメソッド・スタブ
        // DialogFragmentを表示します
        setWord(wordList.get(arg2).word,
        		wordList.get(arg2).yomi,
        		wordList.get(arg2).imi);

	}

	public void setWord(String word, String yomi, String imi) {
		// TODO 自動生成されたメソッド・スタブ
		this.word = word;
		this.yomi = yomi;
		this.imi = imi;

		imiTextView.setText(imi);
		wordTextView.setText(word);
		yomiTextView.setText(yomi);

	}

}
