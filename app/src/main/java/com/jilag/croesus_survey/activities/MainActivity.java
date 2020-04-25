package com.jilag.croesus_survey.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jilag.croesus_survey.utils.AsyncFunctions;
import com.jilag.croesus_survey.utils.DBHelper;
import com.jilag.croesus_survey.R;
import com.jilag.croesus_survey.models.Users;
import com.jilag.croesus_survey.utils.Networks;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button next;
    ImageView selfie;
    String fname, lname, id_num_str;
    int id_num;
    EditText fname_ed, lname_ed, id_num_ed;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fname_ed = (EditText) findViewById(R.id.etFName);
        lname_ed = (EditText) findViewById(R.id.etLName);
        id_num_ed = (EditText) findViewById(R.id.etID);

        next = (Button) findViewById(R.id.btnCnt);

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                fname = fname_ed.getText().toString();
                lname = lname_ed.getText().toString();
                id_num_str = id_num_ed.getText().toString();

                if (fname.equals("") || lname.equals("") || id_num_str.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Fill in all details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SharedPreferences shared = getSharedPreferences("Croesus", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("id_num_str", id_num_str);
                    editor.commit();

                    id_num = Integer.parseInt(id_num_str);

                    DBHelper db = new DBHelper(getApplicationContext());
                    List<Users> user = db.getUser(id_num);
                    int size = user.size();

                    if (size==0)
                    {
                        db.addUser(new Users(id_num, fname, lname,""));
                    }

                    Networks networks = new Networks(getApplicationContext());

                    if (networks.isNetworkConnected() && networks.isInternetAvailable()) { // if internet connection is available
                        AsyncFunctions asyncFunctions = new AsyncFunctions(getApplicationContext());

                        asyncFunctions.updateUsers();

                    }

                    Intent intent = new Intent(getApplicationContext(),SurveysActivity.class);
                    startActivity(intent);

                }

            }
        });

        selfie = (ImageView) findViewById(R.id.ImgProfile);

        selfie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
               // Toast.makeText(getApplicationContext(), "Selfie time", Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
                else
                {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selfie.setImageBitmap(photo);
        }
    }
}
