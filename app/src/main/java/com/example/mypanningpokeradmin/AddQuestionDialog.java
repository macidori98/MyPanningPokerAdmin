package com.example.mypanningpokeradmin;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mypanningpokeradmin.Interface.AddGroupsDialogListener;
import com.example.mypanningpokeradmin.Interface.AddQuestionsDialogListener;
import com.example.mypanningpokeradmin.Model.Groups;
import com.example.mypanningpokeradmin.Model.Questions;
import com.example.mypanningpokeradmin.Utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*this.id = id;
        this.qroup_id = qroup_id;
        this.question = question;
        this.date_from = date_from;
        this.date_until = date_until;*/

public class AddQuestionDialog extends AppCompatDialogFragment {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private AddQuestionsDialogListener listener;
    private EditText question;

    public AddQuestionDialog(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_question_dialog, null);
        question = view.findViewById(R.id.editText_add_question);
        doSomething(builder,view);
        return builder.create();
    }

    private void doSomething(AlertDialog.Builder builder, View view){
        builder.setView(view).setTitle("Add question").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatabase = FirebaseDatabase.getInstance();
                mRef = mDatabase.getReference(Constant.QUESTIONS);

                String q = question.getText().toString();
                String ID = mRef.push().getKey();
                Questions questions = new Questions(ID, Constant.SELECTED_GROUP.getId(), q, "20","30",Constant.SELECTED_GROUP.isActive());
                //Groups groups = new Groups(id, name, false, Integer.valueOf(time));
                listener.applyQuestions(questions);
                mRef.child(ID).setValue(questions);
                Toast.makeText(getContext(), R.string.question_added, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setListener(AddQuestionsDialogListener listener){
        this.listener = listener;
    }

}
