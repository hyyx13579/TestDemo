/*
 *  Android Wheel Control.
 *  https://code.google.com/p/android-wheel/
 *  
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.hyyx.testdemo.view.wheelView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.util.LinkedList;
import java.util.List;

/**
 * Numeric wheel view.
 * 
 * @author Yuri Kanivets
 */
public class WheelView extends View {

    private Context mContext;

    /** Scrolling duration */
    private static final int SCROLLING_DURATION = 400;

    /** Minimum delta for scrolling */
    private static final int MIN_DELTA_FOR_SCROLLING = 1;

    /** Current value & label text color */
    private static final int VALUE_TEXT_COLOR = 0xFF505050;

    /** Items text color */
    private static final int ITEMS_TEXT_COLOR = 0xFF797979;

    private int ADDITIONAL_ITEM_HEIGHT = 15;

    /** Text size */
    private int TEXT_SIZE = 30;

    /** Top and bottom items offset (to hide that) */
    private int ITEM_OFFSET = TEXT_SIZE / 5;

    private int ITEM_TEXT_SIZE = TEXT_SIZE / 2;

    /** Additional width for items layout */
    private int ADDITIONAL_ITEMS_SPACE = 10;

    /** Label offset */
    private int LABEL_OFFSET = 8;

    private float RATE_CENTER_BIGGER = 1.5f;

    /** Left and right padding value */
    private int PADDING = 30;

    /** Default count of visible items */
    private static final int DEF_VISIBLE_ITEMS = 5;

    // Wheel Values
    private WheelAdapter adapter = null;

    private int currentItem = 0;

    // Widths
    private int itemsWidth = 0;

    private int labelWidth = 0;

    // Count of visible items
    private int visibleItems = DEF_VISIBLE_ITEMS;

    // Item height
    private int itemHeight = 0;

    // Text paints
    private TextPaint itemsPaint;

    private TextPaint valuePaint;

    private TextPaint labPaint;

    private Typeface face;

    // Layouts
    private StaticLayout itemsLayout;

    private StaticLayout labelLayout;

    private StaticLayout valueLayout;

    private StaticLayout topItemLayout;

    private StaticLayout bottomItemLayout;

    private String label;

    private boolean isScrollingPerformed;

    private int scrollingOffset;

    private GestureDetector gestureDetector;

    private Scroller scroller;

    private int lastScrollY;

    boolean isCyclic = false;

    private List<OnWheelChangedListener> changingListeners = new LinkedList<OnWheelChangedListener>();

    private List<OnWheelScrollListener> scrollingListeners = new LinkedList<OnWheelScrollListener>();

    private Typeface mTypeface;

    /**
     * Constructor
     */
    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        setDefult(context);
    }

    /**
     * Constructor
     */
    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setDefult(context);
    }

    /**
     * Constructor
     */
    public WheelView(Context context) {
        super(context);
        this.mContext = context;
        setDefult(context);
    }

    private void setDefult(Context context) {
        mTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/number.ttf");
        gestureDetector = new GestureDetector(context, gestureListener);
        gestureDetector.setIsLongpressEnabled(true);
        scroller = new Scroller(context);
    }

    /**
     * Initializes class data
     * 
     */
    private void initData(int h, int w) {
        // 一下用于修改默认值
        // TODO
        if (h == 0) {
            ADDITIONAL_ITEM_HEIGHT = w / 5;
            TEXT_SIZE = w /8;
        } else {
            ADDITIONAL_ITEM_HEIGHT = h / 20;
            TEXT_SIZE = h / 30;
        }
        ADDITIONAL_ITEMS_SPACE = w;
        LABEL_OFFSET = w / 10;
        PADDING = dip2px(mContext, 5f);
        ITEM_OFFSET = -(int) (TEXT_SIZE);

    }

    /**
     * Gets wheel adapter
     * 
     * @return the adapter
     */
    public WheelAdapter getAdapter() {
        return adapter;
    }

    /**
     * Sets wheel adapter
     * 
     * @param adapter the new wheel adapter
     */
    public void setAdapter(WheelAdapter adapter) {
        this.adapter = adapter;
        invalidateLayouts();
        invalidate();
    }

    /**
     * Set the the specified scrolling interpolator
     * 
     * @param interpolator the interpolator
     */
    public void setInterpolator(Interpolator interpolator) {
        scroller.forceFinished(true);
        scroller = new Scroller(getContext(), interpolator);
    }

    /**
     * Gets count of visible items
     * 
     * @return the count of visible items
     */
    public int getVisibleItems() {
        return visibleItems;
    }

    /**
     * Sets count of visible items
     * 
     * @param count the new count
     */
    public void setVisibleItems(int count) {
        visibleItems = count;
        invalidate();
    }

    /**
     * Gets label
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets label
     * 
     * @param newLabel the label to set
     */
    public void setLabel(String newLabel) {
        if (label == null || !label.equals(newLabel)) {
            label = newLabel;
            labelLayout = null;
            invalidate();
        }
    }

    /**
     * Adds wheel changing listener
     * 
     * @param listener the listener
     */
    public void addChangingListener(OnWheelChangedListener listener) {
        changingListeners.add(listener);
    }

    /**
     * Removes wheel changing listener
     * 
     * @param listener the listener
     */
    public void removeChangingListener(OnWheelChangedListener listener) {
        changingListeners.remove(listener);
    }

    /**
     * Notifies changing listeners
     * 
     * @param oldValue the old wheel value
     * @param newValue the new wheel value
     */
    protected void notifyChangingListeners(int oldValue, int newValue) {
        for (OnWheelChangedListener listener : changingListeners) {
            listener.onChanged(this, oldValue, newValue);
        }
    }

    /**
     * Adds wheel scrolling listener
     * 
     * @param listener the listener
     */
    public void addScrollingListener(OnWheelScrollListener listener) {
        scrollingListeners.add(listener);
    }

    /**
     * Removes wheel scrolling listener
     * 
     * @param listener the listener
     */
    public void removeScrollingListener(OnWheelScrollListener listener) {
        scrollingListeners.remove(listener);
    }

    /**
     * Notifies listeners about starting scrolling
     */
    protected void notifyScrollingListenersAboutStart() {
        for (OnWheelScrollListener listener : scrollingListeners) {
            listener.onScrollingStarted(this);
        }
    }

    /**
     * Notifies listeners about ending scrolling
     */
    protected void notifyScrollingListenersAboutEnd() {
        for (OnWheelScrollListener listener : scrollingListeners) {
            listener.onScrollingFinished(this);
        }
    }

    /**
     * Gets current value
     * 
     * @return the current value
     */
    public int getCurrentItem() {
        return currentItem;
    }

    /**
     * Sets the current item. Does nothing when index is wrong.
     * 
     * @param index the item index
     * @param animated the animation flag
     */
    public void setCurrentItem(int index, boolean animated) {
        if (adapter == null || adapter.getItemsCount() == 0) {
            return; // throw?
        }
        if (index < 0 || index >= adapter.getItemsCount()) {
            if (isCyclic) {
                while (index < 0) {
                    index += adapter.getItemsCount();
                }
                index %= adapter.getItemsCount();
            } else {
                return; // throw?
            }
        }
        if (index != currentItem) {
            if (animated) {
                scroll(index - currentItem, SCROLLING_DURATION);
            } else {
                invalidateLayouts();
                int old = currentItem;
                currentItem = index;
                notifyChangingListeners(old, currentItem);
                invalidate();
            }
        }
    }

    /**
     * Sets the current item w/o animation. Does nothing when index is wrong.
     * 
     * @param index the item index
     */
    public void setCurrentItem(int index) {
        setCurrentItem(index, false);
    }

    /**
     * Tests if wheel is cyclic. That means before the 1st item there is shown
     * the last one
     * 
     * @return true if wheel is cyclic
     */
    public boolean isCyclic() {
        return isCyclic;
    }

    /**
     * Set wheel cyclic flag
     * 
     * @param isCyclic the flag to set
     */
    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;

        invalidate();
        invalidateLayouts();
    }

    /**
     * Invalidates layouts
     */
    private void invalidateLayouts() {
        itemsLayout = null;
        valueLayout = null;
        topItemLayout = null;
        bottomItemLayout = null;
        scrollingOffset = 0;
    }

    /**
     * Initializes resources
     */
    private void initResourcesIfNecessary() {
        if (itemsPaint == null) {
            itemsPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
            itemsPaint.setTypeface(mTypeface);
            itemsPaint.setTextSize(TEXT_SIZE);
        }

        if (valuePaint == null) {
            valuePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG
                    | Paint.DITHER_FLAG);
            valuePaint.setTextSize(TEXT_SIZE);
            valuePaint.setTypeface(mTypeface);
            valuePaint.setShadowLayer(0.1f, 0, 0.1f, 0xFFC0C0C0);
        }
        if (labPaint == null) {
            labPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
            labPaint.setTypeface(mTypeface);
            labPaint.setTextSize(TEXT_SIZE);
        }
    }

    private void reserPaint() {
        if (itemsPaint != null) {
            itemsPaint.setColor(ITEMS_TEXT_COLOR);
            itemsPaint.setTextScaleX(1.0f);
        }
    }

    /**
     * Calculates desired height for layout
     * 
     * @param layout the source layout
     * @return the desired layout height
     */
    private int getDesiredHeight(Layout layout) {
        if (layout == null) {
            return 0;
        }

        int desired = getItemHeight() * visibleItems - ITEM_OFFSET * 2 - ADDITIONAL_ITEM_HEIGHT;

        // Check against our minimum height
        desired = Math.max(desired, getSuggestedMinimumHeight());

        return desired;
    }

    /**
     * Returns text item by index
     * 
     * @param index the item index
     * @return the item or null
     */
    private String getTextItem(int index) {
        if (adapter == null || adapter.getItemsCount() == 0) {
            return null;
        }
        int count = adapter.getItemsCount();
        if ((index < 0 || index >= count) && !isCyclic) {
            return null;
        } else {
            while (index < 0) {
                index = count + index;
            }
        }

        index %= count;
        return adapter.getItem(index);
    }

    /**
     * Builds text depending on current value
     * 
     * @param useCurrentValue
     * @return the text
     */
    private String buildText(boolean useCurrentValue) {
        StringBuilder itemsText = new StringBuilder();
        int addItems = visibleItems / 2 + 1;

        for (int i = currentItem - addItems; i <= currentItem + addItems; i++) {
            // if (useCurrentValue || i != currentItem) {//与下面不一样的是中间是否有重叠,并且
            // 这个加入useCurrentValue白屏的Bug
            if (i != currentItem) {
                if (i != currentItem + 1 && i != currentItem - 1) {
                    String text = getTextItem(i);
                    if (text != null) {
                        itemsText.append(text);
                    }
                }
            }
            if (i < currentItem + addItems) {
                itemsText.append("\n");
            }
        }

        return itemsText.toString();
    }

    /**
     * Returns the max item length that can be present
     * 
     * @return the max length
     */
    private int getMaxTextLength() {
        WheelAdapter adapter = getAdapter();
        if (adapter == null) {
            return 0;
        }

        int adapterLength = adapter.getMaximumLength();
        if (adapterLength > 0) {
            return adapterLength;
        }

        String maxText = null;
        int addItems = visibleItems / 2;
        for (int i = Math.max(currentItem - addItems, 0); i < Math.min(currentItem + visibleItems,
                adapter.getItemsCount()); i++) {
            String text = adapter.getItem(i);
            if (text != null && (maxText == null || maxText.length() < text.length())) {
                maxText = text;
            }
        }

        return maxText != null ? maxText.length() : 0;
    }

    /**
     * Returns height of wheel item
     * 
     * @return the item height
     */
    private int getItemHeight() {
        if (itemHeight != 0) {
            return itemHeight;
        } else if (itemsLayout != null && itemsLayout.getLineCount() > 2) {
            itemHeight = itemsLayout.getLineTop(2) - itemsLayout.getLineTop(1);
            return itemHeight;
        }

        return getHeight() / visibleItems;
    }

    /**
     * Calculates control width and creates text layouts
     * 
     * @param widthSize the input layout width
     * @param mode the layout mode
     * @return the calculated control width
     */
    private int calculateLayoutWidth(int widthSize, int mode) {
        initResourcesIfNecessary();

        int width = widthSize;
        int maxLength = getMaxTextLength();
        if (maxLength > 0) {
            float textWidth = (float) Math.ceil(Layout.getDesiredWidth("0", itemsPaint));
            itemsWidth = (int) (maxLength * textWidth);
        } else {
            itemsWidth = 0;
        }
        itemsWidth += ADDITIONAL_ITEMS_SPACE; // make it some more

        labelWidth = 0;
        if (label != null && label.length() > 0) {
            labelWidth = (int) Math.ceil(Layout.getDesiredWidth(label, valuePaint));
        }

        boolean recalculate = false;
        if (mode == MeasureSpec.EXACTLY) {
            width = widthSize;
            recalculate = true;
        } else {
            width = itemsWidth + labelWidth + 2 * PADDING;
            if (labelWidth > 0) {
                width += LABEL_OFFSET;
            }

            // Check against our minimum width
            width = Math.max(width, getSuggestedMinimumWidth());

            if (mode == MeasureSpec.AT_MOST && widthSize < width) {
                width = widthSize;
                recalculate = true;
            }
        }

        if (recalculate) {
            // recalculate width
            int pureWidth = width - LABEL_OFFSET - 2 * PADDING;
            if (pureWidth <= 0) {
                itemsWidth = labelWidth = 0;
            }
            if (labelWidth > 0) {
                double newWidthItems = (double) itemsWidth * pureWidth / (itemsWidth + labelWidth);
                itemsWidth = (int) newWidthItems;
                labelWidth = pureWidth - itemsWidth;
            } else {
                itemsWidth = pureWidth + LABEL_OFFSET; // no label
            }
        }

        if (itemsWidth > 0) {
            createLayouts(itemsWidth, labelWidth);
        }

        return width;
    }

    /**
     * Creates layouts
     * 
     * @param widthItems width of items layout
     * @param widthLabel width of label layout
     */
    private void createLayouts(int widthItems, int widthLabel) {
        labPaint.setColor(VALUE_TEXT_COLOR);
        if (itemsLayout == null || itemsLayout.getWidth() > widthItems) {
            itemsLayout = new StaticLayout(buildText(isScrollingPerformed), itemsPaint, widthItems,
                    widthLabel > 0 ? Layout.Alignment.ALIGN_CENTER : Layout.Alignment.ALIGN_CENTER,
                    1.5f, 0, false);
        } else {
            itemsLayout.increaseWidthTo(widthItems);
        }

        if (topItemLayout == null || topItemLayout.getWidth() > widthItems) {
            topItemLayout = new StaticLayout(getTextItem(currentItem - 1), itemsPaint, widthItems,
                    widthLabel > 0 ? Layout.Alignment.ALIGN_CENTER : Layout.Alignment.ALIGN_CENTER,
                    1.5f, 0, false);
        } else {
            topItemLayout.increaseWidthTo(widthItems);
        }

        if (bottomItemLayout == null || bottomItemLayout.getWidth() > widthItems) {
            bottomItemLayout = new StaticLayout(getTextItem(currentItem + 1), itemsPaint,
                    widthItems, widthLabel > 0 ? Layout.Alignment.ALIGN_CENTER
                            : Layout.Alignment.ALIGN_CENTER, 1.5f, 0, false);
        } else {
            bottomItemLayout.increaseWidthTo(widthItems);
        }

        if ((valueLayout == null || valueLayout.getWidth() > widthItems)) {
            String text = getAdapter() != null ? getAdapter().getItem(currentItem) : null;
            valueLayout = new StaticLayout(text != null ? text : "", valuePaint, widthItems,
                    widthLabel > 0 ? Layout.Alignment.ALIGN_CENTER : Layout.Alignment.ALIGN_CENTER,
                    1, ADDITIONAL_ITEM_HEIGHT, false);
        } else {
            valueLayout.increaseWidthTo(widthItems);
        }

        if (widthLabel > 0) {
            if (labelLayout == null || labelLayout.getWidth() > widthLabel) {
                labelLayout = new StaticLayout(label, labPaint, widthLabel,
                        Layout.Alignment.ALIGN_NORMAL, 1, ADDITIONAL_ITEM_HEIGHT, false);
            } else {
                labelLayout.increaseWidthTo(widthLabel);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthSize;
        int height;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getDesiredHeight(valueLayout);

            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        initData(height, width);
        calculateLayoutWidth(widthSize, widthMode);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (itemsLayout == null) {
            if (itemsWidth == 0) {
                calculateLayoutWidth(getWidth(), MeasureSpec.EXACTLY);
            } else {
                createLayouts(itemsWidth, labelWidth);
            }
        }

        if (itemsWidth > 0) {
            canvas.save();
            // 可以改变控件的位置
            canvas.translate(PADDING, -ITEM_OFFSET);
            float progress = (float) scrollingOffset / (2 * (float) itemHeight);
            drawItems(canvas, progress);
            drawTopItem(canvas, progress);
            drawBottomItem(canvas, progress);
            drawValue(canvas, progress);
            canvas.restore();
        }
        // drawShadows(canvas);
    }

    /**
     * Draws shadows on top and bottom of control
     * 
     * @param canvas the canvas for drawing
     */
    // private void drawShadows(Canvas canvas) {
    // topShadow.setBounds(0, 0, getWidth(), 2 * getHeight() / visibleItems);
    // topShadow.draw(canvas);
    //
    // bottomShadow.setBounds(0, getHeight() - 2 * getHeight() / visibleItems,
    // getWidth(), getHeight() + 100);
    // bottomShadow.draw(canvas);
    // }

    /**
     * Draws value and label layout
     * 
     * @param canvas the canvas for drawing
     */
    private void drawValue(Canvas canvas, float progress) {
        valuePaint.setColor(ITEMS_TEXT_COLOR);
        valuePaint.drawableState = getDrawableState();

        Rect bounds = new Rect();
        itemsLayout.getLineBounds(visibleItems / 2, bounds);
        // draw label
        if (labelLayout != null) {
            canvas.save();
            canvas.translate(itemsLayout.getWidth() - LABEL_OFFSET, bounds.top);
            labelLayout.draw(canvas);
            canvas.restore();
        }

        // draw current value
        if (valueLayout != null) {
            canvas.save();
            canvas.translate(0,
                    bounds.top + ((bounds.top - bounds.bottom + Math.abs(scrollingOffset)) / 4)
                            + scrollingOffset);
            valuePaint.setTextScaleX(RATE_CENTER_BIGGER - Math.abs(progress));

            valuePaint.setColor(Color.argb(255 - getColorProgress(255, 77, Math.abs(progress)),
                    50, 50, 50));
            canvas.scale(1.0f, (RATE_CENTER_BIGGER - Math.abs(progress)));
            valueLayout.draw(canvas);
            canvas.restore();

        }
    }

    /**
     * Draws items
     * 
     * @param canvas the canvas for drawing
     */
    private void drawItems(Canvas canvas, float progress) {
        canvas.save();

        int top = itemsLayout.getLineTop(1);
        canvas.translate(0, -top + scrollingOffset);
        reserPaint();
        itemsPaint.drawableState = getDrawableState();
        // if (scrollingOffset > 0) {
        // itemsPaint.setColor(Color.argb(16 - getColorProgress(16, 77,
        // Math.abs(progress)), 249, 249, 249));
        // } else {
        // itemsPaint.setColor(Color.argb(16 - getColorProgress(16, 0,
        // Math.abs(progress)), 249, 249, 249));
        // }
        // topItemLayout.draw(canvas);

        itemsLayout.draw(canvas);

        canvas.restore();
    }

    private void drawTopItem(Canvas canvas, float progress) {

        canvas.save();
        Rect bounds = new Rect();
        itemsLayout.getLineBounds((visibleItems / 2) - 1, bounds);
        reserPaint();
        itemsPaint.drawableState = getDrawableState();
        if (scrollingOffset > 0) {

            itemsPaint.setTextScaleX(1.0f + progress);
            // itemsPaint.setColor(Color.rgb(137 - getColorProgress(137, 229,
            // progress),
            // 140 - getColorProgress(140, 69, progress), 149 -
            // getColorProgress(149, 66, progress)));
            itemsPaint.setColor(Color.argb(77 - getColorProgress(77, 255, Math.abs(progress)), 50,
                    50, 50));

            canvas.translate(0, bounds.top - (scrollingOffset / 4) + scrollingOffset);
            canvas.scale(1.0f, 1.0f + progress);
            topItemLayout.draw(canvas);

        } else {
            // itemsPaint.setColor(Color.argb(77 - getColorProgress(77, 16,
            // Math.abs(progress)), 249, 249, 249));
            canvas.translate(0, bounds.top + scrollingOffset);
            topItemLayout.draw(canvas);
        }

        canvas.restore();
    }

    private void drawBottomItem(Canvas canvas, float progress) {
        canvas.save();

        Rect bounds = new Rect();
        itemsLayout.getLineBounds((visibleItems / 2) + 1, bounds);
        reserPaint();
        itemsPaint.drawableState = getDrawableState();

        if (scrollingOffset < 0) {

            itemsPaint.setTextScaleX(1.0f - progress);
            itemsPaint.setColor(Color.argb(77 - getColorProgress(77, 255, Math.abs(progress)), 50,
                    50, 50));
            // itemsPaint.setColor(Color.rgb(77 + getColorProgress(77, 255,
            // progress),
            // 140 + getColorProgress(140, 69, progress), 149 +
            // getColorProgress(149, 66, progress)));
            canvas.translate(0, bounds.top + (scrollingOffset / 4) + scrollingOffset);
            canvas.scale(1.0f, 1.0f - progress);
            bottomItemLayout.draw(canvas);

        } else {
            // itemsPaint.setColor(Color.argb(77 - getColorProgress(77, 16,
            // Math.abs(progress)), 249, 249, 249));
            canvas.translate(0, bounds.top + scrollingOffset);
            bottomItemLayout.draw(canvas);
        }

        canvas.restore();
    }

    /**
     * 得到颜色变化都每一单元色
     * 
     * @param start 单元色的起始色值
     * @param end 单元色的终止色值
     * @param progress 当前进度比值(当前长度/总长度)
     * @return 变化后的单元色值
     */
    private int getColorProgress(int start, int end, float progress) {
        int ret = 0;
        ret = (int) ((start - end) * progress);
        return ret;
    }

    /**
     * Draws rect for current value
     * 
     */
    // private void drawCenterRect(Canvas canvas) {
    // int center = getHeight() / 2;
    // int offset = getItemHeight() / 2;
    // centerDrawable.setBounds(0, center - offset, getWidth(), center
    // + offset);
    // centerDrawable.draw(canvas);
    // }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        WheelAdapter adapter = getAdapter();
        if (adapter == null) {
            return true;
        }

        if (!gestureDetector.onTouchEvent(event) && event.getAction() == MotionEvent.ACTION_UP) {
            justify();
        }
        return true;
    }

    /**
     * Scrolls the wheel
     * 
     * @param delta the scrolling value
     */
    private void doScroll(int delta) {
        scrollingOffset += delta;

        int count = scrollingOffset / getItemHeight();
        // 当前显示的位置
        int pos = currentItem - count;
        if (isCyclic && adapter.getItemsCount() > 0) {
            // fix position by rotating
            while (pos < 0) {
                pos += adapter.getItemsCount();
            }
            pos %= adapter.getItemsCount();
        } else if (isScrollingPerformed) {
            //
            if (pos < 0) {
                count = currentItem;
                pos = 0;
            } else if (pos >= adapter.getItemsCount()) {
                count = currentItem - adapter.getItemsCount() + 1;
                pos = adapter.getItemsCount() - 1;
            }
        } else {
            // fix position
            pos = Math.max(pos, 0);
            pos = Math.min(pos, adapter.getItemsCount() - 1);
        }

        int offset = scrollingOffset;
        if (pos != currentItem) {
            setCurrentItem(pos, false);
        } else {
            invalidate();
        }

        // update offset
        scrollingOffset = offset - count * getItemHeight();
        if (scrollingOffset > getHeight()) {
            scrollingOffset = scrollingOffset % getHeight() + getHeight();
        }
    }

    // gesture listener
    private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
        public boolean onDown(MotionEvent e) {
            if (isScrollingPerformed) {
                scroller.forceFinished(true);
                clearMessages();
                return true;
            }
            return false;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            startScrolling();
            doScroll((int) -distanceY);
            return true;
        }

        // TODO 这里出Bug
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            lastScrollY = currentItem * getItemHeight() + scrollingOffset;
            int maxY = isCyclic ? 0x7FFFFFFF : adapter.getItemsCount() * getItemHeight();
            int minY = isCyclic ? -maxY : 0;
            scroller.fling(0, lastScrollY, 0, (int) -velocityY / 2, 0, 0, minY, maxY);
            setNextMessage(MESSAGE_SCROLL);
            return true;
        }
    };

    // Messages
    private final int MESSAGE_SCROLL = 0;

    private final int MESSAGE_JUSTIFY = 1;

    /**
     * Set next message to queue. Clears queue before.
     * 
     * @param message the message to set
     */
    private void setNextMessage(int message) {
        clearMessages();
        animationHandler.sendEmptyMessage(message);
    }

    /**
     * Clears messages from queue
     */
    private void clearMessages() {
        animationHandler.removeMessages(MESSAGE_SCROLL);
        animationHandler.removeMessages(MESSAGE_JUSTIFY);
    }

    // TODO animation handler

    private Handler animationHandler = new Handler() {
        public void handleMessage(Message msg) {
            scroller.computeScrollOffset();
            int currY = scroller.getCurrY();
            int delta = lastScrollY - currY;
            lastScrollY = currY;
            if (delta != 0) {
                doScroll(delta);
            }

            // scrolling is not finished when it comes to final Y
            // so, finish it manually
            if (Math.abs(currY - scroller.getFinalY()) < MIN_DELTA_FOR_SCROLLING) {
                currY = scroller.getFinalY();
                scroller.forceFinished(true);
            }
            if (!scroller.isFinished()) {
                animationHandler.sendEmptyMessage(msg.what);
            } else if (msg.what == MESSAGE_SCROLL) {
                justify();
            } else {
                finishScrolling();
            }
        }
    };

    /**
     * Justifies wheel
     */
    private void justify() {
        if (adapter == null) {
            return;
        }

        lastScrollY = 0;
        int offset = scrollingOffset;
        int itemHeight = getItemHeight();
        boolean needToIncrease = offset > 0 ? currentItem < adapter.getItemsCount()
                : currentItem > 0;
        if ((isCyclic || needToIncrease) && Math.abs((float) offset) > (float) itemHeight / 2) {
            if (offset < 0)
                offset += itemHeight + MIN_DELTA_FOR_SCROLLING;
            else
                offset -= itemHeight + MIN_DELTA_FOR_SCROLLING;
        }
        if (Math.abs(offset) > MIN_DELTA_FOR_SCROLLING) {
            scroller.startScroll(0, 0, 0, offset, SCROLLING_DURATION);
            setNextMessage(MESSAGE_JUSTIFY);
        } else {
            finishScrolling();
        }
    }

    /**
     * Starts scrolling
     */
    private void startScrolling() {
        if (!isScrollingPerformed) {
            isScrollingPerformed = true;
            notifyScrollingListenersAboutStart();
        }
    }

    public interface ScrollCallback {
        void scrollingFinish();
    }

    private ScrollCallback callback;

    /**
     * Finishes scrolling
     */
    void finishScrolling() {
        if (isScrollingPerformed) {
            notifyScrollingListenersAboutEnd();
            isScrollingPerformed = false;
        }
        invalidateLayouts();
        invalidate();
        if (callback != null)
            callback.scrollingFinish();
    }

    public void setCallback(ScrollCallback callback) {
        this.callback = callback;
    }

    /**
     * Scroll the wheel
     * 
     * @param itemsToScroll items to scroll
     * @param time scrolling duration
     */
    public void scroll(int itemsToScroll, int time) {
        scroller.forceFinished(true);
        lastScrollY = scrollingOffset;
        int offset = itemsToScroll * getItemHeight();
        scroller.startScroll(0, lastScrollY, 0, offset - lastScrollY, time);
        setNextMessage(MESSAGE_SCROLL);
        startScrolling();
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
