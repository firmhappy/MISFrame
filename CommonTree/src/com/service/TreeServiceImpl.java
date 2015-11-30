package com.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bean.Vo;
import com.dao.CommonDAO;
import com.db.HibernateSessionFactory;

public class TreeServiceImpl implements TreeService {

	@Override
	public List getChildren(Vo vo) {
		List res = new ArrayList<Vo>();
		Object po = this.voToPo(vo);
		Session session = HibernateSessionFactory.getSession();
		CommonDAO cdao = new CommonDAO();
		List list = cdao.getChildren(
				session,
				po,
				Integer.parseInt(vo.getPage() == null ? "1" : vo.getPage()),
				Integer.parseInt(vo.getPageSize() == null ? Integer.MAX_VALUE
						+ "" : vo.getPageSize()));
		for (Object lpo : list) {
			Vo lvo = this.poToVo(lpo);
			try {
				int count = cdao.getChildrenCount(session, lpo.getClass()
						.getName(), lvo.getId());
				lvo.setCount(count);
				lvo.setIsParent(count > 0 ? "true" : "false");
				lvo.setPage("1");
				lvo.setPageSize(vo.getPageSize());
				lvo.setClassName(vo.getClassName());
				res.add(lvo);
			} catch (SecurityException e) {
				System.out.println("无法放完id属性");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		session.close();
		return res;
	}
	
	public boolean deleteEntity(Vo vo){
		Object po=this.voToPo(vo);
		CommonDAO cdao=new CommonDAO();
		Session session=HibernateSessionFactory.getSession();
		Transaction transaction=session.beginTransaction();
		boolean res=cdao.deleteEntity(session, po);
		if(res){
			transaction.commit();
			session.close();
			return true;
		}else{
			transaction.rollback();
			session.close();
			return false;
		}
	}

	public Vo addEntity(Vo vo) {
		Session session = HibernateSessionFactory.getSession();
		CommonDAO cdao = new CommonDAO();
		Transaction transaction = session.beginTransaction();
		Object po = cdao.addEntity(session, this.voToPo(vo));
		if (po == null) {
			transaction.rollback();
			session.close();
			return null;
		} else {
			transaction.commit();
			vo = this.poToVo(po);
			session.close();
			return vo;
		}
	}

	public Vo updateEntity(Vo vo) {
		Object po = this.voToPo(vo);
		Session session = HibernateSessionFactory.getSession();
		Transaction transaction = session.beginTransaction();
		CommonDAO cdao = new CommonDAO();
		po = cdao.updateEntity(session, po);
		if (po == null) {
			transaction.rollback();
			session.close();
			return null;
		} else {
			vo = this.poToVo(po);
			transaction.commit();
			session.close();
			return vo;
		}
	}

	private Object voToPo(Vo vo) {
		Object po = null;
		String poname = vo.getClass().getName();
		poname = poname.substring(0, poname.length() - 2);
		try {
			po = Class.forName(poname).newInstance();
			Field[] fields = po.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldname = field.getName();
				try {
					Method getvofield;
					try {
						getvofield = vo.getClass().getDeclaredMethod(
								"get" + fieldname.toUpperCase().substring(0, 1)
										+ fieldname.substring(1), null);
					} catch (NoSuchMethodException e) {
						try {
							getvofield=vo.getClass().getSuperclass().getDeclaredMethod(
									"get" + fieldname.toUpperCase().substring(0, 1)
									+ fieldname.substring(1), null);
						} catch (NoSuchMethodException e1) {
							System.out.println("Vo中没有Getter:"+fieldname);
							e1.printStackTrace();
							continue;
						}
					}
					field.set(po, getvofield.invoke(vo, null));
				} catch (SecurityException e) {
					System.out.println("无法访问VO属性：" + field.getName());
					e.printStackTrace();
					continue;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(poname + "不存在！");
			e.printStackTrace();
		}
		return po;
	}

	private Vo poToVo(Object po) {
		Vo vo = null;
		Field[] fields = po.getClass().getDeclaredFields();
		try {
			vo = (Vo) Class.forName(po.getClass().getName() + "Vo")
					.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldname = field.getName();
				try {
					Method setvofield;
					try {
						setvofield = vo.getClass().getDeclaredMethod(
								"set" + fieldname.toUpperCase().substring(0, 1)
										+ fieldname.toLowerCase().substring(1),
								field.getType());
					} catch (NoSuchMethodException e) {
						try {
							setvofield = vo.getClass().getSuperclass().getDeclaredMethod(
									"set" + fieldname.toUpperCase().substring(0, 1)
											+ fieldname.toLowerCase().substring(1),
									field.getType());
						} catch (NoSuchMethodException e1) {
							System.out.println("VO中没有Setter:"+fieldname);
							e1.printStackTrace();
							continue;
						}
					}
					setvofield.invoke(vo, field.get(po));
				} catch (SecurityException e) {
					System.out.println("无法访问Vo属性:" + field.getName());
					e.printStackTrace();
					continue;
				}catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Vo不存在");
			e.printStackTrace();
		}
		return vo;
	}

}
