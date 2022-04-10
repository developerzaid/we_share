package com.hazyaz.weshare.users.donater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.hazyaz.weshare.R;
import com.hazyaz.weshare.users.areaincharge.AIDonationData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private final ArrayList<ArrayList<String >> listdata;
    String className;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<ArrayList<String>> listdata, String clssNam, Context conte) {
        this.listdata = listdata;
        this.className = clssNam;
        this.context = conte;
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

        Log.d("23djfjsdf","Inside My lsit");
        holder.name.setText(myListData.get(3));
        holder.desc.setText(myListData.get(4));
        holder.location.setText("Current Location: "+myListData.get(6));
        Log.d("thisisurl",""+myListData.get(7)+myListData.get(5));
        Picasso.get()
                .load(myListData.get(7))
                .into(holder.imageViewxx);



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(className.equals("AIHome")){
                Intent i = new Intent(context, AIDonationData.class);
                i.putExtra("name",myListData.get(0));
                i.putExtra("city",myListData.get(1));
                i.putExtra("phoneno",myListData.get(2));
                i.putExtra("itemname",myListData.get(3));
                i.putExtra("itemdesc",myListData.get(4));
                i.putExtra("area",myListData.get(5));
                i.putExtra("currentlocation",myListData.get(6));
                i.putExtra("Image",myListData.get(7));
                i.putExtra("key",myListData.get(8));
                i.putExtra("UserKey",myListData.get(9));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
              }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewxx;
        public TextView name,desc,location;
        public CardView relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageViewxx = (ImageView) itemView.findViewById(R.id.imageViewwe);

            this.name = (TextView) itemView.findViewById(R.id.itemName);
            this.desc = (TextView) itemView.findViewById(R.id.itemDesc);
            this.location = (TextView) itemView.findViewById(R.id.lastLocation);
            relativeLayout = itemView.findViewById(R.id.relativeLayoutdf);
        }
    }
}