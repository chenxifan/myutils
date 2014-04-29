/*
 * 创建日期 2011-4-12
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.excellence.expps.util;

import java.util.TimerTask;

import com.excellence.common.service.TaskFace;
import com.excellence.common.util.cache.CacheVersionSynchronizer;

/**
 * 职责：定时刷新用户数据缓存
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class RefreshCacheVersionTask extends TimerTask {
	private TaskFace synchr = null;
	/**
	 * 
	 */
	public RefreshCacheVersionTask() {
		super();
	}

	/* （非 Javadoc）
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if(synchr==null)
		{
			synchr = new CacheVersionSynchronizer();
		}
		try 
		{
			System.out.println("更新用户缓存...");
			synchr.taskDo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
