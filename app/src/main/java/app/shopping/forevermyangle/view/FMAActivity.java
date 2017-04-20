package app.shopping.forevermyangle.view;

import android.support.v7.app.AppCompatActivity;

public class FMAActivity extends AppCompatActivity {

    private FMAProgessDialog mFMAProgessDialog = null;

    public void showProgressing(String message) {
        if (mFMAProgessDialog == null) {
            mFMAProgessDialog = new FMAProgessDialog(this);
        }
        if (message != null) {
            mFMAProgessDialog.setMessage("");
        }

        mFMAProgessDialog.show();
    }

    public void hideProgressing() {
        if (mFMAProgessDialog == null) {
            return;
        }
        mFMAProgessDialog.hide();
    }

    @Override
    protected void onDestroy() {
        mFMAProgessDialog = null;
        super.onDestroy();
    }
}
