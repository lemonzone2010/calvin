package pattern;

import java.util.Observable;
import java.util.Observer;

import java.util.Observable;

public class ObserverTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Product p=new Product();
		Event event=Event.newEvent(p);
		Event event1=Event.newEvent(p);
	}

}
class Product {
	
}
class EventDispatcher implements Observer {
	static EventDispatcher dispatcher=new EventDispatcher();
	public static EventDispatcher getDispatcher() {
		return dispatcher;
	}
	@Override
	
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println(o+","+arg);

	}

}

class Event extends Observable {
	Product source;
	static Event newEvent(Product source) {
		Event event=new Event();
		event.source=source;
		event.addObserver(EventDispatcher.getDispatcher());
		event.setChanged();
		event.notifyObservers();
//		event.notifyObservers(source);
		return event;
	}
}