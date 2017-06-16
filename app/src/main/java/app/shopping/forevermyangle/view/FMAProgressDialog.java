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
 * @class FMAProgressDialog
 * @desc ProgressBar dialog box to show the processing foreground.
 */
public class FMAProgressDialog {

    /**
     * Private class data members.
     */
    private Activity mActivity = null;
    private String mText = "";
    private AlertDialog mAlertDialog = null;
    private ProgressBar mProgressBar = null;
    private TextView mTextView = null;
    private boolean isOpen = false;

    /**
     * @param activity
     * @constructor FMAProgressDialog
     * @desc to initialize the data members.
     */
    public FMAProgressDialog(Activity activity) {

        this.mActivity = activity;
    }

    /**
     * @param text
     * @method setMessage
     * @desc Method to setText in progress dialog.
     */
    public void setMessage(String text) {
        this.mText = text.trim();

        // Set text in view if visible.
        if (mTextView != null) {
            mTextView.setText("" + mText.trim());
        }
    }

    /**
     * @method show
     * @desc Method to show the {@link FMAProgressDialog} over activity.
     */
    public void show() {

        View view = mActivity.getLayoutInflater().inflate(R.layout.process_dialog, null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_processing);
        mTextView = (TextView) view.findViewById(R.id.process_text);
        mTextView.setText("" + mText.trim());
        mAlertDialog = new AlertDialog.Builder(mActivity).create();
        mAlertDialog.setView(view);
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
        isOpen = true;
    }

    /**
     * @method hide
     * @desc Method to hide the progress dialog from the activity.
     */
    public void hide() {

        if (isOpen) {

            mProgressBar = null;
            mTextView = null;
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
        isOpen = false;
    }

    /**
     * @return isOpen
     * @method isVisible
     * @desc Method to return the visibility status of {@link FMAProgressDialog} over an activity.
     */
    public boolean isVisible() {

        return isOpen;
    }

    /**
     * @method dismiss
     * @desc Method to dismiss and release the memory.
     */
    public void dismiss() {

        hide();
    }

    /**
     * @throws Throwable
     * @destructor To dismiss and remove the references.
     */
    @Override
    protected void finalize() throws Throwable {

        dismiss();
        super.finalize();
        System.gc();
    }
}
