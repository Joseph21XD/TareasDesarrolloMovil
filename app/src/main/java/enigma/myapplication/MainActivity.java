package enigma.myapplication;

import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    public static double valor=0;
    private static String url= "https://finance.google.com/finance/converter?a=1&from=AED&to=BAM&meta=ei%3DS4aPWvGSM460mAHfnbW4DA";
    private static String pais1="AED";
    private static String pais2="AED";
    private static boolean estado= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.paises , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinner_adapter);
        spin2.setAdapter(spinner_adapter);

        spin. setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener( ) {

            @Override
            public void onItemSelected (AdapterView <?> parent, View view,
                                        int pos, long id ) {

                RadioButton lblResultado = findViewById (R.id.radioButton) ;
                RadioButton lblResultado2 = findViewById (R.id.radioButton2) ;
                pais1=parent. getItemAtPosition (pos)+"";
                lblResultado.setText(parent. getItemAtPosition (pos)+" a "+ pais2);
                lblResultado2.setText(pais2 +" a "+ parent. getItemAtPosition (pos) );


            }

            @Override
            public void onNothingSelected (AdapterView <?> arg0 ) {

            }
        } ) ;

        spin2. setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener( ) {

            @Override
            public void onItemSelected (AdapterView <?> parent, View view,
                                        int pos, long id ) {

                RadioButton lblResultado = findViewById (R.id.radioButton) ;
                RadioButton lblResultado2 = findViewById (R.id.radioButton2) ;
                pais2=parent. getItemAtPosition (pos)+"";
                lblResultado2.setText(parent. getItemAtPosition (pos)+" a "+ pais1);
                lblResultado.setText(pais1 +" a "+ parent. getItemAtPosition (pos) );


            }

            @Override
            public void onNothingSelected (AdapterView <?> arg0 ) {

            }
        } ) ;
    }


    public void pushButton(View view){

        TextView t= findViewById(R.id.TextView1);
        EditText f= findViewById(R.id.editText);
        t.setText("Espere...");
        if(estado)
            url= "https://finance.google.com/finance/converter?a="+f.getText().toString()+"&from="+pais1+"&to="+pais2+"&meta=ei%3DS4aPWvGSM460mAHfnbW4DA";
        else
            url= "https://finance.google.com/finance/converter?a="+f.getText().toString()+"&from="+pais2+"&to="+pais1+"&meta=ei%3DS4aPWvGSM460mAHfnbW4DA";
        Log.d("BARCA: ", url);
        if(!(pais1.equals(pais2)))
            new MyTask().execute();
        else
            t.setText(f.getText().toString());
    }

    public  void pushRadioButton(View view){
        RadioButton r1= findViewById(R.id.radioButton);
        RadioButton r2= findViewById(R.id.radioButton2);

        if(estado){
            r1.setChecked(false);
            r2.setChecked(true);
            estado=false;
        }
        else{
            r1.setChecked(true);
            r2.setChecked(false);
            estado=true;
        }
    }



    private class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            URL pagina = null;
            try {
                String texto="bld>";
                String texto2="";
                if(MainActivity.estado)
                    texto2=MainActivity.pais2+"<";
                else
                    texto2=MainActivity.pais1+"<";
                pagina = new URL(MainActivity.url);
                URLConnection uc = pagina.openConnection();
                uc.connect();

                BufferedReader lector = new BufferedReader(new InputStreamReader(
                        uc.getInputStream(), "UTF-8"));
                String linea = "";

                while ((linea = lector.readLine()) != null) {

                    if (linea.contains(texto)) {
                        int idx = 0, coincidencias = 0;

                        while (linea.indexOf(texto, idx) > 0) {
                            System.out.println("RESULTADO BARCA: "+Double.parseDouble(linea.substring(linea.indexOf(texto, idx)+4, linea.indexOf(texto2, idx))));
                            MainActivity.valor=Double.parseDouble(linea.substring(linea.indexOf(texto, idx)+4, linea.indexOf(texto2, idx)));
                            idx = linea.indexOf(texto, idx) + 1 + texto.length();
                            coincidencias++;
                        }
                    }
                }


            } catch (MalformedURLException e) {
                Log.d("ERROR", "MalformedURLException");
            } catch (IOException e) {
                Log.d("ERROR", "IOException");
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String result) {
            TextView t= findViewById(R.id.TextView1);
            t.setText(MainActivity.valor+"");
            MainActivity.valor=0;
        }


    }
}
