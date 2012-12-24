package org.ssg.gui.client.task.presenter;

import java.util.HashMap;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.ExerciseType;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.task.view.ExerciseView;

@RunWith(MockitoJUnitRunner.class)
public class DefaultExerciseViewProviderTest extends AbstractPresenterTestCase {

	private DefaultExerciseViewProvider provider;
	
	@Mock
	private GenericExercisePresenter genericExercisePresenter;
	
	@Mock
	private ExerciseView exerciseView;
	
	@Mock
	private ExerciseView notFoundView;
	
	@Before
	public void setUp() {
		HashMap<ExerciseType, ExerciseView> map = new HashMap<ExerciseType, ExerciseView>();
		map.put(ExerciseType.GENERIC, exerciseView);
		provider = new DefaultExerciseViewProvider(map, notFoundView);
	}
	
	@Test
	public void givenGenericExerciseTypeThenCorresondedViewIsReturned() {
		// When
		ExerciseView view = provider.getExerciseView(ExerciseType.GENERIC);
		
		// Then
		Assert.assertThat(view, CoreMatchers.is(exerciseView));
	}
	
	@Test
	public void givenUnknownExerciseTypeThenCorresondedViewIsReturned() {
		HashMap<ExerciseType, ExerciseView> map = new HashMap<ExerciseType, ExerciseView>();
		provider = new DefaultExerciseViewProvider(map, notFoundView);
		
		// When
		ExerciseView view = provider.getExerciseView(ExerciseType.GENERIC);
		
		// Then
		Assert.assertThat(view, CoreMatchers.is(notFoundView));
	}

}
