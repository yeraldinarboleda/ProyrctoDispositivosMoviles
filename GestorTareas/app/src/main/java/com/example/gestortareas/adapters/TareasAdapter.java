package com.example.gestortareas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestortareas.EditarTareaActivity;
import com.example.gestortareas.R;
import com.example.gestortareas.models.Tarea;

import java.util.List;

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.ViewHolder> {

    List<Tarea> lista;
    Context ctx;

    public TareasAdapter(List<Tarea> lista, Context ctx) {
        this.lista = lista;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_tarea, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Tarea t = lista.get(pos);

        // Mostrar texto en el item
        h.titulo.setText(t.titulo);
        h.fecha.setText(t.fecha_limite);

        // === BOTÓN EDITAR ===
        h.btnEditar.setOnClickListener(v -> {
            Intent i = new Intent(ctx, EditarTareaActivity.class);

            i.putExtra("id", t.id);
            i.putExtra("titulo", t.titulo);
            i.putExtra("descripcion", t.descripcion);
            i.putExtra("fecha_limite", t.fecha_limite);
            i.putExtra("estado", t.estado);

            ctx.startActivity(i);
        });

        // === BOTÓN ELIMINAR === (si ya te funciona, déjalo)
        h.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEliminarClick(t.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    // Listener para eliminar
    public interface OnItemClickListener {
        void onEliminarClick(int id);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // ============ VIEW HOLDER ===============
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, fecha;
        Button btnEditar, btnEliminar;

        public ViewHolder(@NonNull View v) {
            super(v);

            titulo = v.findViewById(R.id.txtTituloItem);
            fecha = v.findViewById(R.id.txtFechaItem);

            // Aquí se enlazan los botones del XML
            btnEditar = v.findViewById(R.id.btnEditar);
            btnEliminar = v.findViewById(R.id.btnEliminar);
        }
    }
}
