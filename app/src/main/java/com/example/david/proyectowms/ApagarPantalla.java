package com.example.david.proyectowms;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ApagarPantalla extends Activity {
    String conexion = "";
    String parametro = "";
    String user = "";
    String pass = "";
    String ip = "";
    String sucursal = "";
    String titulo = "";
    String proceso = "";
    String tipo = "";
    String procesOriginal = "";
    String empresa;
    String Sucursal;
    String cod_placa = "";
    String num_rombo = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar_pantalla);

        final Bundle bundle = this.getIntent().getExtras();
        parametro = bundle.getString("parametro");
        cod_placa = bundle.getString("placa");
        num_rombo = bundle.getString("num_rombo");
        user = bundle.getString("user");
        empresa = bundle.getString("empresa");
        Sucursal = bundle.getString("sucursal");
        procesOriginal = bundle.getString("procesOriginal");
        pass = bundle.getString("pass");
        proceso = bundle.getString("proceso");
        System.out.println("PROCESOOOoOoOoOoo" + proceso);
        conexion = bundle.getString("conexion");
        ip = bundle.getString("ip");
        tipo = bundle.getString("tipo");
        sucursal = bundle.getString("sucursal");
    }

    public void salir(View v){
        finish();

    }

    public void guardar(View v){
        EditText ubicacion = (EditText) findViewById(R.id.ubicacion);

        String url = "http://" + ip + "/guardarMovimiento.php";


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sCodSucursal", sucursal.trim()));
        params.add(new BasicNameValuePair("sUbicacion", ubicacion.getText().toString()));
        params.add(new BasicNameValuePair("sParametro", "apagarPantalla"));


        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);

        Toast.makeText(ApagarPantalla.this, "Haz actualizado correctamente!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public String getHttpPost(String url,List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
