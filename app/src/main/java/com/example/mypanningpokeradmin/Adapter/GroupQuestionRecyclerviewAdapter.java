package com.example.mypanningpokeradmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypanningpokeradmin.Interface.OnItemClickListener;
import com.example.mypanningpokeradmin.Model.Questions;
import com.example.mypanningpokeradmin.R;
import com.example.mypanningpokeradmin.Utils.Constant;

import java.util.List;

public class GroupQuestionRecyclerviewAdapter extends RecyclerView.Adapter<GroupQuestionRecyclerviewAdapter.MyViewHolder> {

    private Context context;
    private List<Questions> questionsList;
    private OnItemClickListener listener;

    public GroupQuestionRecyclerviewAdapter(Context context, List<Questions> questionsList){
        this.context = context;
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public GroupQuestionRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_group_question_recyclerview_elements, parent, false);
        return new MyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupQuestionRecyclerviewAdapter.MyViewHolder holder, int position) {
        holder.question.setText(questionsList.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question;

        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            question = view.findViewById(R.id.textView_admin_group_question_recyclerview_question);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
