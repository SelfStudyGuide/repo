package org.ssg.core.domain.adapter;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

import org.junit.Test;

public class TopicTaskInfoTest {
	@Test
	public void test() {
		
	}
	
	public class MyCustomMapper extends ConfigurableMapper {

		   @Override
		   public void configure(MapperFactory mapperFactory) {

		   }
		}
}
