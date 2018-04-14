package com.ilyzs.exercisebook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.github.clans.fab.FloatingActionMenu;
import com.ilyzs.exercisebook.R;
import com.ilyzs.exercisebook.base.BaseFragment;
import com.ilyzs.exercisebook.presenter.ImageDataPresenter;
import com.ilyzs.exercisebook.view.ImageDataView;
import com.jcodecraeer.xrecyclerview.ItemTouchHelperAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.SimpleItemTouchHelperCallback;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangshu on 2018/1/6.
 */

public class ListFragment extends BaseFragment<ImageDataPresenter> implements ImageDataView<Map<String,String>>{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Unbinder unbinder;
    
    private RecyclerViewAdapter adapter;
    private static List<Map<String,String>> list;

    @BindView(R.id.recycler_view)
    public XRecyclerView recyclerView;
    private FloatingActionMenu fabMenu;

    private int loadIndex, pullIndex,nowIndex;

    public ListFragment() {

    }

    public static ListFragment newInstance(int sectionNumber) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        return R.layout.fragment_main;
    }

    @Override
    public void initAllMembersView(Bundle saveInstanceState) {
        unbinder = ButterKnife.bind(this, mView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.HORIZONTAL));

        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter,recyclerView);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        //View header =   LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_header, (ViewGroup)(getActivity().findViewById(android.R.id.content)),false);
        //recyclerView.addHeaderView(header);
    }

    @Override
    public ImageDataPresenter createPresenter() {
        return  new ImageDataPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabMenu = getActivity().findViewById(R.id.menu);
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

/*        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fabMenu.showMenu(true);
                } else {
                    fabMenu.hideMenu(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });*/
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(recyclerView!=null){
            recyclerView.destroy();
            recyclerView = null;
        }
    }

    static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter {
        private Context context;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_main, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(list.get(position).get("name"));
            RequestOptions options = new RequestOptions().bitmapTransform(new CircleCrop()).placeholder(R.drawable.loading).error(R.drawable.loadfail).priority(Priority.NORMAL).diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(list.get(position).get("url")).apply(options).into(holder.iv);
        }

        @Override
        public int getItemCount() {
            return  null!=list?list.size():0;
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
                Collections.swap(list,fromPosition,toPosition);
                notifyItemMoved(fromPosition,toPosition);
        }

        @Override
        public void onItemDismiss(int position) {
            list.remove(position-1);
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.item_name)
            TextView tv;

            @BindView(R.id.iv_item_fragment_main)
            ImageView iv;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
