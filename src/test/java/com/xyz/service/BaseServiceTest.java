package com.xyz.service;

import org.junit.runner.RunWith;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Rollback(value = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/com/xyz/service/service-context.xml" })
@Transactional
public abstract class BaseServiceTest {
	@SuppressWarnings("unchecked")
	public <T> T getTargetObjectFromJDKProxy(Object object) throws Exception {
		return (T) (AopUtils.isJdkDynamicProxy(object) ? ((Advised) object).getTargetSource().getTarget() : object);
	}
}
