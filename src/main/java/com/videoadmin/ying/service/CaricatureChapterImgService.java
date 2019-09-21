package com.videoadmin.ying.service;

import com.videoadmin.base.BaseService;
import com.videoadmin.ying.dao.caricatureChapterImgMapper;
import com.videoadmin.ying.po.caricatureChapterImg;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author lbh
 * Date 2019-07-25
 */
@Service
public class CaricatureChapterImgService extends BaseService<caricatureChapterImg> {

    /**
     * 批量插入章节图片
     * @param caricatureId
     * @param caricatureChapterImgs
     */
    public void batchAdd(Integer caricatureId, List<caricatureChapterImg> caricatureChapterImgs) {

        for (caricatureChapterImg caricatureChapterImg : caricatureChapterImgs) {
            caricatureChapterImg.setCaricatureChapterId(caricatureId);
            caricatureChapterImg.setCreateTime(new Date());
            mapper.insert(caricatureChapterImg);
        }

    }

    public void delByCaricatureId(Integer caricatureId) {
        ((caricatureChapterImgMapper)mapper).deleteByChapterId(caricatureId);
    }

    public List<caricatureChapterImg> findListByChapterId(Integer chapterId) {
        return  ((caricatureChapterImgMapper)mapper).findListByChapterId(chapterId);
    }
}
