package com.weijie.vr4dream.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * json解析帮助类
 * 
 * @author guodong
 * @datetime 2014年7月6日 下午5:37:34
 */
public class JSONUtils {

	public static final <T> T toObject(JSONObject json, Class<T> clazz) {
		try {
			return newGsonInstance().fromJson(json.toString(), clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析某个json节点对应的内容为对象
	 * 
	 * @param json
	 * @param tag
	 * @param clazz
	 * @return
	 */
	public static final <T> T toObject(JSONObject json, String tag,
			Class<T> clazz) {
		try {
			if (json.has(tag) && !json.isNull(tag)) {
				return newGsonInstance().fromJson(json.getJSONObject(tag).toString(),
						clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析jsonArray节点的数据到集合中
	 * 
	 * @param json
	 * @param tag
	 *            节点名称
	 * @param typeToken
	 *            ,例如： new TypeToken<ArrayList<User>>(){}
	 * @return
	 */
	public static <T> ArrayList<T> toList(JSONObject json, String tag,
			TypeToken typeToken) {
		try {
			if (json.has(tag) && !json.isNull(tag)) {
				return newGsonInstance().fromJson(json.getJSONArray(tag).toString(),
						typeToken.getType());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> ArrayList<T> toList(JSONArray json,TypeToken typeToken) {
		if(json!=null) {
			return newGsonInstance().fromJson(json.toString(),
					typeToken.getType());
		} else return null;
	}

	public static <T> ArrayList<T> toList(String json, TypeToken typeToken) {
		return newGsonInstance().fromJson(json, typeToken.getType());
	}

	/**
	 * 转换成json
	 * 
	 * @param list
	 * @return
	 */
	public static String toJson(ArrayList list) {
		return newGsonInstance().toJson(list);
	}
	
	public static final Gson newGsonInstance(){
		return new GsonBuilder().create();
	}
}
