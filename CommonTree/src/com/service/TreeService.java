package com.service;

import java.util.List;

import com.bean.Vo;

public interface TreeService {
	public List getChildren(Vo vo);
	public Vo addEntity(Vo vo);
	public Vo updateEntity(Vo vo);
	public boolean deleteEntity(Vo vo);
}
