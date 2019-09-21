package com.videoadmin.ying.po;

import java.util.List;

/**
 * Author lbh
 * Date 2019-07-26
 * 动漫章节扩展类
 */
public class CaricatureChapterExt extends CaricatureChapter {

    private List<caricatureChapterImg> caricatureChapterImgs;

    public List<caricatureChapterImg> getCaricatureChapterImgs() {
        return caricatureChapterImgs;
    }

    public void setCaricatureChapterImgs(List<caricatureChapterImg> caricatureChapterImgs) {
        this.caricatureChapterImgs = caricatureChapterImgs;
    }
}
