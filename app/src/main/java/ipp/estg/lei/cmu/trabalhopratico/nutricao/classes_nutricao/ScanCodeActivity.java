package ipp.estg.lei.cmu.trabalhopratico.nutricao.classes_nutricao;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.Result;

import java.util.Objects;

import ipp.estg.lei.cmu.trabalhopratico.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int CAMERA_REQUEST = 1888;

    public static String resultado;
    ZXingScannerView scannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        requestPermissions();
    }

    //Responsável por mostrar e guardar o resultado em resultado
    @Override
    public void handleResult(Result result) {
        NutricaoMainActivity.result_txt.setText(result.getText());
        ScanCodeActivity.resultado = result.getText();
        String resultCode = "Código capturado";

        startActivity(new Intent(ScanCodeActivity.this, ClasseIntermediaria.class));

        Toast.makeText(ScanCodeActivity.this, resultCode, Toast.LENGTH_SHORT).show();

        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}