package com.syntech.questboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class QuestLogAdapter extends RecyclerView.Adapter<QuestLogAdapter.ViewHolder>{

    private List<QuestModel> quests;

    public QuestLogAdapter(List<QuestModel> quests) {
        this.quests = quests;
    }

    @Override
    public QuestLogAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quest_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestLogAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.questTitle.setText(quests.get(i).getQuestName());
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView questTitle;
        public ViewHolder(View view) {
            super(view);

            questTitle = (TextView)view.findViewById(R.id.quest_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuestActivity.scrollTo(getAdapterPosition()+1);
                }
            });

        }
    }
}