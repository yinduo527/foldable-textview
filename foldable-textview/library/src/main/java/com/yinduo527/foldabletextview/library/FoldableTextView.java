package com.yinduo527.foldabletextview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yinduo on 14-9-19.
 */
public class FoldableTextView extends TextView implements View.OnClickListener {

    /**
     * The max number of lines when the text is folding.
     */
    private int maxLines = -1;

    /**
     * if the view is ellipsized or not
     * true for yes and false for no
     */
    private boolean isEllipsized = false;

    /**
     * The bottom icon id when the view is ellipsized.
     */
    private int foldingDrawableBottom = 0;

    /**
     * The bottom icon id when the view is not ellipsized.
     */
    private int unfoldingDrawableBottom = 0;

    public FoldableTextView(Context context) {
        super(context);
    }

    public FoldableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FoldableTextView, defStyle, 0);

        foldingDrawableBottom = a.getResourceId(R.styleable.FoldableTextView_foldingDrawableBottom, 0);
        unfoldingDrawableBottom = a.getResourceId(R.styleable.FoldableTextView_unfoldingDrawableBottom, 0);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // initialize the maxLines and isEllipsized
        if (maxLines == -1) {
            Layout l = getLayout();
            if (l != null) {
                maxLines = l.getLineCount();
                if (maxLines > 0) {
                    // if the view is ellipsized, show the bottom icon and set OnClickListener
                    if (l.getEllipsisCount(maxLines - 1) > 0) {
                        isEllipsized = true;
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, foldingDrawableBottom);
                        setOnClickListener(this);
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                        requestLayout();
                        invalidate();

                    }
                }
            }
        }

    }

    public void setFoldingDrawableBottom(int foldingDrawableBottom) {
        this.foldingDrawableBottom = foldingDrawableBottom;
        requestLayout();
        invalidate();
    }

    public void setUnfoldingDrawableBottom(int unfoldingDrawableBottom) {
        this.unfoldingDrawableBottom = unfoldingDrawableBottom;
        requestLayout();
        invalidate();
    }

    @Override
    public void onClick(View v) {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, isEllipsized ? unfoldingDrawableBottom : foldingDrawableBottom);
        setEllipsize(isEllipsized ? null : TextUtils.TruncateAt.END);
        setMaxLines(isEllipsized ? Integer.MAX_VALUE : maxLines);
        isEllipsized = !isEllipsized;
    }
}
