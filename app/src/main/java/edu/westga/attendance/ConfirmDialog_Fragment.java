package edu.westga.attendance;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Wayne on 4/26/2016.
 *
 * Fragment for getting confirmation
 */
public class ConfirmDialog_Fragment extends DialogFragment {

    private String messageTxt;
    private String option;
    private ConfirmListener listener;

    interface ConfirmListener{
        void onConfirm(String option);
    }

    public ConfirmDialog_Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.confirm, container, false);
        TextView message = (TextView) theView.findViewById(R.id.textViewMessage);
        message.setText(messageTxt);

        final Button yesButton = (Button) theView.findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                yesButtonClicked(v);
            }
        });

        final Button closeButton = (Button) theView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                closeButtonClicked(v);
            }
        });
        return theView;
    }

    private void closeButtonClicked(View v) {
        this.dismiss();
    }

    private void yesButtonClicked(View v) {
        listener.onConfirm(option);

        this.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ConfirmListener)context;
    }

    public void setMessage(String message) { this.messageTxt = message; }
    public void setOption(String option) { this.option = option; }
}
