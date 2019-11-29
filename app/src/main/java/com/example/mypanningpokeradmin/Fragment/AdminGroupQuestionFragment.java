package com.example.mypanningpokeradmin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypanningpokeradmin.Adapter.GroupQuestionRecyclerviewAdapter;
import com.example.mypanningpokeradmin.Interface.AddQuestionsDialogListener;
import com.example.mypanningpokeradmin.Interface.OnItemClickListener;
import com.example.mypanningpokeradmin.Model.Questions;
import com.example.mypanningpokeradmin.R;
import com.example.mypanningpokeradmin.Utils.Constant;
import com.example.mypanningpokeradmin.Utils.FragmentNavigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class AdminGroupQuestionFragment extends Fragment implements AddQuestionsDialogListener {

    public static final String TAG = AdminGroupQuestionFragment.class.getSimpleName();

    private View view;
    private RecyclerView recyclerView_admin_group_questions;
    private List<Questions> questionsList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FloatingActionButton fab_add_questions;
    private GroupQuestionRecyclerviewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_group_questions, container, false);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.QUESTIONS);

        recyclerView_admin_group_questions = view.findViewById(R.id.admin_group_questions_recyclerview);
        fab_add_questions = view.findViewById(R.id.floatingActionButton_add_questions);
        questionsList = new ArrayList<>();

        getQuestions();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(VERTICAL);
        recyclerView_admin_group_questions.setLayoutManager(linearLayoutManager);
        fab_add_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();

            }
        });
    }

    private void addQuestion(){
        AddQuestionDialog addQuestionDialog = new AddQuestionDialog();
        addQuestionDialog.setListener(this);
        addQuestionDialog.show(getActivity().getSupportFragmentManager(),"Add new question");
    }


    private void getQuestions(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot q: dataSnapshot.getChildren()){
                    String groups_id = q.child(Constant.GROUP_ID).getValue().toString();

                    if (groups_id.equals(Constant.SELECTED_GROUP.getId())) {

                        String id = q.child(Constant.ID).getValue().toString();
                        String question = q.child(Constant.QUESTION).getValue().toString();
                        int  active_time_limit = Integer.valueOf(q.child(Constant.ACTIVE_TIME_SECONDS).getValue().toString());
                        boolean active = Boolean.valueOf(q.child(Constant.ACTIVE).getValue().toString());

                        Questions questions = new Questions(id, groups_id, question, active, active_time_limit);
                        questionsList.add(questions);
                    }
                }

                mAdapter = new GroupQuestionRecyclerviewAdapter(getContext(),questionsList);
                mAdapter.setOnClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Constant.SELECTED_QUESTION = questionsList.get(position);
                        FragmentNavigation.getInstance(getContext()).replaceFragment(new AnswerFragment(), R.id.fragment_content);
                    }
                });

                recyclerView_admin_group_questions.setAdapter(mAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void applyQuestions(Questions questions) {
        questionsList.add(questions);
        mAdapter.notifyItemInserted(questionsList.size());
    }
}
