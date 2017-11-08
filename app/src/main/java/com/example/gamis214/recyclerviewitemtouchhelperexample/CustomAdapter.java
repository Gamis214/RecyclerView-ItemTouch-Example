package com.example.gamis214.recyclerviewitemtouchhelperexample;

import android.content.Context;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gamis214.recyclerviewitemtouchhelperexample.Model.ServicesResponse;

import java.util.List;

/**
 * Created by gamis214 on 30/10/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ServicesResponse> list;
    private Context context;

    public CustomAdapter(Context context, List<ServicesResponse> list){
        this.list = list;
        this.context = context;
    }

    public static class itemHolder extends RecyclerView.ViewHolder{
        public TextView txtTitulo,txtContent,txtPrecio;
        public ImageView imageView;
        public ConstraintLayout viewBackGround,viewForeGround;

        public itemHolder(View view){
            super(view);
            txtTitulo   =  view.findViewById(R.id.txtTitulo);
            txtContent  =  view.findViewById(R.id.txtContent);
            txtPrecio   =  view.findViewById(R.id.txtPrecio);
            imageView   =  view.findViewById(R.id.imageView);
            viewBackGround = view.findViewById(R.id.viewBackGround);
            viewForeGround = view.findViewById(R.id.viewForeGround);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);
        return new itemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        itemHolder itemHolder = (itemHolder) holder;

        itemHolder.txtTitulo.setText(list.get(position).getName());
        itemHolder.txtPrecio.setText("$ " + list.get(position).getPrice());
        itemHolder.txtContent.setText(list.get(position).getDescription());

        Glide.with(context)
                .load(Uri.parse(list.get(position).getThumbnail()))
                .into(itemHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ServicesResponse item, int position){
        list.add(position,item);
        notifyItemInserted(position);
    }
}
