package com.claire.mychatappusingfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final int SING_IN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //處理用戶登錄
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            // Start sign in / sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .build(),
                    SING_IN_REQUEST_CODE);
        } else {
            // User is already signed in. Therefore(因此), display a welcome Toast
            Toast.makeText(this,
                    "Welcome" + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_SHORT).show();

            // Load chat room contents
            displayChatMessage();
        }
    }

    private void displayChatMessage() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SING_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK){ //則表示用戶已成功登錄
                Toast.makeText(this, "Successfully signed in. Welcome!",
                        Toast.LENGTH_SHORT).show();
                displayChatMessage();
            } else {
                Toast.makeText(this, "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_SHORT).show();
                // Close the app
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sign_out:
                AuthUI.getInstance().signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainActivity.this, "You have been signed out.",
                                        Toast.LENGTH_SHORT).show();
                                //Close activity
                                finish();
                            }
                        });
                break;
        }
        return true;
    }
}
