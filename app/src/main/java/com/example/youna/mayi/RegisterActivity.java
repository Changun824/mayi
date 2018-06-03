package com.example.youna.mayi;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    Spinner spinner;
    ScrollingView ScroallView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser currentUser;
    private String testEmail,testPassword, nickname, passwordAnswer;
    public int num=0;
    public int emailCount=0;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText9;
    public String[] dataEamil;
    public String[] emailArray;
    public String[] comEamil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //mFirebaseDatabase.getInstance().getReference();
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Hints, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        final TextView textView10 = (TextView)findViewById(R.id.textView10);
        textView10.setMovementMethod(new ScrollingMovementMethod());

        TextView textView11 = (TextView)findViewById(R.id.textView11);
        textView11.setMovementMethod(new ScrollingMovementMethod());

        TextView textView12 = (TextView)findViewById(R.id.textView11);
        textView12.setMovementMethod(new ScrollingMovementMethod());

        editText3 = (EditText)findViewById(R.id.idText);
        editText3.setPrivateImeOptions("defaultInputmode=english;");

        editText4 = (EditText)findViewById(R.id.editText4);
        editText4.setFilters(new InputFilter[]{new CustomInputFilter()});
        editText4.setPrivateImeOptions("defaultInputmode=english;");

        editText5 = (EditText)findViewById(R.id.editText5);

        editText6 = (EditText)findViewById(R.id.editText6);
        editText6.setFilters(new InputFilter[]{new CustomInputFilter2()});

        editText9 = (EditText)findViewById(R.id.editText9);
        editText9.setFilters(new InputFilter[]{new CustomInputFilter2()});

        final CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        final CheckBox checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
        final CheckBox checkBox3 = (CheckBox)findViewById(R.id.checkBox3);
        final Button Button2 = (Button)findViewById(R.id.Button2);
        final Button overlapButton = (Button)findViewById(R.id.overlapButton);
        final Button button4 = (Button)findViewById(R.id.button4);

        editText4.setEnabled(false);
        editText5.setEnabled(false);
        editText6.setEnabled(false);
        editText9.setEnabled(false);

        spinner.setEnabled(false);
        checkBox.setEnabled(false);
        checkBox2.setEnabled(false);
        checkBox3.setEnabled(false);
        Button2.setEnabled(false);
        Button2.setBackgroundColor(Color.rgb(191,191,191));

        num=0;
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    editText4.setEnabled(false);
                }
                else{
                    editText4.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {

            }
        });

        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    editText5.setEnabled(false);
                }
                else{
                    editText5.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
                if(s.length() > 10){
                    s.delete(10,11);
                    Toast.makeText(getApplicationContext(), "10자리까지 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = editText4.getText().toString();
                String confirm = editText5.getText().toString();

                if(password.equals(confirm)){
                    editText4.setBackgroundColor(Color.rgb(151,199,119));
                    editText5.setBackgroundColor(Color.rgb(151,199,119));
                    editText6.setEnabled(true);
                }
                else{
                    editText4.setBackgroundColor(Color.rgb(254,138,144));
                    editText5.setBackgroundColor(Color.rgb(254,138,144));
                    editText6.setEnabled(false);
                }
            }
        });

        editText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    spinner.setEnabled(false);
                    editText9.setEnabled(false);
                }
                else{
                    spinner.setEnabled(true);
                    editText9.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
                if(s.length() > 10){
                    s.delete(10,11);
                    Toast.makeText(getApplicationContext(), "10자리까지 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editText9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    checkBox.setEnabled(false);
                }
                else{
                    checkBox.setEnabled(true);
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });

        checkBox.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    checkBox2.setEnabled(true);
                }
                else{
                    checkBox2.setEnabled(false);
                    checkBox3.setEnabled(false);
                    Button2.setBackgroundColor(Color.rgb(191,191,191));
                }
            }

        });

        checkBox2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(checkBox2.isChecked()){
                    checkBox3.setEnabled(true);
                }
                else{
                    checkBox3.setEnabled(false);
                    Button2.setBackgroundColor(Color.rgb(191,191,191));
                }
            }

        });

        checkBox3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(checkBox3.isChecked()){
                    Button2.setEnabled(true);
                    Button2.setBackgroundColor(Color.rgb(253,93,101));
                    Button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            testEmail = editText3.getText().toString();

                            testPassword = editText4.getText().toString();
                            nickname = editText6.getText().toString();
                            passwordAnswer = editText9.getText().toString();
                            createUser(testEmail, testPassword);
                        }
                    });
                }
                else{
                    Button2.setBackgroundColor(Color.rgb(191,191,191));
                }
            }

        });

        overlapButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                 if(editText3.getText().toString().length() == 0){
                     AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setMessage("아이디는 빈칸일 수 없습니다").setPositiveButton("확인",null).create();
                    dialog.show();
                }
                 else if (true==isExistemail(editText3.getText().toString())) {
                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog2.setMessage("사용 가능한 아이디입니다").setPositiveButton("확인",null).create();
                    dialog2.show();

                }

                else {
                    AlertDialog.Builder dialog3 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog3.setMessage("이미 사용 중인 아이디입니다").setPositiveButton("확인", null).create();
                    dialog3.show();
                }
            }
        });

        button4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                nickname = editText6.getText().toString();

                if(editText6.getText().toString().length() == 0){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                    dialog.setMessage("닉네임은 빈칸일 수 없습니다").setPositiveButton("확인",null).create();
                    dialog.show();
                }
                else if (true!=isExistnickname()) {
                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog2.setMessage("사용 가능한 닉네임입니다").setPositiveButton("확인",null).create();
                    dialog2.show();
                }else {
                    AlertDialog.Builder dialog3 = new AlertDialog.Builder(RegisterActivity.this);
                    dialog3.setMessage("이미 사용 중인 닉네임입니다").setPositiveButton("확인", null).create();
                    dialog3.show();
                }
            }
        });
        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            nickname = currentUser.getDisplayName();
            testEmail = currentUser.getEmail();
        }
    }


    protected class CustomInputFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start,
                                   int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");

            if(source.equals("") || ps.matcher(source).matches()){
                return source;
            }
            Toast.makeText(getApplicationContext(), "영문, 숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
            return "";

        }
    }
    //^[ㄱ-가-힣]+$
    protected class CustomInputFilter2 implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start,
                                   int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");

            if(source.equals("") || ps.matcher(source).matches()){
                return source;
            }
            Toast.makeText(getApplicationContext(), "한글만 입력해주세요", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    public void createUser(final String email, final String password){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                            mDatabase.child("User").child(nickname).child("닉네임").setValue(nickname);
                            mDatabase.child("User").child(nickname).child("이메일").setValue(testEmail);
                            mDatabase.child("User").child(nickname).child("비밀번호").setValue(testPassword);
                           // mDatabase.child(nickname).child("비밀번호 질문").setValue(spinner);
                            mDatabase.child("User").child(nickname).child("비밀번호 답변").setValue(passwordAnswer);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "회원가입이 실패되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    public boolean isExistemail(final String abc){
         mDatabase.child("User").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Log.d("[Test]", "ValueEventListener : " + snapshot.getValue());
                    String test=snapshot.getValue().toString();
                    Log.d("=============[test]==========",test);
                    emailArray=test.split(",");
                     Log.d("=============[test]==========",emailArray[2]);
                     dataEamil=emailArray[2].split("=");
                     comEamil=new String[100];
                     comEamil[0]=dataEamil[1];
                     Log.d("=============[test]==========",dataEamil[1]);

                     Log.d("=============[dd]==========",Integer.toString(num));
                     num++;
                 }
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
      //  boolean isExist = testEmail.equals((CharSequence) editText3.getText().toString());
        for(int i=0;i<num;i++) {
            if (true == comEamil[num].equals(abc))
                emailCount = 1;
        }
        Log.d("=============[count]==========",Integer.toString(emailCount));
        if(emailCount==1) {
            num=0;
            return
                    false;
        }
        else {
            num=0;
            return true;
        }
    }

    public boolean isExistnickname(){
        boolean isExist2 = nickname.equals((CharSequence) editText6.getText().toString());
        return isExist2;
    }


    public class User {

        private String username;
        private String email;
        private String pass;
        private String  passAnswer;
        public User() {

        }

        public User(String passAnswer,String pass,String username, String email) {
            this.username = username;
            this.email = email;
            this.pass=pass;
            this.passAnswer=passAnswer;
        }
        public String getUsername()
        {
            return username;
        }
        public String getEmail()
        {
            return email;
        }


    }

}
