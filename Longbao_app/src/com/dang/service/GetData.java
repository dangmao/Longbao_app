package com.dang.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.dang.domain.DataTable;
import com.dang.domain.MySetting;
import com.dang.util.Weibo;
import com.dang.util.DataGetter;

public class GetData {
	//public static DataTable dataTable;
	public static DataTable getData(MySetting mySetting)
	{
		DataTable dataTable = new DataTable();
		//新媒体指数
		HashMap<String,String> hm = new HashMap<String, String>();
		hm.put("vbang_zhishu", "朱一龙</span><spanclass=\"rank_left_value\">.+?</b>");
		hm.put("vbang_paiming", "唯爱一龙＊与众不同.+?<img");
		hm = DataGetter.getData("http://www.xunyee.cn/rank-person-index-0-page-@.html", hm);
		dataTable.setVbang_zhishu(hm.get("vbang_zhishu"));
		dataTable.setVbang_paiming(hm.get("vbang_paiming"));
		//百度送花
		hm = new HashMap<String, String>();
		hm.put("baidu_songhua_paiming", "\"rank\":.+?,");
		hm.put("baidu_songhua", "\"score\":\".+?\"");
		hm = DataGetter.getData("http://baike.baidu.com/starflower/api/starflowerrank?lemmaid=4038282&qq-pf-to=pcqq.c2c", hm);
		dataTable.setBaidu_songhua(hm.get("baidu_songhua"));
		dataTable.setBaidu_songhua_paiming(hm.get("baidu_songhua_paiming"));
		//百度贴吧
		hm = new HashMap<String, String>();
		hm.put("tieba_paiming", "本吧排名：.+?</a>");
		hm.put("tieba_qiandao", "<p>本吧签到人数：.+?</p>");
		hm.put("tieba_tiezi", "贴子数.+?篇");
		hm = DataGetter.getData("http://tieba.baidu.com/f?ie=utf-8&kw=%E6%9C%B1%E4%B8%80%E9%BE%99&fr=search", hm);
		dataTable.setTieba_paiming(hm.get("tieba_paiming"));
		dataTable.setTieba_qiandao(hm.get("tieba_qiandao"));
		dataTable.setTieba_tiezi(hm.get("tieba_tiezi"));
		//寻艺
		hm = new HashMap<String, String>();
		hm.put("xunyi_qiandao", "<iid=\"smallsite_right_signinnum\">.+?</i>");
		hm.put("xunyi_paiming", "今日排名：<i>.+?</i>");
		hm = DataGetter.getData("http://www.xunyee.cn/person-24212.html", hm);
		dataTable.setXunyi_qiandao(hm.get("xunyi_qiandao"));
		dataTable.setXunyi_paiming(hm.get("xunyi_paiming"));
		//微博
		//势力榜
		ArrayList<String> user = mySetting.getUsername();
		ArrayList<String> pass = mySetting.getPassword();
		Weibo wb = null;
		if(user.size()>1)
			wb = new Weibo(user.get(1), pass.get(1));
		else
			wb = new Weibo("#########weiboname###########", "#############password#############");
		wb.login();
		String cookie = wb.getHeaderCookie().toString();
		hm = new HashMap<String, String>();
		hm.put("mingxing_tiji", "\"desc\":\".u63d0.u53ca.u91cf\".+?httpaaaaa\"mention\":.+?,");
		hm.put("mingxing_tiji_paiming", "\"desc\":\".u63d0.u53ca.u91cf\".+?httpaaaaa\"rank\":.+?,");
		hm.put("mingxing_hudong", "\"desc\":\".u4e92.u52a8.u91cf\".+?httpaaaaa\"mention\":.+?,");
		hm.put("mingxing_hudong_paiming","\"desc\":\".u4e92.u52a8.u91cf\".+?httpaaaaa\"rank\":.+?,");
		hm.put("mingxing_sousuo", "\"desc\":\".u641c.u7d22.u91cf\".+?httpaaaaa\"mention\":.+?,");
		hm.put("mingxing_sousuo_paiming", "\"desc\":\".u641c.u7d22.u91cf\".+?httpaaaaa\"rank\":.+?,");
		hm.put("mingxing_aimu", "\"desc\":\".u7231.u6155.u503c\".+?httpaaaaa\"mention\":.+?,");
		hm.put("mingxing_aimu_paiming", "\"desc\":\".u7231.u6155.u503c\".+?httpaaaaa\"rank\":.+?,");
		hm.put("mingxing_zongfen", "mod_name.+?profile_urlaaaaa\"score\":\".+?\",");
		hm.put("mingxing_zongfen_paiming", "mod_name.+?profile_urlaaaaa\"ranknum\":.+?,");
		//hm = DataGetter.getData("http://club.starvip.weibo.cn/chart/detail?rank_type=5&sid=1594052081", cookie, hm);
		hm = DataGetter.getData("http://club.starvip.weibo.cn/chart/detail?rank_type=5&sid=1594052081&time_type=week&day_start=20161121&day_end=20161127", cookie, hm);
		dataTable.setMingxing_tiji(hm.get("mingxing_tiji"));
		dataTable.setMingxing_tiji_paiming(hm.get("mingxing_tiji_paiming"));
		dataTable.setMingxing_hudong(hm.get("mingxing_hudong"));
		dataTable.setMingxing_hudong_paiming(hm.get("mingxing_hudong_paiming"));
		dataTable.setMingxing_sousuo(hm.get("mingxing_sousuo"));
		dataTable.setMingxing_sousuo_paiming(hm.get("mingxing_sousuo_paiming"));
		dataTable.setMingxing_aimu(hm.get("mingxing_aimu"));
		dataTable.setMingxing_aimu_paiming(hm.get("mingxing_aimu_paiming"));
		dataTable.setMingxing_zongfen(hm.get("mingxing_zongfen"));
		dataTable.setMingxing_zongfen_paiming(hm.get("mingxing_zongfen_paiming"));
		return dataTable;
		
	}
}
