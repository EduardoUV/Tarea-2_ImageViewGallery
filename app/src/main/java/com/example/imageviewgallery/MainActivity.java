package com.example.imageviewgallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.content.Intent.ACTION_PICK;

public class MainActivity extends AppCompatActivity {

    ImageView view1;

    private static final int SELECT_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view1 = (ImageView) findViewById(R.id.view1);
    }

    protected void OpenGallery(View view) {
        Intent intent = new Intent(ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Selecciona una imagen..."), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case SELECT_PICTURE:

                if (resultCode == RESULT_OK) {

                    Uri path = data.getData();

                    Redimencionar(path);

                    try {

                        InputStream is = getContentResolver().openInputStream(path);

                        BufferedInputStream bis = new BufferedInputStream(is);

                        Bitmap bitmap = BitmapFactory.decodeStream(bis);

                        view1.setImageBitmap(bitmap);

                        Toast.makeText(this, "Direccion de la imagen: " + path.toString(), Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException e) {

                    }
                }

                break;
        }
    }
//REDIMENCION DE LA IMAGEN CON LA LIBRERIA DE PICASSO
    public void Redimencionar(Uri a) {

        Picasso.with(MainActivity.this)
                .load(a)
                .resize(750,900)
                .into(view1);
    }
    //BOTON DE CERRAR
    public void CloseApp(View view) {
        finish();
    }

}