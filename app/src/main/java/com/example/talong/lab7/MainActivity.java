package com.example.talong.lab7;

import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends TabActivity {
    private ArrayList<Restaurant> restaurants;
    EditText name;
    EditText address ;


    private RestaurantAdapter restaurantAdapter=null;


    RestaurantHelper restaurantHelper=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantHelper=new RestaurantHelper(this);
        restaurants=restaurantHelper.getAllData();
        final ListView lvRestaurant= findViewById(R.id.restaurants);
        Button save=findViewById(R.id.save);
        name=findViewById(R.id.name);
        address=findViewById(R.id.addr);

       // mDBRef=FirebaseDatabase.getInstance().getReference();

        restaurantAdapter = new RestaurantAdapter(MainActivity.this,R.layout.custom_lisview,restaurants);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Restaurant r = new Restaurant();

                        r.setName(name.getText().toString());
                        r.setAddress(address.getText().toString());
                        RadioGroup type = (RadioGroup) findViewById(R.id.types);
                        switch (type.getCheckedRadioButtonId()) {
                            case R.id.take_out:
                                r.setType("Take out");
                                break;
                            case R.id.sit_down:
                                r.setType("Sit down");
                                break;
                            case R.id.delivery:
                                r.setType("Delivery");
                                break;
                        }
                        Toast.makeText(MainActivity.this,name.getText().toString()+" " + address.getText().toString()+ " "+ r.getType(),Toast.LENGTH_SHORT).show();
//                        r=new Restaurant(r.getName(),r.getAddress(),r.getType());
//                        mDBRef.child("Data").push().setValue(r);
                        restaurantHelper.insert(r.getName(),r.getAddress(),r.getType());




                    }


        });
        lvRestaurant.setAdapter(restaurantAdapter);
        restaurantAdapter.notifyDataSetChanged();
//        mDBRef.child("Data").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String name=dataSnapshot.child("name").getValue().toString();
//                String address=dataSnapshot.child("address").getValue().toString();
//                String type=dataSnapshot.child("type").getValue().toString();
//                nameTitle.setText(name);
//                textAddress.setText(address);
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        Button btnDelete=findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant r=new Restaurant();
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xóa thư mục")
                        .setMessage("Bạn có chắc chắn muốn xóa hết không?")
                        .setNegativeButton("Cancel",null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                restaurantAdapter.notifyDataSetChanged();
                            }
                        });
                builder.show();


            }
        });

        lvRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Name:" +name.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        lvRestaurant.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r=restaurants.get(position);
                final int removePosition=position;
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xóa thư mục")
                        .setMessage("Bạn có chắc chắn muốn xóa không?"+ r.getName())

                        .setNegativeButton("Cancel",null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restaurants.remove(removePosition);
                                restaurantAdapter.notifyDataSetChanged();
                            }
                        });
                builder.show();
                return false;
            }
        });
        TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.restaurants);
        spec.setIndicator("List",getResources().getDrawable(R.drawable.list));
        getTabHost().addTab(spec);

        spec=getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("Detail",getResources().getDrawable(R.drawable.home));
        getTabHost().addTab(spec);
        getTabHost().setCurrentTab(0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        restaurantHelper.close();
    }

}
