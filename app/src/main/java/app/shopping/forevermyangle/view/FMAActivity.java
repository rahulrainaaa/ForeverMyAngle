package app.shopping.forevermyangle.view;

import android.support.v7.app.AppCompatActivity;

/**
 * @class FMAActivity
 * @desc {@link AppCompatActivity} Abstract base class for FMA Activity.
 */
public abstract class FMAActivity extends AppCompatActivity {

    /**
     * Private class data members.
     */
    private FMAProgressDialog mFMAProgressDialog = null;

    /**
     * @param message Message to show on the processing dialog.
     * @method showProgressing
     * @desc Method to init and show the processing progress as dialog and disable the UI interaction.
     */
    public void showProgressing(String message) {
        if (mFMAProgressDialog == null) {
            mFMAProgressDialog = new FMAProgressDialog(this);
        }
        if (message != null) {
            mFMAProgressDialog.setMessage("");
        }

        mFMAProgressDialog.show();
    }

    /**
     * @method hideProgressing
     * @desc Method to hide the progressing process dialog.
     */
    public void hideProgressing() {
        if (mFMAProgressDialog == null) {
            return;
        }
        mFMAProgressDialog.hide();
    }

    /**
     * {@link AppCompatActivity} override method to handle onDestroy for releasing resources.
     */
    @Override
    protected void onDestroy() {
        mFMAProgressDialog = null;
        super.onDestroy();
    }

    /**
     * @param signal
     * @method signalMessage
     * @desc Abstract method to send an int code for signal from other class (for callback purpose).
     */
    public abstract void signalMessage(int signal);
}
