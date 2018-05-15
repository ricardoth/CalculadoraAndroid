package notas.cl.ejercicionotas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabHost tabHost;
    public Nota n;
    public EditText txtIngreso;
    public EditText txtPorcentaje;
    public TextView txtNotaNecesaria;
    public TextView txtSumaNotas;
    public TextView lblTotalNotas;
    public TextView lblNotaPonderacion;
    public TextView lblNotaCuatro;
    public TextView lblNotaFinal;
    public TextView lblSituacion;
    public Button btnRegistroNota;
    public ListView lwNotas;
    public List<Nota> notas;
    public float porcentajeObjeto;

    private static DecimalFormat df = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();


        tabHost = (TabHost) findViewById(R.id.idTabHost);
        tabHost.setup();


        //Tab 1
        TabHost.TabSpec idTabUno = tabHost.newTabSpec("idTabUno");
        idTabUno.setIndicator("Registro de notas");
        idTabUno.setContent(R.id.idTabUno);
        registroNota();



        TabHost.TabSpec idTabDos = tabHost.newTabSpec("idTabDos");
        idTabDos.setIndicator("Listado");
        idTabDos.setContent(R.id.idTabDos);
        cargarNotas();


        TabHost.TabSpec idTabTres = tabHost.newTabSpec("idTabTres");
        idTabTres.setIndicator("Estadística");
        idTabTres.setContent(R.id.idTabTres);
        mensajes();


        tabHost.addTab(idTabUno);
        tabHost.addTab(idTabDos);
        tabHost.addTab(idTabTres);
    }

    private void mensajes(){
        lblTotalNotas.setText("Promedio de Notas: ");
        //lblNotaPonderacion.setText("Ponderación para exámen: ");
        //lblNotaCuatro.setText("Nota para aprobar con un 4: ");
        lblSituacion.setText("Situación: ");
    }

    private void initComponents() {
        txtIngreso = (EditText) findViewById(R.id.txtIngreso);
        txtPorcentaje = (EditText) findViewById(R.id.txtPorcentaje);
        //txtNotaNecesaria = (TextView) findViewById(R.id.txtNotaNecesaria);
        lblTotalNotas = (TextView) findViewById(R.id.lblTotalNotas);
        //lblNotaPonderacion = (TextView) findViewById(R.id.lblNotaPonderacion);
        //lblNotaFinal = (TextView) findViewById(R.id.lblNotaFinal);
        lblSituacion = (TextView) findViewById(R.id.lblSituacion);
        //lblNotaCuatro = (TextView) findViewById(R.id.lblNotaParaCuatro);
        txtSumaNotas = (TextView) findViewById(R.id.txtSumaNotas);
        btnRegistroNota = (Button) findViewById(R.id.btnRegistroNota);
        lwNotas = (ListView) findViewById(R.id.lwNotas);

    }

    public void registroNota(){
        notas = new ArrayList<>();
        btnRegistroNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = notas.size() + 1;
                float notaIngreso = Float.parseFloat(txtIngreso.getText().toString());
                float porcentaje =  Float.parseFloat(txtPorcentaje.getText().toString());
                float porcentajeObtenido = porcentajeObjeto + porcentaje;

                if (porcentajeObtenido <= 100){
                    if (notaIngreso >= 1 && notaIngreso <= 7){
                        if (porcentaje > 0 && porcentaje <= 100){
                            n = new Nota(id,notaIngreso,porcentaje);
                            notas.add(n);

                        } else {
                            Toast.makeText(MainActivity.this, "El porcentaje no puede ser mayor a 100%", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "La nota debe ser entre 1 y 7", Toast.LENGTH_SHORT).show();
                    }
                } else if (porcentajeObtenido > 100){
                    Toast.makeText(MainActivity.this, "El porcentaje debe llegar al 100%", Toast.LENGTH_SHORT).show();
                }



                txtIngreso.setText("");
                txtPorcentaje.setText("");
                Toast.makeText(MainActivity.this,"Nota Agregada", Toast.LENGTH_SHORT).show();

                calculoNota();

            }
        });
    }

    public void calculoNota(){
        float suma = 0;
        float notaPromedio;
        double notaPonderacion = 0;
        double notaExamen;

        for (Nota n : notas){
            notaPromedio =  n.getValor() * n.getPorcentaje() / 100;
            suma += notaPromedio;
            porcentajeObjeto = n.getPorcentaje();
        }

        notaPonderacion = suma * 0.7;
        double notaFinal = (3.95 - notaPonderacion) / 0.3;

        String situacion = "[situacion]";


        if (suma < 3.5){
            situacion = "Reprobado";
        } else if (suma > 3.5 && suma < 5.3){
            situacion = "Necesitas un " + df.format(notaFinal)+ " para pasar";
        } else {
            situacion = "Aprobado";
        }


        String resultado = String.valueOf(df.format(suma));
        //DecimalFormat df = new DecimalFormat("0.00");
        txtSumaNotas.setText(resultado);

       /* String prom = String.valueOf(notaPonderacion);
        txtNotaNecesaria.setText(prom);

        String nFinal = String.valueOf(notaFinal);
        lblNotaFinal.setText(nFinal);*/

        String theSituation = String.valueOf(situacion);
        lblSituacion.setText(theSituation);
    }


    public void calcular(){
        float suma, promedio, cantidad, notaPresentacion, notaExamen, notaPromedio;

        notaPromedio = 0;
        cantidad = notas.size();
        notaPresentacion = (float) 0.7;
        notaExamen = (float) 0.3;

        for (Nota no : notas){
            notaPromedio =+ (no.getValor() * no.getPorcentaje()) / 100;
        }

        promedio = notaPromedio * notaPresentacion;

        //Muestra el resultado de la suma de los valores de la lista
        String resultado = String.valueOf(promedio);
        txtSumaNotas.setText(resultado);

        Toast.makeText(MainActivity.this, "Nota Promedio" + notaPromedio, Toast.LENGTH_LONG).show();
    }

    public List<Nota> getNotas(){
        return notas;
    }

    private void cargarNotas(){
        ArrayAdapter<Nota> adapter = new ArrayAdapter<Nota>(this,android.R.layout.simple_list_item_1, getNotas());
        lwNotas.setAdapter(adapter);
    }

}
