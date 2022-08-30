package com.example.playmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView list;
    String[] item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list= findViewById(R.id.listview);
        runtimepermission();
    }

    public void runtimepermission() //method
    {
      Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
              .withListener(new PermissionListener() {
                  @Override
                  public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                       displaysong();
                  }

                  @Override
                  public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                  }

                  @Override
                  public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                      permissionToken.continuePermissionRequest();
                  }
              }).check();
    }

    public ArrayList<File> fingsong(File file)
    {
        ArrayList<File> arraylist = new ArrayList<>();
        File[] files = file.listFiles();

        for(File singlefile : files)
        {
            if(singlefile.isDirectory() && !singlefile.isHidden())
            {
                arraylist.addAll(fingsong(singlefile));
            }
            else
            {
                if(singlefile.getName().endsWith(".mp3") ||singlefile.getName().endsWith(".wav"))
                {
                    arraylist.add(singlefile);
                }
            }
        }
        return arraylist;
    }
    public void displaysong()
    {
        ArrayList<File> mysong = fingsong(Environment.getExternalStorageDirectory());

        item = new String[mysong.size()];

        for(int i =0;i<mysong.size();i++)
        {
            item[i] = mysong.get(i).getName().toString().replace(".mp3","").replace(".wav","");

        }
        customAdapter customAdapter = new customAdapter();
        list.setAdapter(customAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 String songName  = (String) list.getItemAtPosition(position);
                 startActivity(new Intent(getApplicationContext(),playeractivity.class).putExtra("songs",mysong)
                         .putExtra("songname",songName)
                         .putExtra("pos",position)
                 );
            }
        });



    }
    class customAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
          return item.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.listitem,null);
            TextView songtext = view.findViewById(R.id.songlist1);
            ImageView image = view.findViewById(R.id.songimage);
            songtext.setSelected(true);
            image.setImageResource(R.drawable.music);
            songtext.setText(item[position]);
            return view;
        }
    }
}