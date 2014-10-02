package org.openmrs.web.servlet;

import java.util.regex.Pattern;

import org.openmrs.module.Module;
import org.openmrs.module.ModuleUtil;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class DevelopmentViewResolver extends InternalResourceViewResolver {
	
	private static Pattern viewNamePattern = Pattern.compile("/?module.*");
	
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView view = super.buildView(viewName);
		String devPrefix = System.getProperty("openmrs-web.developmentPath");
		if (devPrefix == null || !viewNamePattern.matcher(viewName).find())
			return view;
		Module module = ModuleUtil.getModuleForPath(viewName.substring(viewName.indexOf('/', 1) + 1));
		if (module != null && System.getProperty("openmrs-web.development." + module.getModuleId()) != null)
			view.setUrl(devPrefix + viewName + getSuffix());
		return view;
	}
}
