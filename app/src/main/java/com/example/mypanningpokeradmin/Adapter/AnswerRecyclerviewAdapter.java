package com.example.mypanningpokeradmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypanningpokeradmin.Model.Answer;
import com.example.mypanningpokeradmin.Model.User;
import com.example.mypanningpokeradmin.R;

import java.util.List;

public class AnswerRecyclerviewAdapter extends RecyclerView.Adapter<AnswerRecyclerviewAdapter.MyViewHolder> {

    private Context context;
    private List<User> userList;
    private List<Answer> answerList;


    public AnswerRecyclerviewAdapter(Context context, List<User> userList, List<Answer> answerList){
        this.answerList = answerList;
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public AnswerRecyclerviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_recyclerview_elements, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerRecyclerviewAdapter.MyViewHolder holder, int position) {
        holder.answer.setText(answerList.get(position).getAnswer());
        for (int i = 0; i < userList.size(); ++i) {
            if (answerList.get(position).getUser_id().equals(userList.get(i).getId())) {
                holder.name.setText(userList.get(i).getName());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, answer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_answer_recyclerview_name);
            answer = itemView.findViewById(R.id.textView_answer_recyclerview_answer);
        }
    }
}
