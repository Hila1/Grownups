package com.example.shula.mygrownupsapp;
        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import java.util.ArrayList;

public class WarningHistoryAdapter extends RecyclerView.Adapter<WarningHistoryAdapter.ViewHolder> {
    private static final String TAG ="RecycleViewAdapter";

    private ArrayList<String> chosenHelps= new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>( );
    private ArrayList<String> levels = new ArrayList<>( );
    private Context context;

    public WarningHistoryAdapter(ArrayList<String> chosenHelps, ArrayList<String> dates, ArrayList<String> levels, Context context) {
        this.chosenHelps = chosenHelps;
        this.dates = dates;
        this.levels = levels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.warning_info, parent , false) ;
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        viewHolder.level.setText(levels.get(position));
        viewHolder.date.setText(dates.get(position));
        viewHolder.chosenHelp.setText(chosenHelps.get(position));

    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView level;
        TextView date;
        TextView chosenHelp;
        RelativeLayout xmlWarningHistory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            level=itemView.findViewById(R.id.txt_level);
            chosenHelp=itemView.findViewById(R.id.date);
            date = itemView.findViewById(R.id.chosen_help);
            xmlWarningHistory=itemView.findViewById(R.id.xml_warning_info);
        }
    }


}
