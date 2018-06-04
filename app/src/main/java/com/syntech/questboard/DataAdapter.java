package com.syntech.questboard;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.master.glideimageview.GlideImageView;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{

    private List<KingdomModel> kingdoms;

    public DataAdapter(List<KingdomModel> kingdoms) {
        this.kingdoms = kingdoms;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.name.setText(kingdoms.get(i).getName());
        viewHolder.glideImageView.loadImageUrl(kingdoms.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return kingdoms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private GlideImageView glideImageView;
        public ViewHolder(View view) {
            super(view);

            name = (TextView)view.findViewById(R.id.tv_name);
            glideImageView = (GlideImageView)view.findViewById(R.id.quest_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), QuestActivity.class);
                    int i = getAdapterPosition()+1;
                    intent.putExtra("id", i);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
