package com.engine.instrument;

import java.lang.reflect.Method;

import com.boot.ModuleLoader;
import com.engine.bean.Instrumentation;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;

/* 所有的插桩规则对象都应该是单例模式的。 */
public abstract class AbstractInstrumentRule {
	protected ClassPool pool;
	protected CtClass dealClass; // 规则当前插桩类
	protected byte[] classfileBuffer; // 保存修改后的类所对应的字节数组

	protected CtBehavior dealMethod; // 当前插桩方法
	protected String[] params; // 插桩方法的参数列表
	protected String insertPosition; // 插桩位置

	public AbstractInstrumentRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		pool = new ClassPool();
		pool.appendClassPath(new ClassClassPath(ModuleLoader.class));
		if (loader != null) {
			pool.appendClassPath(new LoaderClassPath(loader));
		}
		try {
			dealClass = pool.get(className);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	/* 设置插桩类 */
	public void changeDealClass(String className) {
		try {
			dealClass = pool.get(className);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	/* 插桩插桩方法 */
	public void changeDealMethod(String methodName, String[] params) {
		if (dealClass == null) {
			System.out.println("Please set dealClass first!");
			return;
		}
		try {
			if (params != null && params.length > 0) {
				CtClass[] cparams = new CtClass[params.length];
				for (int i = 0; i < params.length; i++) {
					cparams[i] = pool.get(params[i]);
				}
				dealMethod = dealClass.getDeclaredMethod(methodName, cparams);
			} else {
				dealMethod = dealClass.getDeclaredMethod(methodName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 设置插桩方法为构造方法 */
	public void changeDealMethodToConstructor(String[] params) {
		if (dealClass == null) {
			System.out.println("Please set dealClass first!");
			return;
		}
		try {
			if (params != null) {
				CtClass[] cparams = new CtClass[params.length];
				for (int i = 0; i < params.length; i++) {
					cparams[i] = pool.get(params[i]);
				}
				dealMethod = dealClass.getDeclaredConstructor(cparams);
			} else {
				System.out.println("Parameters of Constructor can not be null!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 设置插桩位置 */
	public void setInsertPosition(String insertPosition) {
		this.insertPosition = insertPosition;
	}

	/* 执行插桩逻辑 */
	public void callInsertion(String insertLogic, boolean transform) { // false - 返回插桩代码；true - 不返回插桩代码。
		String code = null;
		try {
			Method m = this.getClass().getMethod(insertLogic, null);
			m.setAccessible(true);
			if (!transform)
				code = (String) m.invoke(this, null);
			else
				m.invoke(this, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finishInsert(code); // 插入代码
	}

	public void finishInsert(String code) {
		if (code == null || code.length() == 0) {
			return;
		}
		try {
			if (insertPosition.equals(Instrumentation.POSITION_BEFORE)) {
				dealMethod.insertBefore(code);
			} else if (insertPosition.equals(Instrumentation.POSITION_AFTER)) {
				dealMethod.insertAfter(code);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 返回修改后的类的字节数组 */
	public byte[] completeTransformDealClass() {
		try {
			classfileBuffer = dealClass.toBytecode();
			dealClass.defrost(); // 解冻
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dealClass.detach();
		}
		return classfileBuffer;
	}

}
