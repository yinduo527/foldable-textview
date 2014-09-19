package com.yinduo527.foldabletextview.library;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yinduo on 14-9-19.
 */
public class FoldableTextView extends TextView implements View.OnClickListener {


    private int maxLines = -1;

    private boolean isEllipsized = false;

    public FoldableTextView(Context context) {
        super(context);
    }

    public FoldableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FoldableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (maxLines == -1) {
            Layout l = getLayout();
            if (l != null) {
                maxLines = l.getLineCount();
                if (maxLines > 0) {
                    if (l.getEllipsisCount(maxLines - 1) > 0) {
                        isEllipsized = true;
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        setOnClickListener(this);
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                        requestLayout();
                        invalidate();

                    }
                }
            }
        }

    }

    @Override
    public void onClick(View v) {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, isEllipsized ? 0 : 0);
        setEllipsize(isEllipsized ? null : TextUtils.TruncateAt.END);
        setMaxLines(isEllipsized ? Integer.MAX_VALUE : maxLines);
        isEllipsized = !isEllipsized;
    }
}
