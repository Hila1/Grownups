package com.example.shula.mygrownupsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG ="RecycleViewAdapter";

    private ArrayList<String> names= new ArrayList<>();
    private ArrayList<String> numbers= new ArrayList<>( );
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> names, ArrayList<String> numbers, Context context) {
        this.names = names;
        this.numbers = numbers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent , false) ;
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        viewHolder.neighborName.setText(names.get(position));
        viewHolder.neighborNum.setText(numbers.get(position));
        viewHolder.xml_listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String number = viewHolder.neighborNum.getText().toString();
                 dialContactPhone(number);
            }
        });
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView neighborName;
        TextView neighborNum;
        RelativeLayout xml_listitem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            neighborName=itemView.findViewById(R.id.chosen_help);
            neighborNum=itemView.findViewById(R.id.date);
            xml_listitem=itemView.findViewById(R.id.xml_listitem);
        }
    }

         private void dialContactPhone(final String phoneNumber) {

             context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
         }
}
