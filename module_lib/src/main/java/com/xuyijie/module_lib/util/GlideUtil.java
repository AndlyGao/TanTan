package com.xuyijie.module_lib.util;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xuyijie.module_lib.App;


public class GlideUtil {
    public static void loadGeneralImage(Object url, ImageView imageView) {

        Glide.with(App.getInstance()).asBitmap().load(url).into(imageView);
    }

    public static void loadGeneralImageWithAnim(Object url, ImageView imageView) {
        Glide.with(App.getInstance()).asBitmap().load(url).transition(BitmapTransitionOptions.withCrossFade(1500)).into(imageView);
    }


    public static void loadRoundCornerImage(String url, ImageView imageView) {
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(App.getInstance()).asBitmap().apply(options).load(url).into(imageView);
    }

    public static void loadRoundCornerAvatarImage(Object url, ImageView imageView, int radius) {
        RoundedCorners roundedCorners = new RoundedCorners(radius);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(App.getInstance()).asBitmap().apply(options).load(url).into(imageView);
    }
    public static void loadRoundCorner(ImageView iv,int radius,Object url){
        Glide.with(App.getInstance()).load(url)
 .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
.into(iv);

    }
}
