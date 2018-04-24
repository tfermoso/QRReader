package com.spinoffpyme.qrreader;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.spinoffpyme.qrreader.Database.UserRepository;
import com.spinoffpyme.qrreader.Local.UserDataSource;
import com.spinoffpyme.qrreader.Local.UserDatabase;
import com.spinoffpyme.qrreader.Model.User;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    private static final int RequestCameraPermisionID = 1001;
    private UserRepository userRepository;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermisionID:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cargamos datos en la bbdd de prueba
        UserDatabase userDatabase = UserDatabase.getmInstance(this);//create database
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()));

        loadData();

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();
        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA},RequestCameraPermisionID);
                    return;
                }

                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrcodes=detections.getDetectedItems();
                if(qrcodes.size()!=0){
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            //Create a vibrate
                            Vibrator vibrator=(Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("Datos: ",qrcodes.valueAt(0).displayValue);
                                    User user=userRepository.getUserByQR(qrcodes.valueAt(0).displayValue.toString());
                                    Log.i("Datos: ",userRepository.getAllUsers().get(0).getQr());
                                    Log.i("Datos: ",userRepository.getUserByQR("1111").getName());
                                    if(user!=null){
                                        txtResult.setText("Bienvenido "+user.getName());
                                    }else{
                                        txtResult.setText("Usuario no registrado");
                                    }
                                }
                            }).start();


                        }
                    });
                }
            }
        });
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                for (int i = 0; i < 50; i++) {
                User user = new User(UUID.randomUUID().toString().substring(0,5), UUID.randomUUID().toString() + "@gamil.com",UUID.randomUUID().toString());
                    userRepository.inserUser(user);
                    Log.i("User QR:",UUID.randomUUID().toString());
                }*/

                User user = new User("Patricia", UUID.randomUUID().toString() + "@gamil.com","1111");
                userRepository.inserUser(user);
                user = new User("Juan", UUID.randomUUID().toString() + "@gamil.com","2222");
                userRepository.inserUser(user);
                user = new User("Pedro", UUID.randomUUID().toString() + "@gamil.com","3333");
                userRepository.inserUser(user);
                user = new User("Luis", UUID.randomUUID().toString() + "@gamil.com","4444-5555-6666");
                userRepository.inserUser(user);

            }
        }).start();


    }
}
