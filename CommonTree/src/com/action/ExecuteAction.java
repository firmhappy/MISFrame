package com.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ExecuteAction extends ActionSupport {
	private String param, modelname, methodname;
	private Map responsejson;

	@Override
	public String execute() {
		try {
			Object modelservice=Class.forName("com.service."+modelname.toUpperCase().substring(0, 1)+modelname.substring(1)).newInstance();
			Method method=modelservice.getClass().getDeclaredMethod(methodname, String.class);
			String res=method.invoke(modelservice, param).toString();
			Map map=new HashMap<String,Object>();
			map.put("result", res);
			this.setResponsejson(map);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}

	public Map getResponsejson() {
		return responsejson;
	}

	public void setResponsejson(Map responsejson) {
		this.responsejson = responsejson;
	}

}
