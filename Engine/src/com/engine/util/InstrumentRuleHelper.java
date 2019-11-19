package com.engine.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.engine.bean.InstrumentRule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InstrumentRuleHelper {

	/* 读取指定文件夹路径下的所有.json格式的插桩配置规则文件。 */
	public static List<InstrumentRule> getInstrumentRules(String instrumentRulesPath) {
		List<InstrumentRule> rules = new LinkedList<InstrumentRule>();
		List<InstrumentRule> temp = null;
		File baseDir = new File(instrumentRulesPath);
		ObjectMapper mapper = new ObjectMapper();
		if (baseDir.isDirectory()) {
			for (File file : baseDir.listFiles()) {
				if (file.isFile() && file.getName().endsWith(".json")) {
					try {
						temp = mapper.readValue(file, new TypeReference<List<InstrumentRule>>() {
						});
						rules.addAll(temp);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return rules;
	}
}
