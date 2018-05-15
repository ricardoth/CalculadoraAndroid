package notas.cl.ejercicionotas;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by ricar on 09-04-2018.
 */

public class Nota {
    private int id;
    private float valor;
    private float porcentaje;

    public Nota (int id, float valor, float porcentaje) {
        this.id = id;
        this.valor = valor;
        this.porcentaje = porcentaje;
    }

    private static final NumberFormat nf = new DecimalFormat("#0.0");

   /* public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }


    @Override
    public String toString() {
        return new StringBuilder()
                .append("Nota ").append(id)
                .append("-> ").append(nf.format(valor))
                .append("-> (").append(porcentaje)
                .append("%)").toString();
    }
}
