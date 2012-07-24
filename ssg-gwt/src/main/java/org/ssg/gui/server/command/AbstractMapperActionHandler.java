package org.ssg.gui.server.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.server.command.datamap.DataMappingConfiguration;
import org.ssg.gui.server.command.datamap.DataMappingSupport;

/**
 * Abstract action handler with data mapping support.
 * 
 * @see DataMappingSupport
 */
public abstract class AbstractMapperActionHandler<R extends Response, A extends Action<R>>
		implements ActionHandler<R, A>, DataMappingSupport {

	@Autowired(required = true)
	@Qualifier("OricaDataMapping")
	private DataMappingSupport mapper;

	public <S, D> D map(S source, D destination) {
		return mapper.map(source, destination);
	}

	public void setDataMappingConfiguration(DataMappingConfiguration config) {
		mapper.setDataMappingConfiguration(config);
	}
}
