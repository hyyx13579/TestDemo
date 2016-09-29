package com.example.hyyx.testdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.example.hyyx.testdemo.R;


/**
 * Created by ${hyyx} on 2016/6/30 0030.
 */
public class BarGraphView extends View {


    private final String TAG = BarGraphView.class.getName();
    //画笔
    private Paint mPaint;
    //虚线画笔
    private Paint paintDotted;
    //红线画笔
    private Paint paintRedLine;
    //散点图画笔
    private Paint painPoint;

    //标题
    private String title;
    //标题颜色
    private int titleColor;
    //标题大小
    private float titleSize;
    //X坐标轴最大值
    private float maxAxisValueX = 900;
    //X坐标轴刻度线数量
    private int axisDivideSizeX = 9;
    //Y坐标轴最大值
    private float maxAxisValueY = 700;
    //Y坐标轴刻度线数量
    private int axisDivideSizeY = 20;
    //视图宽度
    private float width;
    //视图高度
    private float height;
    //坐标原点位置
    private final int originX = 30;
    private final int originY = 10;


    //Type的类型值
    public static final int TYPE_TEMP = 1;
    public static final int TYPE_BLOODPRESURE = 2;
    public static final int TYPE_PULSE = 3;
    public static final int TYPE_BLOODSUGAR = 4;
    public static final int TYPE_BREATHE = 5;

    //判读柱形图还是散点图的标记
    private int DRAWSHAPE = 0;

    public static final int DRAWPOINT = 100;
    public static final int DRAWColumn = 99;
    public static final int DRAWBROKRNLINE = 98;

    //x轴数据
    private String[] xLabels;
    //y轴数据
    private int[] yLabels;
    //柱形图数据
    private double[] tempData;
    //散点图数据
    private double[] bloodPresureDataLow;
    private double[] bloodPresureDataHeigt;
    //折线图数据
    private double[] brokenLineData;


    //红线1
    private int reddataOne;
    //红线2
    private int reddataTwo;
    private Path path;


    public BarGraphView(Context context) {
        super(context, null);
        //创建画笔
        mPaint = new Paint();
        paintDotted = new Paint();
        paintRedLine = new Paint();
        painPoint = new Paint();
    }

    public BarGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //创建画笔
        mPaint = new Paint();
        paintDotted = new Paint();
        paintRedLine = new Paint();
        painPoint = new Paint();

    }

    /**
     * 设置X轴的最大值及刻度线数量（包括0坐标刻度）
     *
     * @param maxValue   X轴的最大值
     * @param divideSize 刻度线数量
     */
    public void setAxisX(float maxValue, int divideSize) {
        maxAxisValueX = maxValue;
        axisDivideSizeX = divideSize;
    }

    /**
     * 设置Y轴的最大值及刻度线数量（包括0坐标刻度）
     *
     * @param maxValue   Y轴的最大值
     * @param divideSize 刻度线数量
     */
    public void setAxisY(float maxValue, int divideSize) {
        maxAxisValueY = maxValue;
        axisDivideSizeY = divideSize;
    }


    /**
     * 设置X轴的数值
     *
     * @param xLabels X轴上各点的数据
     */


    public void setXdataInfo(String[] xLabels) {
        this.xLabels = xLabels;
    }


    /**
     * 设置变的Y轴数据
     *
     * @param yLabels
     */
    public void setChangeableYdataInfo(int[] yLabels) {
        this.yLabels = yLabels;
    }

    /**
     * 设置view自带的Y轴数据,包括体温,血压,脉搏,血糖,呼吸的五种标准化数值
     *
     * @param type 5种type类型规定不同的标准化数值
     */


    public void setDefaultYdataInfo(int type) {
        switch (type) {
            case TYPE_TEMP:
                this.yLabels = new int[]{32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43};
                setAxisY(43, 12);
                break;

            case TYPE_BLOODPRESURE:
                this.yLabels = new int[]{40, 60, 80, 100, 120, 140, 160, 180, 200};
                setAxisY(200, 9);
                break;
            case TYPE_PULSE:
                this.yLabels = new int[]{40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150};
                setAxisY(150, 12);
                break;
            case TYPE_BLOODSUGAR:
                this.yLabels = new int[]{60, 80, 100, 120, 140, 160, 180, 200, 220};
                setAxisY(220, 9);
                break;
            case TYPE_BREATHE:
                this.yLabels = new int[]{5, 10, 15, 20, 25, 30, 35, 40};
                setAxisY(40, 8);
                break;
        }


    }


    /**
     * 设置柱形图图表的数据
     *
     * @param tempData
     */
    public void setBarInfo(double[] tempData) {
        this.tempData = tempData;
        DRAWSHAPE = DRAWColumn;
    }

    /**
     * @param brokenLineData
     */
    public void setBrokenLineInfo(double[] brokenLineData) {
        this.brokenLineData = brokenLineData;
        DRAWSHAPE = DRAWBROKRNLINE;
    }


    /**
     * 设置双数据散点图数据
     *
     * @param bloodPresureDataLow
     * @param bloodPresureDataHeigt
     */
    public void setPointInfo(double[] bloodPresureDataLow, double[] bloodPresureDataHeigt) {
        this.bloodPresureDataLow = bloodPresureDataLow;
        this.bloodPresureDataHeigt = bloodPresureDataHeigt;
        DRAWSHAPE = DRAWPOINT;


    }


    /**
     * 设置红线
     *
     * @param reddataOne 红线1
     * @param reddataTwo 红线2
     */

    public void setRedLine(int reddataOne, int reddataTwo) {
        this.reddataOne = reddataOne;
        this.reddataTwo = reddataTwo;

    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        width = MeasureSpec.getSize(widthMeasureSpec) - 200;
//        height = MeasureSpec.getSize(heightMeasureSpec) - 800;

        width = MeasureSpec.getSize(widthMeasureSpec) - 30;
        height = MeasureSpec.getSize(heightMeasureSpec) - 30;
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawAxisX(canvas, mPaint);
        drawAxisY(canvas, mPaint);
        drawAxisScaleMarkX(canvas, mPaint);
        drawAxisScaleMarkY(canvas, mPaint);
        drawAxisScaleMarkValueX(canvas, mPaint);
        drawAxisScaleMarkValueY(canvas, mPaint);
        switch (DRAWSHAPE) {
            case DRAWBROKRNLINE:
                drawBrokrnline(canvas, painPoint);
                break;
            case DRAWColumn:
                drawColumn(canvas, mPaint);
                break;
            case DRAWPOINT:
                drawPoint(canvas, painPoint);
                break;

        }

        drawTitle(canvas, mPaint);
        drawDottedMarkY(canvas, paintDotted);
        drawRedLineMarkYOne(canvas, paintRedLine);
        drawRedLineMarkYTwo(canvas, paintRedLine);
    }

    /**
     * 绘制横坐标轴（X轴）
     *
     * @param canvas
     * @param paint
     */
    private void drawAxisX(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        //设置画笔宽度
        paint.setStrokeWidth(1);
        //设置画笔抗锯齿
        paint.setAntiAlias(true);
        //画横轴(X)
        canvas.drawLine(originX, originY + height, originX + width, originY + height, paint);
    }

    /**
     * 绘制纵坐标轴(Y轴)
     *
     * @param canvas
     * @param paint
     */
    private void drawAxisY(Canvas canvas, Paint paint) {
        //画竖轴(Y)
        canvas.drawLine(originX, originY, originX, originY + height, paint);//参数说明：起始点左边x,y，终点坐标x,y，画笔
    }


    /**
     * 绘制横坐标轴刻度线(X轴)
     *
     * @param canvas
     * @param paint
     */
    private void drawAxisScaleMarkX(Canvas canvas, Paint paint) {
        float cellWidth = width / axisDivideSizeX;
        for (int i = 0; i < axisDivideSizeX - 1; i++) {
            canvas.drawLine(cellWidth * (i + 1) + originX, originY + height - 10, cellWidth * (i + 1) + originX, originY + height, paint);
        }
    }

    /**
     * 绘制纵坐标轴刻度线(Y轴)
     *
     * @param canvas
     * @param paint
     */
    private void drawAxisScaleMarkY(Canvas canvas, Paint paint) {
        float cellHeight = height / axisDivideSizeY;
        for (int i = 0; i < axisDivideSizeY - 1; i++) {
            canvas.drawLine(originX, (originY + cellHeight * (i + 1)), originX + 10, (originY + cellHeight * (i + 1)), paint);
        }
    }

    /**
     * 绘制纵向虚线(Y轴)
     */
    private void drawDottedMarkY(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getContext().getResources().getColor(R.color.border_color));
        float cellHeight = height / axisDivideSizeY;
        for (int i = 0; i < axisDivideSizeY - 1; i++) {

            Path path = new Path();
            path.moveTo(originX, (originY + cellHeight * (i + 1)));
            path.lineTo(originX + width, (originY + cellHeight * (i + 1)));
            PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
            paint.setPathEffect(effects);
            canvas.drawPath(path, paint);

        }
    }

    /**
     * 绘制指定的纵向红线1(Y轴)
     */
    private void drawRedLineMarkYOne(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        float cellHeight = height / axisDivideSizeY;
        int i1 = yLabels[yLabels.length - 1] - yLabels[0];
        float v2 = i1 / (cellHeight * (yLabels.length - 1));
        double v = reddataOne - yLabels[0];
        double v1 = v / v2;
        float leftTopY = (originY + height) - ((float) v1);
        canvas.drawLine(originX, leftTopY, originX + width, leftTopY, paint);
    }

    /**
     * 绘制指定的纵向红线2(Y轴)
     */
    private void drawRedLineMarkYTwo(Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        float cellHeight = height / axisDivideSizeY;
        int i1 = yLabels[yLabels.length - 1] - yLabels[0];
        float v2 = i1 / (cellHeight * (yLabels.length - 1));
        double v = reddataTwo - yLabels[0];
        double v1 = v / v2;
        float leftTopY = (originY + height) - ((float) v1);
        canvas.drawLine(originX, leftTopY, originX + width, leftTopY, paint);
    }


    /**
     * 绘制横坐标轴刻度值(X轴)
     *
     * @param canvas
     * @param paint
     */
    private void drawAxisScaleMarkValueX(Canvas canvas, Paint paint) {
        //设置画笔绘制文字的属性
        paint.setColor(Color.GRAY);
        paint.setFakeBoldText(true);
        paint.setTextSize(16);

        float cellWidth = width / axisDivideSizeX;
        //float cellValue = maxAxisValueX / axisDivideSizeX;
        for (int i = 0; i < axisDivideSizeX; i++) {
            if (xLabels.length > i) {
                canvas.drawText(xLabels[i], (cellWidth * (i) + originX) + (cellWidth + originX) / 2, originY + height + 15, paint);
            } else {
                break;
            }

        }
    }


    /**
     * 绘制纵坐标轴刻度值(Y轴)
     *
     * @param canvas
     * @param paint
     */
    private void drawAxisScaleMarkValueY(Canvas canvas, Paint paint) {
        float cellHeight = height / axisDivideSizeY;
        float cellValue = maxAxisValueY / axisDivideSizeY;
        for (int i = 1; i < axisDivideSizeY; i++) {
            canvas.drawText("" + yLabels[i], originX - 30, (originY + height) - cellHeight * i, paint);
        }
    }


    /**
     * 绘制散点图
     *
     * @param canvas
     * @param paint
     */
    private void drawPoint(Canvas canvas, Paint paint) {


        paint.setColor(getContext().getResources().getColor(R.color.titlecolor));
        paint.setAntiAlias(true);


        if (bloodPresureDataLow == null && bloodPresureDataHeigt == null)
            return;
        float cellWidth = width / axisDivideSizeX;
        float cellHeight = height / axisDivideSizeY;

        for (int i = 0; i < bloodPresureDataLow.length; i++) {

            //限制一屏显示8条数据
            if (i >= 8) {
                break;
            } else {

                float cx = (cellWidth * (i + 1) + originX);
                int i1 = yLabels[yLabels.length - 1] - yLabels[0];
                float v2 = i1 / (cellHeight * (yLabels.length - 1));
                double v = bloodPresureDataLow[i] - yLabels[0];
                double v1 = v / v2;
                float cyLow = (originY + height) - ((float) v1);

                canvas.drawCircle(cx, cyLow, 7, paint);


                // canvas.drawRect(leftX, leftTopY, rightX, bottmoY, mPaint);//左上角x,y右下角x,y，画笔
            }
        }

        for (int i = 0; i < bloodPresureDataHeigt.length; i++) {

            //限制一屏显示8条数据
            if (i >= 8) {
                break;
            } else {

                float cx = (cellWidth * (i + 1) + originX);
                int i1 = yLabels[yLabels.length - 1] - yLabels[0];

                float v2 = i1 / (cellHeight * (yLabels.length - 1));
                double v = bloodPresureDataHeigt[i] - yLabels[0];
                double v1 = v / v2;


                float cy = (originY + height) - ((float) v1);


                canvas.drawCircle(cx, cy, 7, paint);

                // canvas.drawRect(leftX, leftTopY, rightX, bottmoY, mPaint);//左上角x,y右下角x,y，画笔
            }
        }


    }


    /**
     * 绘制柱状图
     *
     * @param canvas
     * @param paint
     */
    private void drawColumn(Canvas canvas, Paint paint) {
        if (tempData == null)
            return;
        int j = 0;
        float cellWidth = width / axisDivideSizeX;
        float cellHeight = height / axisDivideSizeY;

        for (int i = 0; i < tempData.length; i++) {

            //限制一屏显示8条数据
            if (i >= 8) {
                break;
            } else {
                paint.setColor(getContext().getResources().getColor(R.color.titlecolor));


                float leftX = (cellWidth * (i + 1) + originX) - (cellWidth / 4);
                float rightX = (cellWidth * (i + 1) + originX) + (cellWidth / 4);

                int i1 = yLabels[yLabels.length - 1] - yLabels[0];


                float v2 = i1 / (cellHeight * (yLabels.length - 1));
                double v = tempData[i] - yLabels[0];
                double v1 = v / v2;


                float leftTopY = (originY + height) - ((float) v1);

                float bottmoY = originY + height;
                canvas.drawRect(leftX, leftTopY, rightX, bottmoY, mPaint);//左上角x,y右下角x,y，画笔
            }
        }
    }


    /**
     * 绘制折线图
     *
     * @param canvas
     * @param paint
     */
    private void drawBrokrnline(Canvas canvas, Paint paint) {


        paint.setColor(getContext().getResources().getColor(R.color.titlecolor));
        paint.setAntiAlias(true);


        if (brokenLineData == null)
            return;
        float cellWidth = width / axisDivideSizeX;
        float cellHeight = height / axisDivideSizeY;
        path = new Path();

        for (int i = 0; i < brokenLineData.length; i++) {

            //限制一屏显示8条数据
            if (i >= 8) {
                break;
            } else {

                float cx = (cellWidth * (i + 1) + originX);
                int i1 = yLabels[yLabels.length - 1] - yLabels[0];
                float v2 = i1 / (cellHeight * (yLabels.length - 1));
                double v = brokenLineData[i] - yLabels[0];
                double v1 = v / v2;
                float cyLow = (originY + height) - ((float) v1);

                canvas.drawCircle(cx, cyLow, 7, paint);
                if (i == 0) {
                    path.moveTo(cx, cyLow);
                } else {
                    path.lineTo(cx, cyLow);
                }

                // canvas.drawRect(leftX, leftTopY, rightX, bottmoY, mPaint);//左上角x,y右下角x,y，画笔
            }
        }
        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setColor(getContext().getResources().getColor(R.color.titlecolor));
        paint1.setAntiAlias(true);
        canvas.drawPath(path, paint1);


    }


    /**
     * 绘制标题
     *
     * @param canvas
     * @param paint
     */
    private void drawTitle(Canvas canvas, Paint paint) {

        //画标题
        if (title != null) {
            //设置画笔绘制文字的属性
            mPaint.setColor(titleColor);
            mPaint.setTextSize(titleSize);
            mPaint.setFakeBoldText(true);
            canvas.drawText(title, 300, originY + 150, paint);
        }
    }
}
