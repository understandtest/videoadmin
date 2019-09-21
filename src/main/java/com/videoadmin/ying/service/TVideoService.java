package com.videoadmin.ying.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.videoadmin.utils.StringUtils;
import com.videoadmin.ying.dao.TTagsMapper;
import com.videoadmin.ying.dao.TVideoMapper;
import com.videoadmin.ying.dao.TVideoTagsMapper;
import com.videoadmin.ying.po.TBanner;
import com.videoadmin.ying.po.TVideo;
import com.videoadmin.ying.po.TVideoTags;
import com.videoadmin.base.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 影片  服务实现�?
 * </p>
 *
 * @author haha
 * @since 2018-10-05
 */
@Service("tVideoService")
public class TVideoService extends BaseService<TVideo> {
	@Autowired
	private TVideoMapper tVideoMapper;
	@Autowired 
	private TVideoTagsMapper tVideoTagsMapper;
	
	public Page<Map<String, Object>> selectListPage(Map<String, Object> paramMap){
	    logger.info("视频查询，入参："+paramMap);
        Page<Map<String, Object>> page = getPageMap(paramMap);
        logger.info("视频查询，入参："+page);
        page.setRecords(tVideoMapper.selectListPage(page, paramMap));
        logger.info("视频查询，出参："+page.toString());
        return page;
	}
	
	public Map<String, Object> getVideoInfo(Map<String, Object> paramMap){
        logger.info("开始查询视频信息");
        return tVideoMapper.getVideoInfo(paramMap);
    }
	@Transactional
	public void addOrUpdata(Map<String, Object> param) {
	    if (param!=null)
        {
	      //更新当前表
	    TVideo tVideo=new TVideo();
	    tVideo.setIsPush(0);
	        if (param.get("videoName")!=null&& !("").equals(param.get("videoName").toString()))
	        {
	        	tVideo.setVideoName(param.get("videoName").toString());
	        }
	        if (param.get("videoUrl")!=null&& !("").equals(param.get("videoUrl").toString()))
	        {
	        	tVideo.setVideoUrl(param.get("videoUrl").toString());
	        }
	        if (param.get("videoType")!=null&& !("").equals(param.get("videoType").toString()))
	        {
	        	tVideo.setVideoType(Integer.valueOf(param.get("videoType").toString()));
	        }
	        if (param.get("videoCoverType")!=null&& !("").equals(param.get("videoCoverType").toString()))
	        {
	        	tVideo.setVideoCoverType(Integer.valueOf(param.get("videoCoverType").toString()));
	        }
	        if (param.get("starId")!=null&& !("").equals(param.get("starId").toString()))
	        {
	        	tVideo.setStarId(Integer.valueOf(param.get("starId").toString()));
	        }
	        if (param.get("classifyId")!=null&& !("").equals(param.get("classifyId").toString()))
	        {
	        	tVideo.setClassifyId(Integer.valueOf(param.get("classifyId").toString()));
	        }
	        if (param.get("tagTypeId")!=null&& !("").equals(param.get("tagTypeId").toString()))
	        {
	        	tVideo.setTagTypeId(Integer.valueOf(param.get("tagTypeId").toString()));
	        }
	        //上映时间
	        if (param.get("pushTime")!=null&& !("").equals(param.get("pushTime").toString()))
	        {
	        	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	 try {
					Date date = sdf.parse(param.get("pushTime").toString());
					tVideo.setPushTime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        if (param.get("briefContent")!=null&& !("").equals(param.get("briefContent").toString()))
	        {
	        	tVideo.setBriefContent(param.get("briefContent").toString());
	        }
	        if (param.get("videoCover")!=null&& !("").equals(param.get("videoCover").toString()))
	        {
	        	tVideo.setVideoCover(param.get("videoCover").toString());
	        }
	        if (param.get("playNum")!=null&& !("").equals(param.get("playNum").toString()))
	        {
	        	tVideo.setPlayNum(Integer.valueOf(param.get("playNum").toString()));
	        }
	        if (param.get("loseNum")!=null&& !("").equals(param.get("loseNum").toString()))
	        {
	        	tVideo.setLoseNum(Integer.valueOf(param.get("loseNum").toString()));
	        }
	        if (param.get("channelId")!=null&& !("").equals(param.get("channelId").toString()))
	        {
	        	tVideo.setChannelId(Integer.valueOf(param.get("channelId").toString()));
	        }
			if (param.get("playTime")!=null&& !("").equals(param.get("playTime").toString()))
			{
				tVideo.setPlayTime(Double.parseDouble(param.get("playTime").toString()));
			}
	        if(null != param.get("id") && !"".equals(param.get("id").toString())){
				//更新
	        	tVideo.setId(Integer.valueOf(param.get("id").toString()));
	        	//tVideo.setUpdateBy(getCurrUser().getUserId());
	        	tVideo.setUpdateTime(new Date());
	        	//更新视频表
	            logger.info("{}更新视频表 start...{}", tVideo);
	            tVideoMapper.updateById(tVideo);
			}else{
				//新增
				//tBanner.setCreateBy(getCurrUser().getUserId());
				tVideo.setCreateTime(new Date());
				tVideoMapper.insert(tVideo);
			}
	        //根据视频id查找标签，并删除
	        if(tVideo!=null){
	        	/*Map<String, Object> map=new HashMap<>();
	        	map.put("video_id", tVideo.getId());
				List<TVideoTags> list=tVideoTagsMapper.selectByMap(map);*/
				List<TVideoTags> tVideoTags = tVideoTagsMapper.selectByVideoId(tVideo.getId());
				for (TVideoTags tVideoTag : tVideoTags) {
//					delete(tVideoTag.getId());
					tVideoTagsMapper.deleteById(tVideoTag.getId());
				}

			}
	        if (param.get("tagIds")!=null&& !("").equals(param.get("tagIds").toString()))
	        {
	        	String str=param.get("tagIds").toString();
//	        	String[] tagIds=str.substring(0, str.length()-1).split(",");
	        	String[] tagIds=str.split(",");
	        	for (String string : tagIds) {
	        		int tagId=Integer.valueOf(string);
					TVideoTags tVideoTags =new TVideoTags();
					tVideoTags.setTagId(Integer.valueOf(tagId));
					if(tVideo!=null){
						tVideoTags.setVideoId(tVideo.getId());
					}
					tVideoTagsMapper.insert(tVideoTags);
				}
	        	
	        }
        }
	}
}