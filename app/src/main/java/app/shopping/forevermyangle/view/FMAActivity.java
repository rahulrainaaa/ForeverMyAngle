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
    private FMAProgessDialog mFMAProgessDialog = null;

    /**
     * @param message Message to show on the processing dialog.
     * @method showProgressing
     * @desc Method to init and show the processing progress as dialog and disable the UI interaction.
     */
    public void showProgressing(String message) {
        if (mFMAProgessDialog == null) {
            mFMAProgessDialog = new FMAProgessDialog(this);
        }
        if (message != null) {
            mFMAProgessDialog.setMessage("");
        }

        mFMAProgessDialog.show();
    }

    /**
     * @method hideProgressing
     * @desc Method to hide the progressing process dialog.
     */
    public void hideProgressing() {
        if (mFMAProgessDialog == null) {
            return;
        }
        mFMAProgessDialog.hide();
    }

    /**
     * {@link AppCompatActivity} override method to handle onDestroy for releasing resources.
     */
    @Override
    protected void onDestroy() {
        mFMAProgessDialog = null;
        super.onDestroy();
    }

    /**
     * @param signal
     * @method signalMessage
     * @desc Abstract method to send an int code for signal from other class (for callback purpose).
     */
    public abstract void signalMessage(int signal);
}
