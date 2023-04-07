package com.example.parsingjsonexemple;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ItemModel> arrayList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(readJSON());

            JSONArray array = object.getJSONArray("contacts");
            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);
                String id = jsonObject.getString("id");
                String first_name = jsonObject.getString("first_name");
                String last_name = jsonObject.getString("last_name");
                String job = jsonObject.getString("job");
                String email = jsonObject.getString("email");
                String phone = jsonObject.getString("phone");
                String gender = jsonObject.getString("gender");

                final ItemModel model = new ItemModel();
                model.setId(id);
                model.setName(first_name + " " + last_name);
                model.setJob(job);
                model.setEmail(email);
                model.setPhone(phone);
                model.setGender(gender);
                arrayList.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomAdapter adapter = new CustomAdapter(this, arrayList);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.deferNotifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemModel item = (ItemModel) adapterView.getItemAtPosition(i);
                Uri telephone = Uri.parse("tel:" + item.getPhone());
                Intent secondeActivite = new Intent(Intent.ACTION_DIAL, telephone);
                startActivity(secondeActivite);
            }
        });
    }

    public String readJSON() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = getAssets().open("data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        return json;
    }

    public class CustomAdapter extends BaseAdapter {

        Context context;
        ArrayList<ItemModel> arrayList;

        public CustomAdapter(Context context, ArrayList<ItemModel> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            }
            // gary background if position is even
            if (position % 2 == 0) {
                convertView.setBackgroundColor(getResources().getColor(R.color.colorGray));
            } else {
                convertView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }

            TextView name, job, email, phone;
            ImageView imageView = (ImageView) convertView.findViewById(R.id.img);
            name = (TextView) convertView.findViewById(R.id.txt_name);
            job = (TextView) convertView.findViewById(R.id.txt_job);
            email = (TextView) convertView.findViewById(R.id.txt_email);
            phone = (TextView) convertView.findViewById(R.id.txt_phone);

            ItemModel model = arrayList.get(position);
            name.setText(model.getName());
            job.setText(model.getJob());
            email.setText(model.getEmail());
            phone.setText(model.getPhone());
            String url = "https://randomuser.me/api/portraits/" + model.getGender() + "/" + model.getId() + ".jpg";
            Picasso.get().load(url).into(imageView);

            final ItemModel temp = arrayList.get(position);


//            callbtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Uri telephone = Uri.parse("tel:" + temp.getPhone());
//                    Intent secondeActivite = new Intent(Intent.ACTION_DIAL, telephone);
//                    startActivity(secondeActivite);
//
//                }
//            });

            return convertView;
        }
    }

}