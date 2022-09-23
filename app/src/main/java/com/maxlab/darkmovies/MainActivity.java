package com.maxlab.darkmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iammert.library.readablebottombar.ReadableBottomBar;
import com.maxlab.darkmovies.databinding.ActivityMainBinding;
import com.maxlab.darkmovies.fragments.FavoriteFragment;
import com.maxlab.darkmovies.fragments.SeriesFragment;
import com.maxlab.darkmovies.fragments.HomeFragment;
import com.maxlab.darkmovies.fragments.MovieFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());

        auth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()){
                      FirebaseUser user = auth.getCurrentUser();
                  }
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new HomeFragment());
        transaction.commit();
        binding.readableBottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                        transaction.replace(R.id.content, new HomeFragment());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.content, new MovieFragment());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.content, new SeriesFragment());
                        transaction.commit();
                        break;
                    case 3:
                        transaction.replace(R.id.content, new FavoriteFragment());
                        transaction.commit();
                        break;
                }

            }
        });
    }
}