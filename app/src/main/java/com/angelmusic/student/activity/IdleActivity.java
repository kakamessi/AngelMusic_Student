package com.angelmusic.student.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.angelmusic.student.R;
import com.angelmusic.student.base.BaseActivity;

import butterknife.BindView;

public class IdleActivity extends BaseActivity {

    @BindView(R.id.iv_yinfu_bg_ll)
    LinearLayout ivYinfuBgLl;
    @BindView(R.id.white_key_ll)
    LinearLayout whiteKeyLl;
    @BindView(R.id.black_key_ll)
    LinearLayout blackKeyLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setBlackKeyBgColor(3,Color.RED);
//        setWhiteKeyBgColor(4,Color.RED);
//        setYinfuBgColor(5,Color.BLUE);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.layout_yuepu3;
    }

    @Override
    protected void setTAG() {
        TAG = "==IdleActivity==";
    }

    /**
     * 设置音符背景颜色
     * 方法：通过设置背景图片
     *
     * @param position 第几个音符
     * @param color    颜色 Color.RED&&Color.Blue
     */
    private void setYinfuBgColor(int position, int color) {
        int childCount = ivYinfuBgLl.getChildCount();
        if (position < childCount) {
            for (int i = 0; i < childCount; i++) {
                if (i == position) {
                    if (color == Color.RED) {
                        ivYinfuBgLl.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (color == Color.BLUE) {
                        ivYinfuBgLl.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLl.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
        } else {
            Toast.makeText(this, "超出音符个数", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置白色按键的颜色
     * 方法：通过改变背景图片颜色
     *
     * @param position 第几个按键
     * @param color    颜色 Color.RED&&Color.BLUE
     */
    private void setWhiteKeyBgColor(int position, int color) {
        int childCount = whiteKeyLl.getChildCount();
        if (position < childCount) {
            for (int i = 0; i < childCount; i++) {
                if (i == position) {
                    if (color == Color.RED) {
                        ((ImageView) whiteKeyLl.getChildAt(i)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFFFB5555));
                    } else if (color == Color.BLUE) {
                        ((ImageView) whiteKeyLl.getChildAt(i)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFF34B4FF));
                    }
                } else {
                    ((ImageView) whiteKeyLl.getChildAt(i)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, Color.WHITE));
                }
            }
        } else {
            Toast.makeText(this, "超出按键个数", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置黑色按键的颜色
     * 方法：通过设置背景图片
     * 注意：使用时需要将style文件中的white_key_style中的图片隐藏掉
     *
     * @param position 第几个按键,不需要设置某个按键颜色时候传入-1
     * @param color    颜色 Color.RED&&Color.BLUE 默认：Color.BLACK
     */
    private void setBlackKeyBgColor(int position, int color) {
        int childCount = blackKeyLl.getChildCount();
        if (position < childCount) {
            for (int i = 0; i < childCount; i++) {
                if (i == position) {
                    if (color == Color.RED) {
                        blackKeyLl.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_key_righthand);//右手黑键
                    } else if (color == Color.BLUE) {
                        blackKeyLl.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_key_lefthand);//左手黑键
                    }
                } else {
                    blackKeyLl.getChildAt(i).setBackgroundResource(R.mipmap.kc_black_key);//黑键
                }
            }
        } else {
            Toast.makeText(this, "超出按键个数", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 改变图片颜色
     */
    private Drawable getTintPic(Context context, int image, int color) {
        Drawable drawable = ContextCompat.getDrawable(context, image);
        Drawable.ConstantState constantState = drawable.getConstantState();
        Drawable newDrawable = DrawableCompat.wrap(constantState == null ? drawable : constantState.newDrawable()).mutate();
        DrawableCompat.setTint(newDrawable, color);
        return newDrawable;
    }
}
