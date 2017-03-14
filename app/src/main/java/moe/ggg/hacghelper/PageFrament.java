package moe.ggg.hacghelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.List;

/**
 * Created by orange on 2017/3/4.
 */

public class PageFrament extends Fragment {
    private int showstate = 2;
    private Context context;
    private RecyclerView recyclerView;
    private MaterialRefreshLayout materialRefreshLayout;
    private int mtype;
    private int page = 1;
    private int STATE = 1;
    private MyHandler myHandler;
    private MyAdapter myAdapter;
    private MySearchAdapter mySearchAdapter;
    private ProgressDialog progressDialog;
    private String key;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public static PageFrament getPageFrament(int type,String key){
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        bundle.putString("key",key);
        PageFrament pageFrament = new PageFrament();
        pageFrament.setArguments(bundle);
        return pageFrament;
    }
    public static PageFrament getPageFrament(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        PageFrament pageFrament = new PageFrament();
        pageFrament.setArguments(bundle);
        return pageFrament;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mtype = getArguments().getInt("type");
        if(getArguments().getString("key") != null){
            this.key = getArguments().getString("key");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament,container,false);
        showdialog();
        myHandler = new MyHandler();
        new Thread(new MyRunnable()).start();
        materialRefreshLayout = (MaterialRefreshLayout)view.findViewById(R.id.refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                page = ++page;
                STATE = 2;
                new Thread(new PageFrament.MyRunnable()).start();
            }

            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                STATE = 3;
                new Thread(new PageFrament.MyRunnable()).start();
            }
        });
        return view;
    }
    class MyRunnable implements Runnable{
        @Override
        public void run() {
            List<Item> t_item;
            if(key == null){
                t_item = new Items().getItems(mtype,page);
            }else {
                t_item = new Items().getItems(mtype,page,key);
            }

            Message msg = new Message();
            msg.obj = t_item;
            myHandler.sendMessage(msg);
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<Item> h_item = (List<Item>)msg.obj;
            switch (STATE){
                case 1:
                    if(key == null){
                        myAdapter = new MyAdapter(h_item,context);
                        myAdapter.addData(h_item);
                        recyclerView.setAdapter(myAdapter);
                    }else{
                        mySearchAdapter = new MySearchAdapter(h_item,context);
                        mySearchAdapter.addData(h_item);
                        recyclerView.setAdapter(mySearchAdapter);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                    break;
                case 2:
                    if(h_item != null){
                        int position = myAdapter.getItemCount()+1;
                        myAdapter.addData(h_item);
                        recyclerView.scrollToPosition(position);
                    }
                    materialRefreshLayout.finishRefreshLoadMore();
                    break;
                case 3:
                    myAdapter.clearData();
                    myAdapter.addData(h_item);
                    materialRefreshLayout.finishRefresh();
                    break;
            }
            if(key == null){
                setMyAdapter();
            }else
                setMySearchAdapter();
            progressDialog.dismiss();
        }
    }
    public void setMyAdapter(){
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, Item item) {
                Intent intent = new Intent(context,ContentActivity.class);
                intent.putExtra("url",item.getUrl());
                intent.putExtra("title",item.getTitle());
                intent.putExtra("imgurl",item.getImage());
                startActivity(intent);
            }
        });
    }
    public void setMySearchAdapter(){
        mySearchAdapter.setOnItemClickListener(new MySearchAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, Item item) {
                Intent intent = new Intent(context,ContentActivity.class);
                intent.putExtra("url",item.getUrl());
                intent.putExtra("title",item.getTitle());
                intent.putExtra("imgurl",item.getImage());
                startActivity(intent);
            }
        });
    }
    public void showdialog(){
        progressDialog = new ProgressDialog(getActivity());
        if(showstate == 1){
                progressDialog.setTitle("正在进行");
                progressDialog.setMessage("加载中...");
                progressDialog.show();
        }
    }
    public void setShowdialog(int type){
        this.showstate = type;
    }
}
