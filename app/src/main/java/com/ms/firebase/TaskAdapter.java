package com.ms.firebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private Context context;
    private List<TaskDataClass> dataList;

    public void setSearchList(List <TaskDataClass> dataSearchList){
        this.dataList = dataSearchList;
        notifyDataSetChanged();
    }

    public TaskAdapter(Context context, List<TaskDataClass> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.recActivityImg.setImageResource(dataList.get(position).getTaskTypeImage());
        holder.recTaskTitle.setText(dataList.get(position).getTaskTitle());
        holder.recCreatedOn.setText(dataList.get(position).getTaskCreation());
        holder.recDueOn.setText(dataList.get(position).getTaskDue());
        holder.recStatus.setText(dataList.get(position).getStatus());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: ADD
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

class TaskViewHolder extends RecyclerView.ViewHolder{

    ImageView recActivityImg;
    TextView recCreatedOn, recDueOn, recStatus, recTaskTitle, reMoreInfo;
    CardView recCard;


    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);

        recActivityImg = itemView.findViewById(R.id.colourType);

        recTaskTitle = itemView.findViewById(R.id.titleWork);

        recCreatedOn = itemView.findViewById(R.id.dateCreated);

        recDueOn = itemView.findViewById(R.id.dateOfDue);

        recStatus = itemView.findViewById(R.id.currentStatus);

        recCard = itemView.findViewById(R.id.reCard);

        reMoreInfo = itemView.findViewById(R.id.moreInfo);

    }
}