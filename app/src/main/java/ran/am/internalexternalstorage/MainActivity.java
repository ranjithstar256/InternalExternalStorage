package ran.am.internalexternalstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText editTextFileName, editTextData;
    Button saveInternalButton, readinternalButton;
    Button saveExternalButton, readExternalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextFileName = findViewById(R.id.editText1);
        editTextData = findViewById(R.id.editText2);
        saveInternalButton = findViewById(R.id.button1);
        readinternalButton = findViewById(R.id.button2);

        saveExternalButton = findViewById(R.id.button14);
        readExternalButton = findViewById(R.id.button17);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        //Performing Action on Read Button
        saveInternalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String filename = editTextFileName.getText().toString();
                String data = editTextData.getText().toString();

                FileOutputStream fos;
                try {
                    fos = openFileOutput(filename, Context.MODE_PRIVATE);
                    fos.write(data.getBytes());
                    fos.close();

                    Toast.makeText(getApplicationContext(), filename + " saved Internal",
                            Toast.LENGTH_LONG).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        //Performing Action on Read Button
        readinternalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String filename = editTextFileName.getText().toString();

                StringBuffer stringBuffer = new StringBuffer();

                try {
                    //Attaching BufferedReader to the FileInputStream by the help of InputStreamReader
                    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                            openFileInput(filename)));

                    String inputString;
                    //Reading data line by line and storing it into the stringbuffer

                    while ((inputString = inputReader.readLine()) != null) {
                        stringBuffer.append(inputString + "\n");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Displaying data on the toast
                Toast.makeText(getApplicationContext(), stringBuffer.toString(),
                        Toast.LENGTH_LONG).show();

            }

        });

        saveExternalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String filename = editTextFileName.getText().toString();
                String data = editTextData.getText().toString();

                try {
                    File myFile = new File("/sdcard/" + filename);
                    myFile.createNewFile();

                    FileOutputStream fOut = new FileOutputStream(myFile);

                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                    myOutWriter.append(data);
                    myOutWriter.close();
                    fOut.close();

                    Toast.makeText(getApplicationContext(), filename + "saved External", Toast.LENGTH_LONG).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        //Performing action on Read Button
        readExternalButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String filename = editTextFileName.getText().toString();

                String aDataRow = "";
                String aBuffer = "";
                try {
                    File myFile = new File("/sdcard/" + filename);
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader myReader = new BufferedReader(
                            new InputStreamReader(fIn));

                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow + "\n";
                    }
                    myReader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext

                        (), aBuffer, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}