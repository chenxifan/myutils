package com.excellence.expps.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.excellence.common.util.SQLUtil;
import com.excellence.exoa.option.dao.OptionDao;
import com.excellence.expps.common.MainframeBeanFactory;
import com.excellence.platform.missive.vo.OpinionInstVO;
import com.excellence.platform.um.dao.UserService;
import com.excellence.platform.um.vo.Common;

/**
 * @author zhangcj
 */
public class ExOAUtil {

	private static SQLUtil sqlUtil = SQLUtil.getInstance();
	
	/**
	 * 获取OA基础数据字典的枚举值,并且按 name 为key content 为value 组合成map对象
	 * @param dicTypeName
	 * @return
	 */
	public static Map getDict2Map(String dicTypeName) {
		List types = getDicts(dicTypeName);
		Map map = new HashMap();
		if (types != null) {
			for (int i=0;i<types.size();i++) {
				Map typeMap = (Map)types.get(i);
				map.put(typeMap.get("name")+"",typeMap.get("content"));
			}
		}
		return map;
	}

	/**
	 * 获取OA基础数据字典的枚举值
	 * @param dicTypeName 数据字典类型名称
	 * @return 数据字典的枚举值 ArrayList <Map>
	 * MapEnry:{type:数据字典类型, name=数据字典名称, content=数据字典内容}
	 * 例如： {option_id=2, type=programType, content=电视剧 , name=teledrama}
	 */
	public static ArrayList getDicts(String dicTypeName) {
		try {
			OptionDao optionDao = (OptionDao) MainframeBeanFactory.getBeanFactory().getBean("exoaOptionDao");
			return optionDao.getOptionListByType(dicTypeName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据数据字典的Code，取到字典的中文显示名称
	 * 
	 * @param dicType： 字典类型名称
	 * @param dicCode： 字典的code
	 * @return String: 字典的中文显示名称
	 */
	public static String getDicName(String dicType, String dicCode) {
		try {
			for (Iterator it = getDicts(dicType).iterator(); it.hasNext();) {
				Map dicMap = (Map) it.next();
				String dicName = (String) dicMap.get("name");

				if (dicName.equals(dicCode)) {
					return (String) dicMap.get("content");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "";
	}

	/**
	 * 功能：获取某些数据字典的枚举内容
	 * 
	 * @param names
	 *            数据字典的名称数组
	 * @return key是数据字典的名称，value是数据字典的枚举值 ArrayList <Map>，MapEnry:{type:数据字典类型,
	 *         name=数据字典名称, content=数据字典内容}，例如： {option_id=2, type=programType,
	 *         content=电视剧, name=teledrama}
	 */
	public static Map getDicts(String[] names) {
		Map data = new HashMap();
		if (names != null && names.length > 0) {
			SQLUtil sqlUtil = SQLUtil.getInstance();
			String sql = "select * from mv_option_list where name<>? and (";
			ArrayList params = new ArrayList();
			params.add("type");
			for (int i = 0; i < names.length; i++) {
				if (i == 0)
					sql += "type=?";
				else
					sql += " OR type=?";
				params.add(names[i]);
			}
			sql += ") order by name asc";
			try {
				List maps = sqlUtil.query(sql, params);
				List tmp = null;
				Map map = null;
				for (int i = 0; i < names.length; i++) {
					tmp = new ArrayList();
					data.put(names[i], tmp);
					for (int j = 0; j < maps.size(); j++) {
						map = (Map) maps.get(j);
						if (map.get("type").toString().equalsIgnoreCase(
								names[i])) {
							tmp.add(map);
						}
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return data;
	}

	/**
	 * 获取用户管理模块服务
	 * 
	 * @param addressParaStr
	 * @param expandFlag
	 * @param excludeIds
	 * @param noGroup
	 * @param addressOriginal
	 * @return
	 */
	public static UserService getUserService() {
		return UserService.getInstance();
	}

	public static Integer getCommonIdByUserName(String userName) {

		if (StringUtils.isEmpty(userName)) {
			return null;
		}

		String sqlString = "select id from um_common where name = ?";

		ArrayList params = new ArrayList();
		params.add(userName);

		try {
			Map map = sqlUtil.queryRecordInfo(sqlString, params);

			if (map != null) {
				return (Integer) map.get("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据公文实例id 和 表单30以外的数据名称获取 数据
	 * @param formsetInstId
	 * @param name
	 * @return
	 */
	public static List getFormDataInsts(Integer formsetInstId,String name) {
		List datas = null;
		ArrayList params = new ArrayList();
		String sql = "select name,value,descri from MV_FORM_DATA_INST where 1=1 ";
		
		if (formsetInstId != null) {
			sql += " and formset_inst_id = ?";
			params.add(formsetInstId);
		}
		if (StringUtils.isNotEmpty(name)) {
			sql += " and name = ?";
			params.add(name);
		}
		try {
			datas = sqlUtil.query(sql,params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		return datas;
	}
	
	/**
	 * 根据公文实例id 和 表单30以外的数据名称获取 数据
	 * 并将数据转化为map key:字段name value:字段value
	 * @param formsetInstId
	 * @param name
	 * @return
	 */
	public static Map getFormDataInst(Integer formsetInstId,String name) {
		Map map = new HashMap();
		List datas = getFormDataInsts(formsetInstId,name);
		if (datas != null && datas.size() >0) {
			for (int i=0;i<datas.size();i++) {
				Map dataMap = (Map) datas.get(i);
				map.put(dataMap.get("name"),dataMap.get("value"));
			}
		}
		return map;
	}
	
	/**
	 * 根据公文实例获取公文意见
	 * @param formsetInstId
	 * @param bindingDataName 可以为null
	 * @return
	 * @throws Exception
	 */
	public static List getOpinionByFormsetInstId(int formsetInstId,String bindingDataName) throws Exception {
		//binding_data_name
		//formset_inst_id
		ArrayList params = new ArrayList();
		String sql = "select * from mv_opinion_inst where formset_inst_id = ? ";
		params.add(new Integer(formsetInstId));
		if (bindingDataName != null && StringUtils.isNotEmpty(bindingDataName)) {
			sql += " and binding_data_name = ? ";
			params.add(bindingDataName);
		}
		sql += " order by binding_data_name,edit_time ";
		return sqlUtil.query(sql,params);
	}
	
	
	public static Common getSecondOrg(int commonId,UserService userService,int targetCommonId) throws Exception {
		Common comm = userService.loadCommon(commonId);
		if (comm != null) {
			int pid = comm.getPid();
			if (pid == 0 || pid == targetCommonId) {
				return comm;
			} else {
				comm = getSecondOrg(pid,userService,targetCommonId);
			}
		}
		return comm;
	}
	
	/**
	 * 根据流程表单实例ID和绑定数据源名称获取最后填写“意见实例VO”对象
	 * @param formsetInstId
	 * @param dsName
	 * @return
	 */
	public static Map getLastOpinionInst(int formsetInstId, String bindingDataName){ 
		try{
			List opinionInstVos = getOpinionByFormsetInstId(formsetInstId,bindingDataName);		
			if(opinionInstVos == null || opinionInstVos.size() <= 0){
				return null;
			}
			//根据填写意见时间进行排序
			Collections.sort(opinionInstVos, new Comparator() {  
				public int compare(Object a, Object b) {  
		          	Map opinion1 = (Map)a;	
		          	Map opinion2 = (Map)b;		            
		          	return  new Long(((Date)opinion2.get("edit_time")).getTime() - ((Date)opinion1.get("edit_time")).getTime()).intValue();
				}  
		    });   
			//TODO 方法的返回值应该是批示意见的VO，需要将Map转换成VO
			return (Map)opinionInstVos.get(0);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}









