package com.mzw.wallpaper;

import java.io.Serializable;

/**
 * Created by think on 2018/10/7.
 */

public class WallpaperBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    String id;
    String name;
    String titel;
    String content;
    int pic;//图片  资源图片


    public WallpaperBean(String id, String name, String titel, String content, int pic) {
        this.id = id;
        this.name = name;
        this.titel = titel;
        this.content = content;
        this.pic = pic;
    }
}
