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

public abstract class AbstractInstrumentRule {
	protected ClassPool pool;
	protected CtClass dealClass;
	protected byte[] classfileBuffer;

	protected CtBehavior dealMethod;
	protected String[] params;
	protected String insertPosition;

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

	// 所有的插桩规则对象都应该是单例模式的。

	public void changeDealClass(String className) {
		try {
			dealClass = pool.get(className);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

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

	public void setInsertPosition(String insertPosition) {
		this.insertPosition = insertPosition;
	}

	public void callInsertion(String insertLogic) {
		String code = null;
		try {
			Method m = this.getClass().getMethod(insertLogic, null);
			m.setAccessible(true);
			code = (String) m.invoke(this, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finishInsert(code);
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

	public byte[] completeTransformDealClass() {
		try {
			classfileBuffer = dealClass.toBytecode();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dealClass.detach();
		}
		return classfileBuffer;
	}

	public String insert_transmitTaintFromParamsToThis() {
		pool.importPackage("com.engine.instrument.StringBuilderTaintRule");
		StringBuffer code_buffer = new StringBuffer("");
		System.out.println("exec insert logic...");
		code_buffer.append("System.out.println(\"Inserting ...\");");
		return code_buffer.toString();
	}
}
