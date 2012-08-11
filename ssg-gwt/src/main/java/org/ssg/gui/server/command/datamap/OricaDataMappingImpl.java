package org.ssg.gui.server.command.datamap;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Provides implementation of {@link DataMappingSupport}. Implementation is a
 * state-full. <br/>
 * Documentation: http://code.google.com/p/orika/wiki/GettingStartedGuide.
 */

@Service("OricaDataMapping")
@Scope("prototype")
public class OricaDataMappingImpl implements DataMappingSupport {

	private DefaultMapperFactory factory;
	private MappingContext context = new MappingContext();

	public OricaDataMappingImpl() {
		DefaultMapperFactory.Builder builder = new DefaultMapperFactory.Builder();
		factory = builder.build();
	}

	public <S, D> D map(S source, D destination) {
		factory.getMapperFacade().map(source, destination, context);
		return destination;
	}

	public void setDataMappingConfiguration(DataMappingConfiguration config) {
		if (config instanceof OrikaDataMappingConfiguration) {
			((OrikaDataMappingConfiguration) config).copfigureDataMapper(factory);
		}
	}

}
