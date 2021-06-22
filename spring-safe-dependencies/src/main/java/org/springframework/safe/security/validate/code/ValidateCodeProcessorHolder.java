package org.springframework.safe.security.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.safe.security.enmus.Validate;
import org.springframework.safe.security.validate.code.exception.ValidateCodeException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 校验码处理器管理器
 *
 * @author Sir.D
 */
@Component
public class ValidateCodeProcessorHolder {

	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

	public ValidateCodeProcessor findValidateCodeProcessor(Validate type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}

	public ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			throw new ValidateCodeException("====> validate Code Processors【" + processor + "】is non-existent！");
		}
		return processor;
	}

}
