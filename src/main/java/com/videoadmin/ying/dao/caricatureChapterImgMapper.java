package com.videoadmin.ying.dao;

import com.videoadmin.base.BaseMapper;
import com.videoadmin.ying.po.caricatureChapterImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author lbh
 * Date 2019-07-25
 */
public interface caricatureChapterImgMapper extends BaseMapper<caricatureChapterImg> {
    void deleteByChapterId(@Param("chapterId") Integer chapterId);

    List<caricatureChapterImg> findListByChapterId(@Param("chapterId") Integer chapterId);
}
