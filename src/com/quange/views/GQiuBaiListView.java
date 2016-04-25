package com.quange.views;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.quange.girls.R;
import com.quange.model.GQiuBaiModel;
import com.quange.viewModel.GAPIManager;
import com.quange.viewModel.GQiuBaiAdapter;

import android.app.Activity;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class GQiuBaiListView implements OnItemClickListener{
	private View mView;
	private Activity mAct;
	private PullToRefreshListView lList;
	private GQiuBaiAdapter lAdapter;
	private ArrayList<GQiuBaiModel> mLSList = new ArrayList<GQiuBaiModel>();
	private ArrayList<String> mListIds = new ArrayList<String>();
	private RelativeLayout rlW;
	private TextView tvCW;
	private String appPath;
	private int qiubaiType;
	private int mCurPage = 1;
	
	public GQiuBaiListView(Activity act, int type) {
		super();
		this.mAct = act;
		this.qiubaiType = type;
		mView = View.inflate(mAct, R.layout.view_qiubai, null);
		appPath = mAct.getApplicationContext().getFilesDir().getAbsolutePath();
		initView();
	}

	/**
	 * 得到视图
	 * 
	 * @return
	 */
	public View getView() {
		return mView;
	}

	// 初始化设置
	private void initView() {
		lList = (PullToRefreshListView) mView.findViewById(R.id.qiubai_list);
		
		lAdapter = new GQiuBaiAdapter(mAct, mLSList);
		lList.setMode(Mode.BOTH);
		lList.setAdapter(lAdapter);
		lList.setOnRefreshListener(orfListener2());
	
		lList.setOnItemClickListener(this);
		lList.setOnScrollListener(new OnScrollListener() {
			boolean isLastRow = false;

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// 滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
				// firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
				// visibleItemCount：当前能看见的列表项个数（小半个也算）
				// totalItemCount：列表项共数

				// 判断是否滚到最后一行
				if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
					isLastRow = true;
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
				// 回调顺序如下
				// 第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
				// 第2次：scrollState = SCROLL_STATE_FLING(2)
				// 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
				// 第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
				// 当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；
				// 由于用户的操作，屏幕产生惯性滑动时为2

				// 当滚到最后一行且停止滚动时，执行加载
				if (isLastRow && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
					
					refresh(false);
					isLastRow = false;
				}
			}
		});
	
		
	}

	public void firstLoadData()
	{
		if(mLSList.size()==0)
		{
			lList.post(new Runnable() {
			      @Override public void run() {
			    	  lList.setRefreshing(true);
			      }
			 });
		}
	}
	
	public ArrayList<GQiuBaiModel> removeDuplicate(List<GQiuBaiModel> mLSList2){
		   return new ArrayList<GQiuBaiModel>(new HashSet<GQiuBaiModel>(mLSList2));
		}
	
	public void refresh(final boolean isRefresh) {
			mCurPage = isRefresh ? 1 : ++mCurPage ;
			GAPIManager.getInstance(mAct).fetchQiuBai(mCurPage, qiubaiType, new Listener<List<GQiuBaiModel>>(){
				@Override
				public void onResponse(List<GQiuBaiModel> response) {
					if(isRefresh)
					{
						mLSList.clear();
						mListIds.clear();
					}
						
					for(GQiuBaiModel model :response)
					{
						if(mListIds.indexOf(model.id+"") != -1)
						{
							System.out.println("已经存在，去重");
						}
						else
						{
							mListIds.add(model.id+"");
							mLSList.add(model);
							
						}
					}
					//mLSList.addAll(response);
					
					
					lAdapter.notifyDataSetChanged();
					
				}
				
				
			} ,  new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					System.out.println(error);
					--mCurPage;
				}
			});
				
	}


	private OnRefreshListener2<ListView> orfListener2() {
		return new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				
				refresh(true);
				stoprefresh(refreshView);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				
				refresh(false);
				stoprefresh(refreshView);
			}
		};
	}

	protected void stoprefresh(final PullToRefreshBase<ListView> refreshView) {
		refreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				refreshView.onRefreshComplete();
			}
		}, 2000);
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}


}
