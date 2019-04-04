package com.example.peoplelist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    Button btn_add, btn_sortABC, btn_sortAge;

    ListView lv_friendsList;

    PersonAdapter adapter;

    MyFriends myFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_sortABC = findViewById(R.id.btn_sortABC);
        btn_sortAge = findViewById(R.id.btn_sortAge);
        lv_friendsList = findViewById(R.id.lv_listOfNames);

        myFriends = ((MyApplication) this.getApplication()).getMyFriends();

        adapter = new PersonAdapter(MainActivity.this, myFriends);

        lv_friendsList.setAdapter(adapter);

        //listen for incoming messages
        Bundle incomingMessage = getIntent().getExtras();

        if (incomingMessage != null) {
            //capture incoming data
            String name = incomingMessage.getString("name");
            int age = Integer.parseInt(incomingMessage.getString("age"));
            int picNum = Integer.parseInt(incomingMessage.getString("picNum"));
            int positionEditted = incomingMessage.getInt("edit");

            //create new person object
            Person p = new Person(name, age, picNum);

            //add person to the list and update adapter
            if (positionEditted > -1) {
                myFriends.getMyFriendsList().remove(positionEditted);
            }

            myFriends.getMyFriendsList().add(p);
            adapter.notifyDataSetChanged();
        }


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(v.getContext(), NewPersonForm.class );
                startActivity(add);
            }
        });

        btn_sortABC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(myFriends.getMyFriendsList());
                adapter.notifyDataSetChanged();
            }
        });

        btn_sortAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(myFriends.getMyFriendsList(), new Comparator<Person>() {
                    @Override
                    public int compare(Person p1, Person p2) {
                        return p1.getAge() - p2.getAge();
                    }
                });
                adapter.notifyDataSetChanged();

            }
        });

        lv_friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editPerson(position);
            }
        });

    }

    public void editPerson(int position) {
        Intent i = new Intent(getApplicationContext(), NewPersonForm.class);

        //get the contecnts of person at position
        Person p = myFriends.getMyFriendsList().get(position);

        i.putExtra("edit", position);
        i.putExtra("name", p.getName());
        i.putExtra("age", p.getAge());
        i.putExtra("picNum", p.getPictureNumber());

        startActivity(i);
    }
}
