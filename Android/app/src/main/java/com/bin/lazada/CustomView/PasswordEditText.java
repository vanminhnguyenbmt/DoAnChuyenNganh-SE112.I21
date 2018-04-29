package com.bin.lazada.CustomView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.bin.lazada.R;

@SuppressLint("AppCompatCustomView")
public class PasswordEditText extends EditText {

    Drawable eye, eyeStrike;
    Boolean visible = false;
    Boolean useStrike = false;
    Drawable drawable;
    int ALPHA = (int) (255 * .70f);

    public PasswordEditText(Context context) {
        super(context);
        khoitao(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        khoitao(attrs);
    }

    private void khoitao(AttributeSet attrs) {
        if(attrs != null) {
            TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText, 0, 0);
            this.useStrike = array.getBoolean(R.styleable.PasswordEditText_useStrike, false);
        }
        eye = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_black_24dp).mutate();
        eyeStrike = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_black_24dp).mutate();

        caidat();
    }

    private void caidat() {
        setInputType(InputType.TYPE_CLASS_TEXT | (visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables = getCompoundDrawables();
        drawable = useStrike && !visible ? eyeStrike : eye;
        drawable.setAlpha(ALPHA);
        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawable, drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // bắt sự kiện người dùng click vào hình con mắt ( lấy tọa độ x edittext - width của hình)
        if(event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= (getRight() - drawable.getBounds().width())) {
            visible = !visible;
            caidat();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
