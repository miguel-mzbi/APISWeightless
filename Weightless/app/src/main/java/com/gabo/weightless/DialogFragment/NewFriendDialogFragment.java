package com.gabo.weightless.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gabo.weightless.R;



public class NewFriendDialogFragment extends DialogFragment {

    private EditText friendNameInput;

    public interface NewFriendDialogInterface {
        public void onFinishNewFriendDialog(String friend);
    }

    private NewFriendDialogInterface listener;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add a new Friend");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.new_friend_dialog, null);
        friendNameInput = (EditText) view.findViewById(R.id.friendsNameInput);


        builder.setView(view)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onFinishNewFriendDialog(friendNameInput.getText().toString());
                        NewFriendDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        NewFriendDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (NewFriendDialogInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NewFriendDialogInterface");
        }
    }
}
