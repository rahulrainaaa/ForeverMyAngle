package app.shopping.forevermyangle.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import app.shopping.forevermyangle.R;

/**
 * @class FMAProgessDialog
 * @desc Class to handle the Progress Dialog for this application.
 */

public class FMAProgessDialog {

    /**
     * Class private data members.
     */
    private AlertDialog.Builder builder;
    private AlertDialog alert = null;
    private Activity mActivity = null;
    private ProgressBar mProgress = null;
    private TextView mTextView = null;

    /**
     * @constructor FMAProgessDialog
     * @desc Initialize and create the dialog for progress.
     */
    public FMAProgessDialog(Activity activity) {

        this.mActivity = activity;

        // Getting view.
        View view = mActivity.getLayoutInflater().inflate(R.layout.process_dialog, null);
        mProgress = (ProgressBar) view.findViewById(R.id.progress_processing);
        mTextView = (TextView) view.findViewById(R.id.process_text);

        // Creating AlertDialog for holding.
        builder = new AlertDialog.Builder(mActivity);
        builder.setView(view).setCancelable(false);
        alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // alert.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

    /**
     * @param message
     * @method setMessage
     * @desc Method to set the message for processing dialog. (null for no message).
     */
    public void setMessage(String message) {
        if (mTextView == null) {
            return;
        }
        mTextView.setText(message.trim());
    }

    /**
     * @method show
     * @desc Method to show the processing dialog.
     */
    public void show() {
        if (alert == null) {
            return;
        }
        alert.show();
    }

    /**
     * @method hide
     * @desc Method to hide the processing dialog.
     */
    public void hide() {
        if (alert == null) {
            return;
        }
        alert.hide();
    }

    /**
     * @method dismiss
     * @desc Method to dismiss processing dialog and release the objects.
     */
    public void dismiss() {

        alert = null;
        builder = null;
        mTextView = null;
        mProgress = null;
        mActivity = null;

        // Init Garbage connection.`
        System.gc();
    }


}