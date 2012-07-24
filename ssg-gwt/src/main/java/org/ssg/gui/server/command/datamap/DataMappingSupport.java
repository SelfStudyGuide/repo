package org.ssg.gui.server.command.datamap;

/**
 * Provides data mappings facilities.
 */
public interface DataMappingSupport {

	/**
	 * Copies properties from source bean to destination one.
	 * 
	 * @param source source bean
	 * @param destination destination bean
	 * @return populated destination bean
	 */
	<S, D> D map(S source, D destination);

	void setDataMappingConfiguration(DataMappingConfiguration config);
}
