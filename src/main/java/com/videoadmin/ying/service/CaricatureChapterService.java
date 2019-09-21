package com.videoadmin.ying.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.ying.dao.CaricatureChapterMapper;
import com.videoadmin.ying.dao.caricatureChapterImgMapper;
import com.videoadmin.ying.po.CaricatureChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Author lbh
 * Date 2019-07-25
 */
@Transactional
@Service
public class CaricatureChapterService extends BaseService<CaricatureChapter> {

    @Autowired
    private caricatureChapterImgMapper caricatureChapterImgMapper;

    public Page<Map<String, Object>> selectListPage(Map<String, Object> paraMap) {

        logger.info("章节查询，入参：" + paraMap);
        Page<Map<String, Object>> page = getPageMap(paraMap);
        logger.info("章节查询，入参：" + page);
        page.setRecords(((CaricatureChapterMapper) mapper).selectListPage(page, paraMap));
        logger.info("章节查询，出参：" + page.toString());
        return page;
    }

    /**
     * 批量删除章节，根据ids
     * @param ids
     */
    public void batchDelByIds(String ids) {
        logger.info("删除分类数据，删除主键为：" + ids);
        Map<String,Object> map=new HashMap<String,Object>();
        if(DataUtil.isNotEmpty(ids)){
            String[] strs=ids.split(",");
            for (String string : strs) {
                int id=Integer.valueOf(string);

                //删除所属图片
                caricatureChapterImgMapper.deleteByChapterId(id);
                mapper.deleteById(id);
            }
        }

    }

    /**
     * 校验
     * @param param
     * @return
     */
    public Integer validateCaricatureChapterName(Map<String, Object> param) {
        return ((CaricatureChapterMapper)mapper).validateCaricatureChapterName(param);
    }

}


