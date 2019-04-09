package com.itql.module.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

public class ViewUtil {

	public static void removeViewFromParent(View view) {
		ViewGroup viewGroup = (ViewGroup) view.getParent();
		if (viewGroup != null) {
			viewGroup.removeView(view);
		}
	}

	public static void setVisibility(View view, int visibility) {
		if (view != null) view.setVisibility(visibility);
	}

	public static void setVisible(View view, boolean visible) {
		if (view != null) view.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	public static void setText(TextView view, int res) {
		if (view != null) view.setText(res);
	}

	public static void setText(TextView view, CharSequence s) {
		if (view != null) view.setText(s);
	}

	public static void setText(TextView view, int res, CharSequence s) {
		if (view != null) {
			if (res != 0) view.setText(res);
			else view.setText(s);
		}
	}

	public static void setImg(AppCompatImageView view, int res) {
		if (view != null) view.setImageResource(res);
	}

	public static void setBackgroundColor(View view, int color) {
		if (view != null) view.setBackgroundColor(color);
	}

	public static void setBackgroundResource(View view, int res) {
		if (view != null) view.setBackgroundResource(res);
	}

	public static void setOnClickListener(View view, View.OnClickListener listener) {
		if (view != null) view.setOnClickListener(listener);
	}

}
