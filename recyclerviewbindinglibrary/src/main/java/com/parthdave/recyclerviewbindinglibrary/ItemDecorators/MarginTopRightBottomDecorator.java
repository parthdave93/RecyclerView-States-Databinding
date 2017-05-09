package com.parthdave.recyclerviewbindinglibrary.ItemDecorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MarginTopRightBottomDecorator extends RecyclerView.ItemDecoration {
    
    private int spacing;
    private MarginType marginType;
    
    public enum MarginType {
        TopLeftRightBottom, LeftRightBottom, Bottom, Top, LeftRight
    }
    
    public MarginTopRightBottomDecorator(int spacing, MarginType marginType) {
        this.spacing = spacing;
        this.marginType = marginType;
    }
    
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (marginType == MarginType.LeftRightBottom || marginType == MarginType.Bottom)
            outRect.bottom = spacing;
        if (marginType == MarginType.LeftRightBottom || marginType == MarginType.LeftRight)
            outRect.left = spacing;
        if (marginType == MarginType.LeftRightBottom || marginType == MarginType.LeftRight)
            outRect.right = spacing;
    }
}