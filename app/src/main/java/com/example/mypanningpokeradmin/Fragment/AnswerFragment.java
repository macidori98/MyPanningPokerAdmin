package com.example.mypanningpokeradmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypanningpokeradmin.Adapter.AnswerRecyclerviewAdapter;
import com.example.mypanningpokeradmin.Model.Answer;
import com.example.mypanningpokeradmin.Model.User;
import com.example.mypanningpokeradmin.R;
import com.example.mypanningpokeradmin.Utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnswerFragment extends Fragment {
    public static final String TAG = AnswerFragment.class.getSimpleName();

    private View view;
    private RecyclerView recyclerView_answer;
    private TextView selected_question;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefAns, mRefUsers;
    private List<Answer> answerList;
    private List<User> userList;
    private LinearLayoutManager linearLayoutManager;
    private AnswerRecyclerviewAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.answers_fragment, container, false);


        selected_question = view.findViewById(R.id.textView_answers_fragment_question);
        mDatabase = FirebaseDatabase.getInstance();
        mRefAns = mDatabase.getReference(Constant.ANSWER);
        mRefUsers = mDatabase.getReference(Constant.USERS);
        answerList = new ArrayList<>();
        userList = new ArrayList<>();

        getAnswers();

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selected_question.setText(Constant.SELECTED_QUESTION.getQuestion());
    }

    private void getAnswers(){
        mRefAns.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ans:dataSnapshot.getChildren()){
                    if (ans.child(Constant.QUESTION_ID).getValue().toString().equals(Constant.SELECTED_QUESTION.getId())){
                        boolean found = false;

                        String id = ans.child(Constant.ID).getValue().toString();
                        String question_id = ans.child(Constant.QUESTION_ID).getValue().toString();
                        String user_id = ans.child(Constant.USER_ID).getValue().toString();
                        String answer = ans.child(Constant.ANSWER).getValue().toString();

                        Answer answer1 = new Answer(id, question_id, user_id, answer);

                        for (int i = 0; i < answerList.size(); ++i){
                            if (answerList.get(i).getUser_id().equals(user_id)){
                                found = true;
                                break;
                            }
                        }
                        if (!found){
                            answerList.add(answer1);
                        }
                    }
                }
                getUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUsers(){
        mRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()){
                    String u_id  = user.child(Constant.ID).getValue().toString();
                    for(int i = 0; i < answerList.size(); ++i){
                        if (answerList.get(i).getUser_id().equals(u_id)){
                            String u_name = user.child(Constant.NAME).getValue().toString();
                            User user1 = new User(u_id, u_name);

                            if (!userList.contains(user1)){
                                userList.add(user1);
                            }
                        }
                    }
                }
                recyclerView_answer = view.findViewById(R.id.recyclerview_answers_fragment);
                recyclerView_answer.setLayoutManager(linearLayoutManager);

                mAdapter = new AnswerRecyclerviewAdapter(getContext(), userList, answerList);

                recyclerView_answer.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
