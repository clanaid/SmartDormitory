package com.bailv.smartdormitory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;

public class SlidingMenuFragment extends Fragment {

	private GridView gridView;
	private SlidingmenuGridViewAdapter adapter;

	private View view;

	private ChangeFragment listener;

	private String[] data = { "宿舍环境", "电器控制", "空调控制", "安防", "模式" };
	private int ImgID[] = { R.drawable.home_tubiao, R.drawable.chazuo_tubiao2,
			R.drawable.air_tubiao, R.drawable.safe_tubiao,
			R.drawable.set_tubiao };

	public interface ChangeFragment {
		void change(int key);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO 自动生成的方法存根
		super.onAttach(activity);
		listener = (ChangeFragment) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根

		view = inflater.inflate(R.layout.slidingmenu_gridview, null);
		gridView = (GridView) view.findViewById(R.id.slidingmen_gridview);

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onActivityCreated(savedInstanceState);
		adapter = new SlidingmenuGridViewAdapter(getActivity(), data, ImgID);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				listener.change(position);
				adapter.setSeclection(position);
				adapter.notifyDataSetChanged();
			}
		});
	}

}
