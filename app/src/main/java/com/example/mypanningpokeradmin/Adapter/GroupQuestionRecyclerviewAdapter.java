package com.example.mypanningpokeradmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypanningpokeradmin.Interface.OnItemClickListener;
import com.example.mypanningpokeradmin.Model.Questions;
import com.example.mypanningpokeradmin.R;
import com.example.mypanningpokeradmin.Utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public void onBindViewHolder(@NonNull final GroupQuestionRecyclerviewAdapter.MyViewHolder holder, final int position) {
        holder.question.setText(questionsList.get(position).getQuestion());
        holder.switch_set_activity_question.setChecked(questionsList.get(position).isActive());
        if (questionsList.get(position).isActive()){
            holder.active.setText(R.string.active);
            holder.active.setTextColor(ContextCompat.getColor(context,R.color.green));
        } else {
            holder.active.setText(R.string.inactive);
            holder.active.setTextColor(ContextCompat.getColor(context,R.color.red));
        }

        /*holder.switch_set_activity_question.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                FirebaseDatabase mDatabase;
                DatabaseReference mRef;
                mDatabase = FirebaseDatabase.getInstance();
                mRef = mDatabase.getReference(Constant.QUESTIONS);

                mRef.child(questionsList.get(position).getId()).child(Constant.ACTIVE).setValue(holder.switch_set_activity_question.isChecked());
                holder.switch_set_activity_question.setChecked(holder.switch_set_activity_question.isChecked());
                questionsList.get(position).setActive(holder.switch_set_activity_question.isChecked());

                if (holder.switch_set_activity_question.isChecked()){
                    holder.active.setText(R.string.active);
                    holder.active.setTextColor(ContextCompat.getColor(context,R.color.green));
                    deleteElements(position);
                } else {
                    holder.active.setText(R.string.inactive);
                    holder.active.setTextColor(ContextCompat.getColor(context,R.color.red));
                }

                for (int i = 0; i < questionsList.size(); ++i){
                    if (!questionsList.get(i).getId().equals(questionsList.get(position).getId())){
                        mRef.child(questionsList.get(i).getId()).child(Constant.ACTIVE).setValue(false);
                        questionsList.get(i).setActive(false);
                        //listener2.updateDataSetChange(i, questionsList);
                    }
                }

            }
        });*/
    }

    private void deleteElements(final int position){
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mRefAnswers = mDatabase.getReference(Constant.ANSWER);
        mRefAnswers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ans : dataSnapshot.getChildren()){
                    if (ans.child(Constant.QUESTION_ID).getValue().toString().equals(questionsList.get(position).getId())){
                        mRefAnswers.child(ans.getKey()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView question, active;
        public Switch switch_set_activity_question;

        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);

            question = view.findViewById(R.id.textView_admin_group_question_recyclerview_question);
            switch_set_activity_question = view.findViewById(R.id.switch_set_activity_question);
            active = view.findViewById(R.id.admin_group_question_recyclerview_element_active);

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

            switch_set_activity_question.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    final int position = getAdapterPosition();

                    FirebaseDatabase mDatabase;
                    DatabaseReference mRef;
                    final DatabaseReference mRef2;
                    mDatabase = FirebaseDatabase.getInstance();
                    mRef = mDatabase.getReference(Constant.QUESTIONS);

                    mRef.child(questionsList.get(position).getId()).child(Constant.ACTIVE).setValue(switch_set_activity_question.isChecked());
                    switch_set_activity_question.setChecked(switch_set_activity_question.isChecked());
                    questionsList.get(position).setActive(switch_set_activity_question.isChecked());


                    if (switch_set_activity_question.isChecked()){
                        active.setText(R.string.active);
                        active.setTextColor(ContextCompat.getColor(context,R.color.green));
                    } else {
                        active.setText(R.string.inactive);
                        active.setTextColor(ContextCompat.getColor(context,R.color.red));
                        deleteElements(getAdapterPosition());
                    }


                    /*for (int i = 0; i < questionsList.size(); ++i){
                        if (!questionsList.get(i).getId().equals(questionsList.get(position).getId())){
                            mRef.child(questionsList.get(i).getId()).child(Constant.ACTIVE).setValue(false);
                            questionsList.get(i).setActive(false);
                        }
                    }*/

                    final List<Questions> questionsList1 = questionsList;

                    mRef2 = mDatabase.getReference(Constant.QUESTIONS);
                    /*mRef2.orderByChild(Constant.GROUP_ID).equalTo(questionsList.get(position).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String key = snapshot.child(Constant.ID).getValue().toString();
                                mRef2.child(key).child(Constant.ACTIVE).setValue(switch_set_activity_question.isChecked());
                                List<Questions> questionsList2 = questionsList;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });*/

                    mRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot q : dataSnapshot.getChildren()){
                                String group_id = q.child(Constant.GROUP_ID).getValue().toString();
                                String id = q.child(Constant.ID).getValue().toString();
                                if(group_id.equals(Constant.SELECTED_GROUP.getId()) && questionsList.get(position).getId().equals(id)){
                                    mRef2.child(id).child(Constant.ACTIVE).setValue(switch_set_activity_question.isChecked());
                                    questionsList.get(position).setActive(true);
                                } else {
                                    if (group_id.equals(Constant.SELECTED_GROUP.getId()) && !questionsList.get(position).getId().equals(id)) {
                                         mRef2.child(id).child(Constant.ACTIVE).setValue(false);


                                         for (int i = 0; i < questionsList.size(); ++i){
                                             if (questionsList.get(i).getId().equals(id)){
                                                 questionsList.get(i).setActive(false);
                                                 notifyItemChanged(i, questionsList);
                                             }
                                         }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
           });
        }
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
