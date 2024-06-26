package com.example.shopSaver;

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
import java.util.List;

// ListaAdapter.java
public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ItemViewHolder> {
    // items es final, la referencia a items no puede ser reasignada después de la inicialización
    private final List<ListItem> items;
    private final Context context;

    private OnItemStateChangedListener itemStateChangedListener;


    public ListaAdapter(Context context, List<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        final ListItem item = items.get(position); // item es final, la referencia a item no puede ser reasignada después de la inicialización
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
        // Configurar OnLongClickListener para cambiar el texto al hacer click largo
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showChangeTextDialog(holder.getAdapterPosition()); // Mostrar el diálogo para cambiar el texto
                return true;
            }
        });



    }

    private void showChangeTextDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(this.context.getString(R.string.ModificarString));
        // Crear un EditText dentro del diálogo para que el usuario ingrese el nuevo texto
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(items.get(position).getText());
        builder.setView(input);
        // Configurar los botones del diálogo
        builder.setPositiveButton(this.context.getString(R.string.AceptarString), new DialogInterface.OnClickListener() {
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
        builder.setNegativeButton(this.context.getString(R.string.QuitarString), new DialogInterface.OnClickListener() {
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
        final TextView itemName;

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
