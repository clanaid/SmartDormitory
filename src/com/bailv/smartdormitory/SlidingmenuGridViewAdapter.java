package com.bailv.smartdormitory;

import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlidingmenuGridViewAdapter extends BaseAdapter {

	private int[] ImgID = null;
	private String[] data = null;
	private Context context = null;
	private LayoutInflater inflater = null;

	private int clickTemp = 0;

	public SlidingmenuGridViewAdapter(Context context, String[] data,
			int[] ImgID) {
		this.ImgID = ImgID;
		this.data = data;
		this.context = context;

		this.inflater = LayoutInflater.from(context);
	}

	public void setSeclection(int position) {
		clickTemp = position;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return data.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		Holder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.gridview_item, null);
			holder = new Holder();
			holder.tv = (TextView) convertView.findViewById(R.id.item_text);
			holder.img = (ImageView) convertView.findViewById(R.id.item_icon);
			holder.layout = (LinearLayout) convertView
					.findViewById(R.id.item_background);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.tv.setText(data[position]);
		holder.img.setImageResource(ImgID[position]);

		if (position == clickTemp) {
			holder.layout
					.setBackgroundResource(R.drawable.gridview_item_select_color);
		} else {
			holder.layout.setBackgroundResource(R.drawable.gridview_item_color);
		}

		return convertView;
	}

	private class Holder {
		ImageView img = null;
		TextView tv = null;
		LinearLayout layout = null;

		public LinearLayout getLayout() {
			return layout;
		}

		public void setLayout(LinearLayout layout) {
			this.layout = layout;
		}

		public ImageView getImg() {
			return img;
		}

		public void setImg(ImageView img) {
			this.img = img;
		}

		public TextView getTv() {
			return tv;
		}

		public void setTv(TextView tv) {
			this.tv = tv;
		}

	}

}
