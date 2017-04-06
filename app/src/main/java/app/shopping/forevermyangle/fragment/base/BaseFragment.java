package app.shopping.forevermyangle.fragment.base;


import android.app.Fragment;
import android.view.MotionEvent;

import app.shopping.forevermyangle.interfaces.fragment.OnTouchEventListener;

public abstract class BaseFragment extends Fragment implements OnTouchEventListener {

    public abstract void onTouchEventCallback(MotionEvent event);
}
