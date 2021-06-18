package it.rdev.blog.api.parameters;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import it.rdev.blog.api.dao.entity.User;

public class UserParameterResolver implements ParameterResolver {

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Parameter parameter = parameterContext.getParameter();
		return Objects.equals(
				parameter.getParameterizedType().getTypeName(),
				"java.util.List<it.rdev.blog.api.dao.entity.User>");
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		List<User> users = new ArrayList<>();
		User u = new User();
		u.setId(1);
		u.setPassword("password01");
		u.setUsername("username");
		users.add(u);
		return users;
	}

}
