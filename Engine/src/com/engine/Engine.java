package com.engine;

import java.lang.instrument.Instrumentation;
import java.util.List;

import com.boot.ConfigurationPropertiesHelper;
import com.boot.Module;
import com.engine.bean.InstrumentRule;
import com.engine.transformer.ClassesTransformer;
import com.engine.util.InstrumentRuleHelper;

public class Engine implements Module {

	private List<InstrumentRule> rules = null;

	public Engine() {
		rules = InstrumentRuleHelper.getInstrumentRules(ConfigurationPropertiesHelper.getInstrumentRulesPath());
	}

	@Override
	public void start(Instrumentation inst) throws Throwable {
		new ClassesTransformer(rules, inst).retransform();
	}

}
