package com.engine.transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.List;

import com.engine.bean.InstrumentRule;
import com.engine.rule.AbstractInstrumentRule;

public class ClassesTransformer implements ClassFileTransformer {

	private Instrumentation inst;
	private List<InstrumentRule> instrumentRules;

	public ClassesTransformer(List<InstrumentRule> rules, Instrumentation inst) {
		inst.addTransformer(this, true);
		this.inst = inst;
		this.instrumentRules = rules;
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if (instrumentRules == null || instrumentRules.size() <= 0) {
			return classfileBuffer;
		}
		String currentClassName = className.replaceAll("/", "."); // 当前遍历到的类
		for (InstrumentRule rule : instrumentRules) {
			String targetClassName = rule.getClassName(); // 目标类
			if (currentClassName.equals(targetClassName)) {
				com.engine.bean.Instrumentation[] instrumentations = rule.getInstrumentations();
				if (instrumentations == null || instrumentations.length <= 0) {
					continue;
				}
				String dealClass = rule.getDealClass();
				Class c = null;
				AbstractInstrumentRule rule_instance = null;
				try {
					c = Class.forName(dealClass);
					if (c == null) {
						continue;
					}
					Method getInstance = c.getMethod("getInstance", ClassLoader.class, byte[].class, String.class);
					rule_instance = (AbstractInstrumentRule) getInstance.invoke(null, loader, classfileBuffer,
							targetClassName);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (rule_instance == null) {
					System.out.println("Can not instance of " + dealClass + "!");
					continue;
				}
				rule_instance.changeDealClass(targetClassName);
				for (com.engine.bean.Instrumentation instrumentation : instrumentations) {
					String methodName = instrumentation.getMethodName();
					String[] params = instrumentation.getParams();
					int methodType = instrumentation.getMethodType();
					String insertPosition = instrumentation.getInsertPosition();
					rule_instance.setInsertPosition(insertPosition);
					String insertLogic = instrumentation.getInsertLogic();
					
					if (methodType == com.engine.bean.Instrumentation.METHOD_TYPE_CONSTRUCTOR) { // 插桩构造方法
						rule_instance.changeDealMethodToConstructor(params);
						rule_instance.callInsertion(insertLogic, false);
					} else if (methodType == com.engine.bean.Instrumentation.METHOD_TYPE_NORMAL) { // 插桩普通方法
						rule_instance.changeDealMethod(methodName, params);
						rule_instance.callInsertion(insertLogic, false);
					} else if (methodType == com.engine.bean.Instrumentation.METHOD_TYPE_STATIC_BLOCK) { // 插桩添加静态代码块

					} else if (methodType == com.engine.bean.Instrumentation.METHOD_TYPE_MODIFY_CLASS) { // 修改类
						rule_instance.callInsertion(insertLogic, true);
					}
				}
				classfileBuffer = rule_instance.completeTransformDealClass();
			}
		}
		return classfileBuffer;
	}

	public ClassesTransformer retransform() {
		Class[] loadedClasses = inst.getAllLoadedClasses();
		for (Class clazz : loadedClasses) {
			for (InstrumentRule rule : instrumentRules) {
				String targetClass = rule.getClassName();
				if (clazz.getName().equals(targetClass)) {
					try {
						inst.retransformClasses(clazz);
					} catch (UnmodifiableClassException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return this;
	}

}
