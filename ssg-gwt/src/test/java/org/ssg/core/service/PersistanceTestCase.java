package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.ssg.core.service.PersistanceIntegrationTest.Value;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ "junit", "real-core", "junit-mock-security" })
@ContextConfiguration(locations = { "classpath:/spring/test-application.ctx.xml" })
@TransactionConfiguration(defaultRollback = false)
public class PersistanceTestCase {

	private static final Log LOG = LogFactory.getLog(PersistanceTestCase.class);

	private Map<Class<?>, Value> values;

	private Class<?> objectType;

	@Autowired
	@Qualifier("TestGenericDao")
	private TestGenericDao dao;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private PlatformTransactionManager transactionManager;

	private HibernateTemplate template;

	private TransactionTemplate transactionTemplate;

	private Object objectToTest;

	public PersistanceTestCase(Class<?> objectType, Map<Class<?>, Value> values) {
		LOG.info("Instantiating object type " + objectType);
		try {
			objectToTest = objectType.newInstance();
		} catch (InstantiationException e) {
			LOG.error("Cannot instantiate object type " + objectType, e);
			Assert.fail(e.getMessage());
		} catch (IllegalAccessException e) {
			LOG.error("Cannot instantiate object type " + objectType, e);
			Assert.fail(e.getMessage());
		}
		this.objectType = objectType;
		this.values = values;
	}

	public PersistanceTestCase(Object object, Map<Class<?>, Value> values) {
		this.objectType = object.getClass();
		this.objectToTest = object;
		this.values = values;
	}

	@Before
	public void setUp() {
		template = new HibernateTemplate(sessionFactory);
		transactionTemplate = new TransactionTemplate(transactionManager);

		transactionTemplate.execute(new TransactionCallback<Integer>() {
			public Integer doInTransaction(TransactionStatus status) {
				dao.deleteAllByType(objectType);
				return 0;
			}
		});
	}

	@After
	public void tearDown() {

	}

	@Test
	public void verifyThatObjectCanBePercisted() throws InstantiationException, IllegalAccessException {

		populateObject(objectToTest);

		LOG.info("Percisting object type " + objectType);
		saveObject(objectToTest);
	}

	@Test
	public void verifyThatObjectCanBeLoaded() throws InstantiationException, IllegalAccessException {

		populateObject(objectToTest);

		LOG.info("Percisting object type " + objectType);
		int id = saveObject(objectToTest);

		LOG.info("Loading and Asserting object type " + objectType + ", id " + id);
		loadObjectAndAssert(id, objectToTest);
	}

	@Test
	public void verifyThatObjectCanBeDeleted() throws InstantiationException, IllegalAccessException {

		populateObject(objectToTest);

		LOG.info("Percisting object type " + objectType);
		final int id = saveObject(objectToTest);

		LOG.info("Deleting object type " + objectType + ", id " + id);
		deleteObject(id);
	}

	private void deleteObject(final int id) {
		transactionTemplate.execute(new TransactionCallback<Integer>() {

			public Integer doInTransaction(TransactionStatus status) {
				dao.delete(id, objectType);
				return 0;
			}
		});
	}

	private void populateObject(Object objectToSave) throws IllegalAccessException {
		Class<?> objectToSaveType = objectToSave.getClass();

		LOG.info("Populating object type " + objectToSaveType + " with values");
		final Field[] fields = objectToSaveType.getDeclaredFields();

		for (Field field : fields) {
			if (!isPrimaryKey(field.getName()) && !skipField(field)) {
				field.setAccessible(true);
				Class<?> type = field.getType();
				LOG.info("Field " + field + " with type " + type);
				if (values.containsKey(type)) {

					Object value = values.get(type).createValue();
					field.set(objectToSave, value);

					LOG.info("Setting field " + field + " with value " + value);
				}

				if (type.equals(List.class)) {
					List<?> list = (List<?>) field.get(objectToSave);
					if (list != null) {
						LOG.info("Populating sub-objects for " + field);
						for (Object childObject : list) {
							populateObject(childObject);
						}
					} else {
						LOG.info("Field " + field + " is null");
					}
				}
			}
		}
	}

	protected boolean skipField(Field field) {
		return Modifier.isStatic(field.getModifiers());
	}

	private void loadObjectAndAssert(final Integer id, final Object expectedObject) throws IllegalAccessException {
		transactionTemplate.execute(new TransactionCallback<Integer>() {

			public Integer doInTransaction(TransactionStatus status) {
				try {
					Object loadedObject = dao.getById(id, objectType);
					assertObjects(expectedObject, loadedObject);
				} catch (Exception e) {
					Assert.fail("Fail to assert object " + objectType + ". " + e.getMessage());
				}
				return 0;
			}
		});

	}

	protected int saveObject(final Object objectToSave) {
		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			public Integer doInTransaction(TransactionStatus status) {
				return Integer.class.cast(template.save(objectToSave));
			}
		});

	}

	protected boolean isPrimaryKey(String name) {
		return name.equals("id");
	}

	protected void assertObjects(final Object expectedObject, Object actualObject) throws IllegalAccessException {
		Field[] fields = expectedObject.getClass().getFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object expectedVal = field.get(expectedObject);
			Object actualVal = field.get(actualObject);
			if (field.getType().equals(List.class)) {
				List<?> expectedList = List.class.cast(expectedVal);
				List<?> actualList = List.class.cast(actualVal);
				assertObjects(expectedList, actualList);
			} else {
				assertThat("Field: " + field.toString(), actualVal, is(expectedVal));
			}
		}
	}

	protected void assertObjects(List<?> expectedList, List<?> actualList) throws IllegalAccessException {
		assertThat("List size", actualList.size(), is(expectedList.size()));
		Iterator<?> ai = actualList.iterator();
		Iterator<?> ei = actualList.iterator();
		for (; ai.hasNext();) {
			assertObjects(ei.next(), ai.next());
		}
	}

}
