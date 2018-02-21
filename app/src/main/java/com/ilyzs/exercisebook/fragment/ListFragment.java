package com.ilyzs.exercisebook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangshu on 2018/1/6.
 */

public class ListFragment extends BaseFragment implements ImageDataView{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Unbinder unbinder;
    private RecyclerViewAdapter adapter;
    private static String[] data;
    private static String[] imgUrl;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    private FloatingActionMenu fabMenu;
    private ImageDataPresenter presenter;

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

        data = getResources().getStringArray(R.array.name);
        adapter = new RecyclerViewAdapter();

        presenter = new ImageDataPresenter();
        presenter.attachView(this);

        presenter.getData("",getResources().getStringArray(R.array.img));
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
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initAllMembersView(Bundle saveInstanceState) {
        unbinder = ButterKnife.bind(this, mView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabMenu = getActivity().findViewById(R.id.menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.HORIZONTAL));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showData(String[] data) {
        this.imgUrl = data;
        adapter.notifyDataSetChanged();
    }

    static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private Context context;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_main, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(data[position]);
            RequestOptions options = new RequestOptions().bitmapTransform(new CircleCrop()).placeholder(R.drawable.loading).error(R.drawable.loadfail).priority(Priority.NORMAL).diskCacheStrategy(DiskCacheStrategy.ALL);
            if (null != imgUrl && null != imgUrl[position] ) {
                Glide.with(context).load(imgUrl[position]).apply(options).into(holder.iv);
            }
        }

        @Override
        public int getItemCount() {
            return  null!=data?data.length:0;
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
