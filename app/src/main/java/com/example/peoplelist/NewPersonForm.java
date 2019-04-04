package com.example.peoplelist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewPersonForm extends AppCompatActivity {

    Button btn_cancel, btn_done;
    EditText et_name, et_age, et_picNum;

    int positionToEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person_form);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_done = findViewById(R.id.btn_done);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_picNum = findViewById(R.id.et_picNum);

        //listen for incoming data
        Bundle incomingIntent = getIntent().getExtras();

        if (incomingIntent != null) {
            String name = incomingIntent.getString("name");
            int age = incomingIntent.getInt("age");
            int picNum = incomingIntent.getInt("picNum");
            positionToEdit = incomingIntent.getInt("edit");

            //fill in the form
            et_name.setText(name);
            et_age.setText(Integer.toString(age));
            et_picNum.setText(Integer.toString(picNum));

        }

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get strings from et_ view objects
                String newName = et_name.getText().toString();
                String newAge = et_age.getText().toString();
                String newPicNum = et_picNum.getText().toString();

                //put the strings into a message for MainActivity

                //start MainActivity


                Intent i = new Intent(v.getContext(), MainActivity.class);

                i.putExtra("edit", positionToEdit);
                i.putExtra("name", newName);
                i.putExtra("age", newAge);
                i.putExtra("picNum", newPicNum);

                startActivity(i);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}
