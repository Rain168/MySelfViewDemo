package com.study.longl.myselfviewdemo.AuditProgress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.study.longl.myselfviewdemo.R;

import java.util.ArrayList;

/**
 * Created by longl on 2018/12/5.
 * 自定义进度流程展示控件
 * 重封装，易用性，可扩展性
 */

public class AuditProgressViewTwo extends View {

    private int allCount;           //所有进度数目，比如5个
    private int currentPosition;    //当前进度所在位置，比如在第3个进度
    private int lineWidth = 100;
    private int lineCompleteColor;

    private TextPaint textPaint;    //写文字的画笔
    private Paint bitmapPaint;      //画图片的画笔
    private Paint linePaint;        //画线的画笔

    private Rect textWidths;        //承接文字宽度的矩形

    private int completeColor = Color.parseColor("#FF0000");      //完成颜色，主要用于文字
    private int notCompleteColor = Color.parseColor("#757575");   //未完成颜色，主要用于文字

    private ArrayList<Bitmap> completeBitmap = new ArrayList<>();        //完成的Bitmap
    private ArrayList<Bitmap> notCompleteBitmap = new ArrayList<>();     //未完成的Bitmap

    private int notCompleteImageArray[] = {R.drawable.audit_uncomplete, R.drawable.audit_uncomplete, R.drawable.audit_uncomplete, R.drawable.audit_uncomplete, R.drawable.audit_uncomplete};
    private int completeImageArray[] = {R.drawable.audit_complete, R.drawable.audit_complete, R.drawable.audit_complete, R.drawable.audit_complete, R.drawable.audit_complete};
    private String text[] = {"步骤一", "步骤二", "步骤三", "步骤四", "步骤五"};

    public AuditProgressViewTwo(Context context) {
        this(context, null);
    }

    public AuditProgressViewTwo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuditProgressViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AuditProgressViewTwo);
        allCount = typedArray.getInteger(R.styleable.AuditProgressViewTwo_all_count, 5);
        currentPosition = typedArray.getInteger(R.styleable.AuditProgressViewTwo_current_position, 4);
        completeColor = typedArray.getInteger(R.styleable.AuditProgressViewTwo_complete_color, Color.BLACK);
        notCompleteColor = typedArray.getInteger(R.styleable.AuditProgressViewTwo_not_complete_color, Color.GRAY);
        lineCompleteColor = typedArray.getInteger(R.styleable.AuditProgressViewTwo_line_complete_color, Color.BLACK);
        typedArray.recycle();

        if (allCount > completeImageArray.length) {
            throw new RuntimeException(getClass().getName() + "资源数组图标个数不能小于全部流程个数");
        }

        textWidths = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        textPaint = new TextPaint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(notCompleteColor);
        textPaint.setTextSize(30);

        bitmapPaint = new Paint();
        bitmapPaint.setStyle(Paint.Style.FILL);
        bitmapPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(completeColor);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setStrokeWidth(1.5f);

        completeBitmap.clear();
        notCompleteBitmap.clear();
        for (int i = 0; i < completeImageArray.length; i++) {
            completeBitmap.add(i, BitmapFactory.decodeResource(getResources(), completeImageArray[i]));
        }
        for (int i = 0; i < notCompleteImageArray.length; i++) {
            notCompleteBitmap.add(i, BitmapFactory.decodeResource(getResources(), notCompleteImageArray[i]));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < allCount; i++) {
            textPaint.getTextBounds(text[i], 0, text[i].length(), textWidths);
            int wid = (textWidths.width() / 2 - completeBitmap.get(i).getWidth() / 2);
            if (i < currentPosition) {
                canvas.drawBitmap(completeBitmap.get(i), i * 2 * 80 + wid, 20, bitmapPaint);
                linePaint.setColor(lineCompleteColor);
                textPaint.setColor(completeColor);
            } else {
                canvas.drawBitmap(notCompleteBitmap.get(i), i * 2 * 80 + wid, 20, bitmapPaint);
                linePaint.setColor(notCompleteColor);
                textPaint.setColor(notCompleteColor);
            }
            canvas.drawText(text[i], i * 2 * 80, completeBitmap.get(i).getHeight() + 70, textPaint);
            if (i > 0) {
                canvas.drawLine((completeBitmap.get(i).getWidth() + lineWidth) * i - lineWidth, 20 + completeBitmap.get(i).getHeight() / 2, (completeBitmap.get(i).getWidth() + lineWidth) * i, 20 + completeBitmap.get(i).getHeight() / 2, linePaint);
            }
        }
    }

    /**
     * 设置完成进度的图片数组
     *
     * @param complete 数据源
     */
    public void setCompleteImageSource(int[] complete) {
        this.completeImageArray = complete;
    }

    /**
     * 设置未完成进度的图片数组
     *
     * @param notComplete 数据源
     */
    public void setNotCompleteImageSource(int[] notComplete) {
        this.notCompleteImageArray = notComplete;
    }

    /**
     * 设置文字描述数组
     *
     * @param textSource 数据源
     */
    public void setTextSource(String[] textSource) {
        this.text = textSource;
    }
}