package toong.vn.androidhidekeyboardwhentouchoutsideedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final View v = getCurrentFocus();
        if (v instanceof EditText && (ev.getAction() == MotionEvent.ACTION_UP)) {
            int touchPos[] = new int[2];
            v.getLocationOnScreen(touchPos);
            float x = ev.getRawX() + v.getLeft() - touchPos[0];
            float y = ev.getRawY() + v.getTop() - touchPos[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                // NORMAL WAY
                // In some case it will not work well if the screen have > 2 EditText
                // From first EditText you click to second EditText, sometime keyboard will pull down a little bit then pull up again
                hideKeyboard();

                // MAGIC WAY
                // Test on few device and I see it work but not sure it will work in all devices or not
                // If it not work, user never open keyboard in app
                //                v.post(new Runnable() {
                //                    @Override
                //                    public void run() {
                //                        if (!(getCurrentFocus() instanceof EditText) || v.equals(
                //                                getCurrentFocus())) {
                //                            hideKeyboard();
                //                        }
                //                    }
                //                });
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
