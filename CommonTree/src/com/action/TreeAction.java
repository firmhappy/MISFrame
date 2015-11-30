package com.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.bean.Vo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.service.TreeService;
import com.service.TreeServiceImpl;

public class TreeAction extends ActionSupport {
	private String id, page, pageSize, pid, param;
	private String className;
	private List responsejsons;
	private Map responsejson;

	public String getChildren() {
		TreeService ts = new TreeServiceImpl();
		try {
			Vo vo = (Vo) Class.forName("com.bean." + className + "Vo")
					.newInstance();
			vo.setClassName(className);
			vo.setPid(Long.parseLong(pid));
			vo.setPage(page);
			vo.setPageSize(pageSize);
			List res = new ArrayList<Map<String, Object>>();
			List list = ts.getChildren(vo);
			for (Object o : list) {
				res.add(this.voToMap((Vo) o));
			}
			responsejsons = res;
			System.out.println("Finish");
			return Action.SUCCESS;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Action.ERROR;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Action.ERROR;
		} catch (ClassNotFoundException e) {
			System.out.println("不存在" + className + "Vo");
			e.printStackTrace();
			return Action.ERROR;
		} catch (SecurityException e) {
			System.out.println("无法访问属性");
			e.printStackTrace();
			return Action.ERROR;
		}
	}

	public String insert() {
		JSONObject json;
		try {
			json = new JSONObject(param);
			Vo vo = this.jsonToVo(json);
			TreeService ts = new TreeServiceImpl();
			vo = ts.addEntity(vo);
			vo.setClassName(json.getString("className"));
			vo.setPage("1");
			vo.setIsParent("false");
			vo.setCount(0);
			this.responsejson = this.voToMap(vo);
			return Action.SUCCESS;
		} catch (JSONException e) {
			System.out.println("数据格式错误");
			e.printStackTrace();
			return Action.ERROR;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Action.ERROR;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Action.ERROR;
		}
	}

	@SuppressWarnings("finally")
	public String delete() {
		JSONObject json;
		TreeService ts = new TreeServiceImpl();
		long pid=-1;
		boolean res=false;
		Map map=new HashMap<String,Object>();
		try {
			json = new JSONObject(param);
			Vo vo = this.jsonToVo(json);
			res = ts.deleteEntity(vo);
			if(res){
				pid=Long.parseLong(json.get("pid").toString());
			}
		} catch (JSONException e) {
			System.out.println("数据格式错误");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			map.put("pid", pid);
			this.setResponsejson(map);
			return Action.SUCCESS;
		}
	}

	public String update() {
		JSONObject json;
		try {
			json = new JSONObject(param);
			Vo vo = this.jsonToVo(json);
			TreeService ts = new TreeServiceImpl();
			vo = ts.updateEntity(vo);
			this.responsejson = this.voToMap(vo);
			return Action.SUCCESS;
		} catch (JSONException e) {
			System.out.println("数据格式错误");
			e.printStackTrace();
			return Action.ERROR;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Action.ERROR;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Action.ERROR;
		}
	}

	private Map voToMap(Vo o) throws IllegalArgumentException,
			IllegalAccessException {
		Map map = new HashMap<String, Object>();
		Field[] subfields = o.getClass().getDeclaredFields();
		Field[] superfields = o.getClass().getSuperclass().getDeclaredFields();
		Field[] fields = Arrays.copyOf(subfields, subfields.length
				+ superfields.length);
		System.arraycopy(superfields, 0, fields, subfields.length,
				superfields.length);
		Map dataobj = new HashMap<String, Object>();
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = field.get(o);
			dataobj.put(field.getName(), value);
		}
		map.put("dataobj", dataobj);
		map.put("id", dataobj.get("id"));
		map.put("name", dataobj.get("name"));
		map.put("isParent", dataobj.get("isParent"));
		map.put("count", dataobj.get("count"));
		map.put("page", dataobj.get("page"));
		map.put("pageSize", dataobj.get("pageSize"));
		map.put("pid", dataobj.get("pid"));
		map.put("className", dataobj.get("className"));
		return map;
	}

	private Vo jsonToVo(JSONObject json) {
		Vo vo = null;
		try {
			vo = (Vo) Class.forName(
					"com.bean." + json.getString("className") + "Vo")
					.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("VO不存在");
			e.printStackTrace();
		} catch (JSONException e) {
			System.out.println("数据格式错误");
			e.printStackTrace();
		}
		if (vo == null) {
			return vo;
		}
		Iterator keys = json.keys();
		while (keys.hasNext()) {
			String key = keys.next().toString();
			Field field;
			try {
				field = vo.getClass().getDeclaredField(key);
			} catch (SecurityException e) {
				System.out.println("无法访问VO属性:" + key);
				e.printStackTrace();
				continue;
			} catch (NoSuchFieldException e) {
				try {
					field = vo.getClass().getSuperclass().getDeclaredField(key);
				} catch (SecurityException e1) {
					System.out.println("无法访问VO属性:" + key);
					e1.printStackTrace();
					continue;
				} catch (NoSuchFieldException e1) {
					System.out.println("属性不存在:" + key);
					e1.printStackTrace();
					continue;
				}
			}
			field.setAccessible(true);
			try {
				if (key.equals("id") || key.equals("pid")) {
					field.set(vo, Long.parseLong(json.getString(key)));

				} else if (key.equals("count")) {
					field.set(vo, Integer.parseInt(json.getString(key)));
				} else {
					field.set(vo, json.get(key).toString());
				}
			} catch (NumberFormatException e) {
				System.out.println("数据格式错误");
				e.printStackTrace();
				continue;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} catch (JSONException e) {
				System.out.println("数据格式错误");
				e.printStackTrace();
				continue;
			}
		}
		return vo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassname() {
		return className;
	}

	public void setClassname(String classname) {
		this.className = classname;
	}

	public List getResponsejsons() {
		return responsejsons;
	}

	public void setResponsejsons(List responsejsons) {
		this.responsejsons = responsejsons;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Map getResponsejson() {
		return responsejson;
	}

	public void setResponsejson(Map responsejson) {
		this.responsejson = responsejson;
	}

}
