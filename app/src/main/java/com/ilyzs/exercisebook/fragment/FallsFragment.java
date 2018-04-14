package com.ilyzs.exercisebook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ilyzs.exercisebook.R;
import com.ilyzs.exercisebook.base.BaseFragment;
import com.ilyzs.exercisebook.base.BasePresenter;
import com.ilyzs.exercisebook.presenter.ImageDataPresenter;
import com.ilyzs.exercisebook.view.ImageDataView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FallsFragment extends BaseFragment<ImageDataPresenter> implements ImageDataView<Map<String,String>>{

    private Unbinder unbinder;
    private RecyclerViewAdapter adapter;
    private static List<Map<String,String>> list ;

    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    private int loadIndex, pullIndex,nowIndex;

    public FallsFragment() {
    }

    public static FallsFragment newInstance(String param1) {
        FallsFragment fragment = new FallsFragment();
        Bundle args = new Bundle();
        args.putString("param1",param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        adapter = new RecyclerViewAdapter();
        mPresenter.getData("0");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_falls;
    }


    @Override
    public void initAllMembersView(Bundle saveInstanceState) {
        unbinder = ButterKnife.bind(this,mView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.HORIZONTAL));

        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallZigZag);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
    }

    @Override
    public ImageDataPresenter createPresenter() {
        return new ImageDataPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowIndex = --pullIndex;
                mPresenter.getData(String.valueOf(nowIndex));
            }

            @Override
            public void onLoadMore() {
                nowIndex = ++loadIndex;
                mPresenter.getData(String.valueOf(nowIndex));
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(List<Map<String,String>> list) {
        if(null==recyclerView){
            return;
        }
        if(nowIndex>=0){
            this.list.addAll(list);
        }else{
            this.list.addAll(0,list);
        }
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
        adapter.notifyDataSetChanged();
    }

    static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

        Context context;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            this.context = parent.getContext();
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_falls,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            //图片加载应该抽出来一个工具类，这里就简写类，可参考“Business”项目中的“ImageLoadUtil.class”
            RequestOptions options = new RequestOptions().placeholder(R.drawable.loading).error(R.drawable.loadfail).priority(Priority.NORMAL).centerCrop() .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(list.get(position).get("url")).apply(options).transition(new DrawableTransitionOptions().dontTransition()).into(holder.iv);
        }

        @Override
        public int getItemCount() {
            return  list.size();
        }

         static class ViewHolder extends RecyclerView.ViewHolder{

            @BindView(R.id.iv_item_fragment_falls)
            ImageView iv;

             public ViewHolder(View itemView) {
                 super(itemView);
                 ButterKnife.bind(this,itemView);
             }
         }
    }
}
