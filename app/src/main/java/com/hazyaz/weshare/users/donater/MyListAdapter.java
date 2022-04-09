package com.hazyaz.weshare.users.donater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.hazyaz.weshare.R;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private final ArrayList<ArrayList<String >> listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<ArrayList<String>> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.donater_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ArrayList<String> myListData = listdata.get(position);

        holder.name.setText(myListData.get(3));
        holder.desc.setText(myListData.get(4));
//        holder.location.setText(myListData.get(7));


//        holder.imageView.setImageResource(listdata[position].getImgId());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name,desc,location;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);

            this.name = (TextView) itemView.findViewById(R.id.itemName);
            this.desc = (TextView) itemView.findViewById(R.id.itemDesc);
            this.location = (TextView) itemView.findViewById(R.id.lastLocation);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayoutdf);
        }
    }
}