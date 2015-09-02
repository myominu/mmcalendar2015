package com.newworth.m_dictionary;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SrchFragment extends Fragment implements OnItemClickListener {

	public static final int M_TO_J_MODE = 1;
	public static final int J_TO_M_MODE = 2;
	public static final int K_TO_M_MODE = 3;
	protected SQLiteDatabase db;
	protected Typeface typefaceOriginal;
	protected MainActivity owner;
	protected ListView listView;
	protected Button button2;
	protected Button delButton;
	protected EditText editText1;
	protected TextArrayAdapter adapter;
	protected int mode;
	protected ArrayList<WordData> wordList;

	public SrchFragment(MainActivity activity) {
		this.owner = activity;
		wordList = new ArrayList<WordData>();
	}

	public int getLayoutID() {
		return R.layout.srch_view;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle saveInstanceState) {

		View view = inflater.inflate(this.getLayoutID(), null);

		db = this.owner.getDB();
		typefaceOriginal = this.owner.getFont();
		button2 = (Button) view.findViewById(R.id.button2);
		delButton = (Button) view.findViewById(R.id.del_button);
		editText1 = (EditText) view.findViewById(R.id.editText1);
		listView = (ListView) view.findViewById(R.id.srch_list_view);
		// adapter = new
		// ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
		adapter = new TextArrayAdapter(getActivity());
		listView.setAdapter(adapter);

		if (this.mode == M_TO_J_MODE) {
			adapter.setFont(typefaceOriginal);
			editText1.setTypeface(typefaceOriginal);
		}

		// ×ボタンが押されたら、Editの中を空にする
		delButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editText1.setText("");
			}
		});

		// Editがクリックされたらキーボード表示
		editText1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(editText1, 0);
			}
		});

		// Editの内容が変更されたら、検索
		editText1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 文字列が空だったら、リストを削除
				if (s.toString().equals("")) {
					adapter.clear();
					wordList.clear();
				} else {
					// それぞれのモードごとの検索
					if (mode == M_TO_J_MODE) {
						searchWord(s.toString());
					} else if (mode == J_TO_M_MODE){
						searchImi(s.toString());
					} else{
						searchKana(s.toString());
					}
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) owner
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showInputMethodPicker();
			}
		});

		listView.setOnItemClickListener(this);
		return view;
	}

	public void searchWord(String srch_word) {
		adapter.clear();
		wordList.clear();
		String sql = String
				.format("select * from m_words where word like '%s%%' or word like '-%s%%' limit 100",
						srch_word, srch_word);
		Cursor cursor = db.rawQuery(sql, new String[] {});
		try {
			int i = 0;
			while (cursor.moveToNext()) {
				WordData word = new WordData();
				word.yomi = cursor.getString(cursor.getColumnIndex("yomi"));
				word.word = cursor.getString(cursor.getColumnIndex("word"));
				word.imi = cursor.getString(cursor.getColumnIndex("imi"));
				adapter.add(word.word);
				wordList.add(word);
				i++;
			}
		} finally {
			cursor.close();
		}
	}

	public void searchImi(String srch_word) {
		adapter.clear();
		wordList.clear();
		String sql = String.format(
				"select * from m_words where imi like '%%%s%%' limit 100",
				srch_word);
		Cursor cursor = db.rawQuery(sql, new String[] {});
		try {
			int i = 0;
			while (cursor.moveToNext()) {
				WordData word = new WordData();
				word.yomi = cursor.getString(cursor.getColumnIndex("yomi"));
				word.word = cursor.getString(cursor.getColumnIndex("word"));
				word.imi = cursor.getString(cursor.getColumnIndex("imi"));
				adapter.add(word.imi);
				wordList.add(word);
				i++;
			}
		} finally {
			cursor.close();
		}
	}

	public void searchKana(String srch_word) {
		adapter.clear();
		wordList.clear();
		String sql = String.format(
				"select * from m_words where yomi like '%s%%' limit 100",
				srch_word);
		Cursor cursor = db.rawQuery(sql, new String[] {});
		try {
			int i = 0;
			while (cursor.moveToNext()) {
				WordData word = new WordData();
				word.yomi = cursor.getString(cursor.getColumnIndex("yomi"));
				word.word = cursor.getString(cursor.getColumnIndex("word"));
				word.imi = cursor.getString(cursor.getColumnIndex("imi"));
				adapter.add(word.yomi);
				wordList.add(word);
				i++;
			}
		} finally {
			cursor.close();
		}
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public class WordData {
		public String yomi;
		public String word;
		public String imi;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 自動生成されたメソッド・スタブ
		// DialogFragmentを表示します
		WordDetailFragment wordDetailFragment = new WordDetailFragment();
		wordDetailFragment.setWord(wordList.get(arg2).word,
				wordList.get(arg2).yomi, wordList.get(arg2).imi);
		wordDetailFragment.show(owner.getSupportFragmentManager(),
				WordDetailFragment.class.getSimpleName());

	}
}
