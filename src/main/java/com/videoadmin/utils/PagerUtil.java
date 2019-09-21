
package com.videoadmin.utils;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.ArrayList;
import java.util.List;

public class PagerUtil {

	public static List<Integer> getSelectNumberList(){
		List<Integer> numberList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			numberList.add((i + 1) * 10);
		}

		return numberList;
	}

	/**
	 * 获取分页信息并显示下拉框
	 * @return
	 */
	public static String getPageInfoAndShowSelect(Page<?> page){
		return getPage(page,true);
	}


	public static String getPageInfo(Page<?> page) {

		return getPage(page,false);

	}

	public static String getPage(Page<?> page,boolean isShowSelect){

		int currentPage = page.getCurrent();// 当前页
		int totalPages = page.getPages();// 总页数
		int pagesize = page.getSize();//每页多少条
		if(page.getTotal()==0){
			return "";
		}
		final StringBuffer html = new StringBuffer();
		html.append("<div class='panel-body'>");
		html.append(" <ul class='pull-left pageright'>");
		int totalrows = page.getTotal();
		html.append("<li><a href='javascript:;'>当前第"+currentPage+"页,共"+totalrows+"条记录，合计"+totalPages+"页</a></li>");
		html.append(" </ul>");
		html.append(" <ul id='page' class='pagination pagination-sm pull-right'>");

		//添加分页下拉框
		if(isShowSelect){
			html.append("<select id='pageSelect' lay-ignore style=\"width:50px\">");

			// 分页选择下拉框
			List<Integer> selectNumberList = getSelectNumberList();

			selectNumberList.forEach((n) -> {

				String selected = "";

				if(n == pagesize){
					selected = "selected='selected'";
				}

				html.append("<option "+selected+"  value="+n+">"+n+"</option>");
			});

			html.append("</select>");
		}

		if (currentPage > 1) {
			html.append(" <li><a href='javascript:turnPage(1);'>«</a></li>");
		} else {
			html.append(" <li><a href='javascript:;'>«</a></li>");
		}
		// 快速导航，显示前后4页共9页
		int i = 1;
		if (totalPages < 10) {
			for (; i <= totalPages; i++) {
				if (i == currentPage) {
					html.append("<li><a href='javascript:;' style=' color: #fff;background-color: #428bca;'>"+ currentPage +"</a></li>");
				} else {
					html.append("<li><a href='javascript:turnPage(" + i + ");'>"+ i +"</a></li>");
				}
			}
		} else {
			if (currentPage < 5) {
				i = 1;
				for (; i < 10; i++) {
					if (i > totalPages) {
						break;
					}
					if (i == currentPage) {
						html.append("<li><a href='javascript:;' style=' color: #fff;background-color:#428bca;'>"+ currentPage +"</a></li>");
					} else {
						html.append("<li><a href='javascript:turnPage(" + i + ");'>"+ i +"</a></li>");
					}
				}
			} else if (currentPage + 4 > totalPages) {
				i = totalPages - 9 + 1;
				if (i < 1) {
					i = 1;
				}
				for (; i <= totalPages; i++) {
					if (i == currentPage) {
						html.append("<li><a href='javascript:;' style=' color: #fff;background-color:#428bca;'>"+ currentPage +"</a></li>");
					} else {
						html.append("<li><a href='javascript:turnPage(" + i + ");'>"+ i +"</a></li>");
					}
				}
			} else {
				i = currentPage - 4;
				for (; i <= currentPage + 4; i++) {
					if (i == currentPage) {
						html.append("<li><a href='javascript:;' style=' color: #fff;background-color:#428bca;'>"+ currentPage +"</a></li>");
					} else {
						html.append("<li><a href='javascript:turnPage(" + i + ");'>"+ i +"</a></li>");
					}
				}
			}
		}
		// 下一页
		if (currentPage != totalPages) {
			html.append("<li><a href='javascript:turnPage(" + totalPages + ");'>»</a></li>");
		} else {
			html.append("<li><a href='javascript:;'>»</a></li>");
		}


		html.append("</ul></div>");
		return html.toString();

	}
}
