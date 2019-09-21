package com.videoadmin.ying.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.base.BaseService;
import com.videoadmin.utils.DataUtil;
import com.videoadmin.ying.dao.CaricatureChapterMapper;
import com.videoadmin.ying.dao.CaricatureMapper;
import com.videoadmin.ying.dao.caricatureChapterImgMapper;
import com.videoadmin.ying.po.Caricature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author lbh
 * Date 2019-07-25
 */
@Transactional
@Service
public class CaricatureService extends BaseService<Caricature> {

    @Autowired
    private caricatureChapterImgMapper caricatureChapterImgMapper;

    @Autowired
    private CaricatureChapterMapper caricatureChapterMapper;

    public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
        logger.info("视频查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("视频查询，入参："+page);
        page.setRecords(((CaricatureMapper)mapper).selectListPage(page,paramMap));
        logger.info("视频查询，出参："+page.toString());
        return page;
    }

    public void updateById(Caricature caricature){
        mapper.updateById(caricature);
    }


    /**
     * 批量删除漫画
     * @param ids
     */
    public void batchDelete(String ids) {

        logger.info("删除视频数据，删除主键为：" + ids);
        Map<String,Object> map=new HashMap<String,Object>();
        if(DataUtil.isNotEmpty(ids)){
            String[] strs=ids.split(",");
            for (String string : strs) {
                int id=Integer.valueOf(string);
                //删除章节
                deleteCaricatureChapterByCaricatureId(id);
                mapper.deleteById(id);
            }
        }

    }


    /**
     * 删除关联的动漫章节
     * @param caricatureId
     */
    private void deleteCaricatureChapterByCaricatureId(int caricatureId) {

        //查询关联动漫章节的ids
        List<Integer> chapterIds = caricatureChapterMapper.selectByCaricatureId(caricatureId);

        for (Integer chapterId : chapterIds) {
            //删除所有的关联的图片
            caricatureChapterImgMapper.deleteByChapterId(chapterId);
            //删除对应的章节
            caricatureChapterMapper.deleteById(chapterId);
        }


    }

    /**
     * 添加或者更新
     * @param caricature
     */
    public void addOrUpdate(Caricature caricature) {

        Integer id = caricature.getId();
        //caricature.setIsPush("0"); //默认修改后到未发布状态
        if(null == id){ //添加
            caricature.setCreateTime(new Date());
            mapper.insert(caricature);
        }else{ //修改
            caricature.setUpdateTime(new Date());
            mapper.updateById(caricature);
        }

    }

    public Integer validateCaricatureName(Map<String, Object> param) {
        return ((CaricatureMapper)mapper).validateCaricatureName(param);
    }
}
