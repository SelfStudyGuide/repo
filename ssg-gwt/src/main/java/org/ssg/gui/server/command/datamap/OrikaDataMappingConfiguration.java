package org.ssg.gui.server.command.datamap;

import ma.glasnost.orika.MapperFactory;

/**
 * http://code.google.com/p/orika/wiki/GettingStartedGuide
 */
public interface OrikaDataMappingConfiguration extends DataMappingConfiguration {

	void copfigureDataMapper(MapperFactory factory);

}
