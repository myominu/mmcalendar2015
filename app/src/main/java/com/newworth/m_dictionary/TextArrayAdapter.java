package com.newworth.m_dictionary;

import android.R.anim;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TextArrayAdapter extends ArrayAdapter<String> {
	private LayoutInflater layoutInflater_;
	private Typeface typefaceOriginal = null;

	public TextArrayAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_1);
		 layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 特定の行(position)のデータを得る
		String item = getItem(position);

		// convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		if (null == convertView) {
			convertView = layoutInflater_.inflate(android.R.layout.simple_list_item_1, null);
		}

		TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
		textView.setText(item);
		if(this.typefaceOriginal!=null){
			textView.setTypeface(this.typefaceOriginal);
		}
		return convertView;
	}

	public void setFont(Typeface font){
		typefaceOriginal = font;
	}

}
