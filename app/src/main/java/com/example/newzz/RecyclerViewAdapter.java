package com.example.newzz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<data> items = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<data> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
      data newsdata = items.get(position);

      String imageurl = newsdata.getImageurl();
      String head = newsdata.getHeading();
      String desc = newsdata.getDescription();
        Picasso.get().load(imageurl).into(holder.imageView);
        holder.heading.setText(head);
        holder.description.setText(desc);


        holder.bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://newsinshort.000webhostapp.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView heading;
        private TextView description;
        private TextView bottom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            heading = itemView.findViewById(R.id.headtextview);
            description = itemView.findViewById(R.id.desctextview);
            bottom = itemView.findViewById(R.id.bottomtext);
        }
    }

}
