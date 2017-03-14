package moe.ggg.hacghelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by orange on 2017/3/14.
 */

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.MyViewHold> {
    private List<Item> items;
    private Context context;

    public MySearchAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }
    public void clearData(){
        items.clear();
        notifyItemRangeChanged(0,items.size());
    }
    public void addData(List<Item> datas){

        addData(0,datas);
    }

    public void addData(int position,List<Item> a_items){
        if(a_items !=null && a_items.size()>0) {
            this.items.addAll(a_items);
            notifyItemChanged(position, items.size());
        }
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_search,null);
        return new MyViewHold(view);
    }

    @Override
    public void onBindViewHolder(MyViewHold holder, int position) {
        holder.simpleDraweeView.setImageURI(items.get(position).getImage());
        holder.textView1.setText(items.get(position).getContent());
        holder.textView2.setText(items.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHold extends RecyclerView.ViewHolder{
        private SimpleDraweeView simpleDraweeView;
        private TextView textView1;
        private TextView textView2;
        public MyViewHold(View itemView) {
            super(itemView);
            textView1 = (TextView)itemView.findViewById(R.id.item_search_content);
            textView2 = (TextView)itemView.findViewById(R.id.item_search_title);
            simpleDraweeView = (SimpleDraweeView)itemView.findViewById(R.id.item_search_image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.OnItemClick(v,items.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(View v,Item item);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
