package com.example.ninjareader.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.example.ninjareader.R;

/**
 * Created by alonrz on 2/25/15.
 */
public class AddArticleDialog extends DialogFragment  {
    public interface AddUrlDialogListener {
        void onFinishAddUrl(String url);
    }

    private EditText etUrl;

    public AddArticleDialog() {
        // Empty constructor required for DialogFragment
    }

    public static AddArticleDialog newInstance(String title) {
        AddArticleDialog frag = new AddArticleDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title", "Enter URL");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);

        builder.setIcon(R.drawable.ic_title_add_dark);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_dialog, null);
        etUrl = (EditText) view.findViewById(R.id.etUrl);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddUrlDialogListener listener = (AddUrlDialogListener) getActivity();
                listener.onFinishAddUrl(etUrl.getText().toString());
                dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



        builder.setView(view);
        return builder.create();
    }


}
