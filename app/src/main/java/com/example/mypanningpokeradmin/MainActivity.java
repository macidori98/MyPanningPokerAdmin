package com.example.mypanningpokeradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mypanningpokeradmin.Fragment.AdminLoginFragment;
import com.example.mypanningpokeradmin.Utils.FragmentNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentNavigation.getInstance(this).replaceFragment(new AdminLoginFragment(), R.id.fragment_content);
    }
}
