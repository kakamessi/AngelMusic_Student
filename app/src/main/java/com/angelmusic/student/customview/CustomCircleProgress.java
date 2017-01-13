package com.angelmusic.student.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.angelmusic.student.R;

/**
 * 自定义View，圆形进度条
 */
public class CustomCircleProgress extends ProgressBar {

    //定义一些属性常量
    private static final int PROGRESS_DEFAULT_COLOR = 0xFF8ab609;//默认圆(边框)的颜色
    private static final int PROGRESS_REACHED_COLOR = 0XFF7aa300;//进度条的颜色
    private static final int CENTER_TRIANGLE_COLOR = 0xFF8ab609;//中间三角形颜色
    private static final int CENTER_SQUARE_COLOR = 0xFF8ab609;//中间方形或者两条数线颜色
    private static final int PROGRESS_REACHED_HEIGHT = 4;//进度条的高度
    private static final int PROGRESS_DEFAULT_HEIGHT = 2;//默认圆的高度
    private static final int PROGRESS_RADIUS = 30;//圆的半径

    //View的当前状态，默认为未开始
    private Status mStatus = Status.Start;
    //画笔
    private Paint mPaint;
    //默认圆(边框)的颜色
    private int mDefaultColor = PROGRESS_DEFAULT_COLOR;
    //进度条的颜色
    private int mReachedColor = PROGRESS_REACHED_COLOR;
    //进度条的高度
    private int mReachedHeight = dp2px(PROGRESS_REACHED_HEIGHT);
    //默认圆(边框)的高度
    private int mDefaultHeight = dp2px(PROGRESS_DEFAULT_HEIGHT);
    //圆的半径
    private int mRadius = dp2px(PROGRESS_RADIUS);
    //通过path路径去绘制三角形
    private Path mPath;
    //三角形的边长
    private int triangleLength;


    public CustomCircleProgress(Context context) {
        this(context, null);
    }

    public CustomCircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性的值
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCircleProgress);
        //默认圆的颜色
        mDefaultColor = array.getColor(R.styleable.CustomCircleProgress_progress_default_color, PROGRESS_DEFAULT_COLOR);
        //进度条的颜色
        mReachedColor = array.getColor(R.styleable.CustomCircleProgress_progress_reached_color, PROGRESS_REACHED_COLOR);
        //默认圆的高度
        mDefaultHeight = (int) array.getDimension(R.styleable.CustomCircleProgress_progress_default_height, mDefaultHeight);
        //进度条的高度
        mReachedHeight = (int) array.getDimension(R.styleable.CustomCircleProgress_progress_reached_height, mReachedHeight);
        //圆的半径
        mRadius = (int) array.getDimension(R.styleable.CustomCircleProgress_circle_radius, mRadius);
        //最后不要忘了回收 TypedArray
        array.recycle();

        //设置画笔（new画笔的操作一般不要放在onDraw方法中，因为在绘制的过程中onDraw方法会被多次调用）
        setPaint();

        //通过path路径绘制三角形
        mPath = new Path();
        //让三角形的长度等于圆的半径(等边三角形)
        triangleLength = mRadius;
        //绘制三角形，首先我们需要确定三个点的坐标
        float firstX = (float) ((mRadius * 2 - Math.sqrt(3.0) / 2 * mRadius) / 2);//左上角第一个点的横坐标，根据勾股定律,Math.sqrt(3.0)表示对3开方
        //为了显示的好看些，这里微调下firstX横坐标
        float mFirstX = (float) (firstX + firstX * 0.2);
        float firstY = mRadius - triangleLength / 2;
        //同理，依次可得出第二个点(左下角)第三个点的坐标
        float secondX = mFirstX;
        float secondY = (float) (mRadius + triangleLength / 2);
        float thirdX = (float) (mFirstX + Math.sqrt(3.0) / 2 * mRadius);
        float thirdY = mRadius;
        mPath.moveTo(mFirstX, firstY);
        mPath.lineTo(secondX, secondY);
        mPath.lineTo(thirdX, thirdY);
        mPath.lineTo(mFirstX, firstY);
    }

    private void setPaint() {
        mPaint = new Paint();
        //下面是设置画笔的一些属性
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动，绘制出来的图要更加柔和清晰
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);//设置填充样式
        /**
         *  Paint.Style.FILL    :填充内部
         *  Paint.Style.FILL_AND_STROKE  ：填充内部和描边
         *  Paint.Style.STROKE  ：仅描边
         */
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔笔刷类型


    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int paintHeight = Math.max(mReachedHeight, mDefaultHeight);//比较两数，取最大值

        if (heightMode != MeasureSpec.EXACTLY) {
            int exceptHeight = getPaddingTop() + getPaddingBottom() + mRadius * 2 + paintHeight;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
        }
        if (widthMode != MeasureSpec.EXACTLY) {
            int exceptWidth = getPaddingLeft() + getPaddingRight() + mRadius * 2 + paintHeight;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (mStatus == Status.Start) {
            //绘制下载的图标
            mPaint.setFilterBitmap(true);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.down_all_btn_normal);
            Rect rectFSrc = new Rect(0, 0, mRadius, mRadius);
            Rect rectFDst = new Rect(mRadius / 2, mRadius / 2, mRadius * 2, mRadius * 2);
            canvas.drawBitmap(bitmap, rectFSrc, rectFDst, mPaint);
        } else if (mStatus == Status.End) {
            //绘制垃圾桶
            mPaint.setFilterBitmap(true);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.delete_icon);
            Rect rectFSrc = new Rect(0, 0, mRadius, mRadius);
            Rect rectFDst = new Rect(mRadius / 2, mRadius / 2, mRadius * 2, mRadius * 2);
            canvas.drawBitmap(bitmap, rectFSrc, rectFDst, mPaint);
        } else {
            canvas.translate(getPaddingLeft(), getPaddingTop());
            mPaint.setStyle(Paint.Style.STROKE);
            //画默认圆(边框)的一些设置
            mPaint.setColor(mDefaultColor);
            mPaint.setStrokeWidth(mDefaultHeight);
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

            //画进度条的一些设置
            mPaint.setColor(mReachedColor);
            mPaint.setStrokeWidth(mReachedHeight);
            //根据进度绘制圆弧
            float sweepAngle = getProgress() * 1.0f / getMax() * 360;
            canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), -90, sweepAngle, false, mPaint);//drawArc：绘制圆弧

            if (mStatus == Status.Pause) {//开始后暂停，画笔填充三角形
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(CENTER_TRIANGLE_COLOR);
                canvas.drawPath(mPath, mPaint);
            } else if (mStatus == Status.Loading) {//正在下载状态,画方块
                mPaint.setColor(CENTER_SQUARE_COLOR);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawRect(mRadius * 2 / 3, mRadius * 2 / 3, 2 * mRadius - (mRadius * 2 / 3), 2 * mRadius - (mRadius * 2 / 3), mPaint);
            }
        }
        canvas.restore();
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    //当前view显示的状态
    public enum Status {
        Start,//初始未下载
        Pause,//下载暂停中
        Loading,//正在下载中
        End//下载完成
    }

    //设置Status的set/get方法
    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        this.mStatus = status;
        invalidate();
    }
}
