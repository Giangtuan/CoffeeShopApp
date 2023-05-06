package com.example.foodorder.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText edtEmaildangky,edtMatkhaudk;
    Button btnDangky;
    FirebaseAuth auth;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        edtEmaildangky = findViewById(R.id.edtEmaildangky);
        edtMatkhaudk= findViewById(R.id.edtMatkhaudk);

        btnDangky =(Button) findViewById(R.id.btndangky);

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String el = edtEmaildangky.getText().toString().trim();
                String mk  = edtMatkhaudk.getText().toString().trim();

                progressDialog.show();

                if(TextUtils.isEmpty(el)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập email",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                if(TextUtils.isEmpty(mk)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập mật khẩu",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    return;
                }
                if(mk.length() < 6){
                    Toast.makeText(getApplicationContext(), "Mật khẩu quá ngắn, Tối thiểu phải 6 kí tự!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                auth.createUserWithEmailAndPassword(el,mk)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Đăng ký thất bại!" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });
            }

        });
    }
}
