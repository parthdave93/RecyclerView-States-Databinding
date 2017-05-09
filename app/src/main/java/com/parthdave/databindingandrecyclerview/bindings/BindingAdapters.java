package com.parthdave.databindingandrecyclerview.bindings;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by Parth Dave on 19/4/17.
 * Spaceo Technologies Pvt Ltd.
 * parthd.spaceo@gmail.com
 */

public class BindingAdapters {
    @BindingAdapter("bind:imageUrl")
    public static void viewImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url.contains("http") ? url : new File(url)).centerCrop().into(view);
    }
}
