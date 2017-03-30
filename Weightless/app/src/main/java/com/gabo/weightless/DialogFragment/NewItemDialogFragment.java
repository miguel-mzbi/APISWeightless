package com.gabo.weightless.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.renderscript.Double2;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.gabo.weightless.R;

/**
 * Created by MiguelAngel on 29/03/2017.
 */

public class NewItemDialogFragment extends DialogFragment {

    private EditText itemName, itemWeight, itemQuantity;

    public interface ItemDialogInterface {
        public void onFinishItemDialog(String name, double weight, int quantity);
    }

    private ItemDialogInterface listener;

    public static NewItemDialogFragment newInstance(String n, double w, int q, boolean b) {
        NewItemDialogFragment f = new NewItemDialogFragment();
        Bundle args = new Bundle();
        args.putString("name", n);
        args.putInt("qty", q);
        args.putDouble("weight", w);
        args.putBoolean("bool", b);
        f.setArguments(args);

        return f;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create New Item");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.new_item_dialog, null);
        itemName = (EditText) view.findViewById(R.id.itemName_ET);
        itemWeight = (EditText) view.findViewById(R.id.itemWeight_ET);
        itemQuantity = (EditText) view.findViewById(R.id.itemQuantity_ET);

        Bundle args = getArguments();
        if(args.getBoolean("bool")) {
            String name = getArguments().getString("name");
            String qty = Integer.toString(args.getInt("qty"));
            String weight = Double.toString(args.getDouble("weight"));
            itemName.setText(name);
            itemWeight.setText(weight);
            itemQuantity.setText(qty);
        }

        builder.setView(view)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onFinishItemDialog(itemName.getText().toString(),
                                Double.parseDouble(itemWeight.getText().toString()),
                                Integer.parseInt(itemQuantity.getText().toString()));
                        NewItemDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        NewItemDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ItemDialogInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ItemDialogListener");
        }
    }
}
