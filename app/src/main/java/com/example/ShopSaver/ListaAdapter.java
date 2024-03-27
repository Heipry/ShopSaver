package com.example.ShopSaver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// ListaAdapter.java
public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ItemViewHolder> {
    private List<ListItem> items = new ArrayList<>();
    private final Context context;

    private final String tiendaNombre;
    private OnItemStateChangedListener itemStateChangedListener;


    public ListaAdapter(Context context, List<ListItem> items, String tiendaNombre) {
        this.context = context;
        this.items = items;
        this.tiendaNombre = tiendaNombre;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        final ListItem item = items.get(position);
        holder.itemName.setText(item.getText());
        // Configurar el estado de tachado del elemento
        if (item.getActivo()) {
            holder.itemName.setPaintFlags(0); // Sin tachado

        } else {
            holder.itemName.setPaintFlags(holder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG); // Con tachado

        }

        // Configurar OnClickListener para cambiar el estado de tachado del elemento al hacer clic en él
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Cambiar el estado de tachado del elemento
                item.changeActivo();
                // Notificar al adaptador de que los datos han cambiado
                notifyItemChanged(holder.getAdapterPosition());
                // Llamar al método de la interfaz para notificar el cambio de estado a ListaActivity
                if (itemStateChangedListener != null) {
                    itemStateChangedListener.onItemStateChanged();
                }
            }
        });
        // Configurar OnLongClickListener para cambiar el texto al hacer clic largo
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showChangeTextDialog(holder.getAdapterPosition()); // Mostrar el diálogo para cambiar el texto
                // Llamar al método de la interfaz para notificar el cambio de estado a ListaActivity

                return true; // Indicar que el evento de clic largo ha sido consumido
            }
        });



    }

    private void showChangeTextDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Modificar elemento");
        // Crear un EditText dentro del diálogo para que el usuario ingrese el nuevo texto
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(items.get(position).getText());
        builder.setView(input);
        // Configurar los botones del diálogo
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newText = input.getText().toString();
                if (!TextUtils.isEmpty(newText)) {
                    // Cambiar el texto del elemento y notificar al adaptador
                    items.get(position).setText(newText);
                    notifyItemChanged(position);
                }
            }
        });
        builder.setNegativeButton("Quitar de la lista", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                items.remove(position);
                notifyItemRemoved(position);
                if (itemStateChangedListener != null) {
                    itemStateChangedListener.onItemStateChanged();
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }

    public interface OnItemStateChangedListener {
        void onItemStateChanged();
    }
    public void setItemStateChangedListener(OnItemStateChangedListener listener) {
        this.itemStateChangedListener = listener;
    }
}
