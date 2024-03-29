package jdraw.test;

import jdraw.decorators.BorderDecorator;
import jdraw.figures.Rect;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureListener;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class DecoratorTest {

	private Figure f;

	@BeforeEach
	public void setUp(){
		f = new BorderDecorator(new Rect(1,1,20,10));
	}

	@Test
	public void testNotification1() {
		FigureListener l = mock(FigureListener.class);
		f.addFigureListener(l);
		f.move(1, 1);
		verify(l, times(1).description("figureChanged must be called on a registered listener")).notifyObservers(any());

		f.removeFigureListener(l);
		f.move(2, 2);
		verify(l, times(1).description("figureChanged must not be called on disconnected listener")).notifyObservers(any());
	}

	@Test
	public void testNotification2() {
		FigureListener l = mock(FigureListener.class);
		f.addFigureListener(l);
		f.move(0, 0);

		verify(l, never().description("figureChanged must not be called if the state did not change"))
				.notifyObservers(any());
	}

	@Test
	public void testNotification3() {
		FigureListener l = mock(FigureListener.class);

		f.addFigureListener(l);
		f.move(1, 1);

		ArgumentCaptor<FigureEvent> event = ArgumentCaptor.forClass(FigureEvent.class);
		verify(l, times(1).description("figureChanged must be called on a registered listener")).notifyObservers(event.capture());
		assertSame(f, event.getValue().getFigure(), "getFigure must return the figure which was changed");
	}

	@Test
	final public void testMultiListeners() {
		FigureListener l1 = mock(FigureListener.class);
		FigureListener l2 = mock(FigureListener.class);

		f.addFigureListener(l1);
		f.addFigureListener(l2);
		f.move(3, 3);

		verify(l1, times(1).description("multiple listeners are not supported")).notifyObservers(any());
		verify(l2, times(1).description("multiple listeners are not supported")).notifyObservers(any());
	}

	@Test
	final public void testMultiListeners2() {
		FigureListener l = mock(FigureListener.class);

		f.addFigureListener(l);
		f.addFigureListener(l);
		f.move(3, 3);
		verify(l, times(1).description("set semantics of addFigureListener violated")).notifyObservers(any());
		f.removeFigureListener(l);
		f.removeFigureListener(l);
		f.move(3, 3);
		verify(l, times(1).description("set semantics of addFigureListener violated")).notifyObservers(any());
	}

	@Test
	final public void testRemoveListener() {
		FigureListener removeListener = mock(FigureListener.class);
		Answer<Void> removeFigureChanged = (invocation) -> {
			FigureEvent e = invocation.getArgument(0, FigureEvent.class);
			e.getFigure().removeFigureListener(removeListener);
			return null;
		};
		doAnswer(removeFigureChanged).when(removeListener).notifyObservers(any());

		f.addFigureListener(mock(FigureListener.class));
		f.addFigureListener(removeListener);
		f.addFigureListener(mock(FigureListener.class));
		f.addFigureListener(mock(FigureListener.class));
		f.move(4, 4);
	}

	@Test
	final public void testNullListener() {
		f.addFigureListener(null);
		f.move(1, 1); // no exception should be thrown upon notification
	}

	@Test
	final public void testCycle() {
		Figure f1 = f;
		Figure f2 = new Rect(10, 10);
		f1.addFigureListener(new UpdateListener(f2));
		f2.addFigureListener(new UpdateListener(f1));

		f2.move(5, 5);
		assertEquals(f1.getBounds().getLocation(), f2.getBounds().getLocation(),
				"Position of the two figures must be equal");
		assertEquals(15, f1.getBounds().x, "Figures must both be at position x=15");
		assertEquals(15, f1.getBounds().y, "Figures must both be at position y=15");

		f1.move(5, 5);
		assertEquals(f1.getBounds().getLocation(), f2.getBounds().getLocation(),
				"Position of the two figures must be equal");
		assertEquals(20, f1.getBounds().x, "Figures must both be at position x=20");
		assertEquals(20, f1.getBounds().y, "Figures must both be at position y=20");
	}

	static class UpdateListener implements FigureListener {
		private final Figure f;

		public UpdateListener(Figure f) {
			this.f = f;
		}

		@Override
		public void notifyObservers(FigureEvent e) {
			Point p1 = e.getFigure().getBounds().getLocation();
			Point p2 = f.getBounds().getLocation();
			f.move(p1.x - p2.x, p1.y - p2.y);
		}
	}

}
