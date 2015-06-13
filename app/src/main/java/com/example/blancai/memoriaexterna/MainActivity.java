package com.example.blancai.memoriaexterna;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity implements OnClickListener {

    //se declaran las variables
    private EditText txtTexto;
    private Button btnGuardar, btnAbrir;
    private static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //se conecta la parte logicA con la grafica
        txtTexto = (EditText) findViewById(R.id.txtArchi);
        btnGuardar = (Button) findViewById(R.id.bGuardar);
        btnAbrir = (Button) findViewById(R.id.bAbrir);
        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        File sdCard, directory,
                file = null;
        try {
            // se valida si la memoria se encuentra insertada
            if (Environment.getExternalStorageState().equals("mounted")) {
                // Obtenemos el directorio de la memoria externa sdCard = Environment.getExternalStorageDirectory();
                sdCard = Environment.getExternalStorageDirectory();
                if (v.equals(btnGuardar)) {
                    String str = txtTexto.getText().toString();
                    // Clase que permite grabar texto en un archivo FileOutputStream fout = null;
                    try {


                        // creamos el archivo en el nuevo directorio creado
                        file = new File(directory, "MiArchivo.txt");

                        fout = new FileOutputStream(file);


                        OutputStreamWriter ows = new OutputStreamWriter(fout);
                        ows.write(str);// Escribe en el buffer la cadena de texto
                        ows.flush(); //  Volca lo que hay en el buffer al archivo
                        ows.close();// Cierra el archivo de texto

                        //nos muestra que se almaceno el archivo
                        Toast.makeText(getBaseContext(), "El archivo se ha almacenado!!!", Toast.LENGTH_SHORT).show();
                        txtTexto.setText("");
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }

            if (v.equals(btnAbrir)) {
                try {
                    //se obtien el direcorio donde se encuentra nuestro archivo
                    directory = new File(sdCard.getAbsolutePath() + "/Mis archivos");

                    // Creamos un objeto File de nuestro archivo a leer
                    file = new File(directory, "MiArchivo.txt");


                    // el cual representa un stream del archivo que vamos a leer
                    FileInputStream fin = new FileInputStream(file);


                    InputStreamReader isr = new InputStreamReader(fin);

                    char[] inputBuffer = new char[READ_BLOCK_SIZE];
                    String str = "";

                    // Se lee el archivo de texto mientras no se llegue al
                    // final
                    // de él
                    int charRead;
                    while ((charRead = isr.read(inputBuffer)) > 0) {
                        // Se lee por bloques de 100 caracteres
                        // ya que se desconoce el tamaño del texto
                        // Y se va copiando a una cadena de texto
                        String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                        str += strRead;

                        inputBuffer = new char[READ_BLOCK_SIZE];
                    }

                    // Se muestra el texto leido en la caje de text
                    txtTexto.setText(str);
                    isr.close();


                    //se muestra un mensaje que se a cargado el archivo
                    Toast.makeText(getBaseContext(), "El archivo ha sido cargado", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            } else
                //se muestra un mensaje que no encuentra la memoria externa
                Toast.makeText(getBaseContext(), "El almacenamineto externo no se encuentra disponible", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
        }
        }


    }
}



