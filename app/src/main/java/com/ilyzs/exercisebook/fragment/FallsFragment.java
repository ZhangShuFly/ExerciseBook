package com.ilyzs.exercisebook.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.ilyzs.exercisebook.presenter.ImageDataPresenter;
import com.ilyzs.exercisebook.view.ImageDataView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FallsFragment extends BaseFragment implements ImageDataView{

    private Unbinder unbinder;
    private RecyclerViewAdapter adapter;
    private static String[] data;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private ImageDataPresenter presenter;

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
        if (getArguments() != null) {
            String param1 = getArguments().getString("param1");
        }
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
        return R.layout.fragment_falls;
    }

    @Override
    public void initAllMembersView(Bundle saveInstanceState) {
        unbinder = ButterKnife.bind(this,mView);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
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
    public void showData(String[] data) {
        this.data = data;
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
            Glide.with(context).load(data[position]).apply(options).transition(new DrawableTransitionOptions().dontTransition()).into(holder.iv);
        }

        @Override
        public int getItemCount() {
            return  null!=data?data.length:0;
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
