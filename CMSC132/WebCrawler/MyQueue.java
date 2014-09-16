import java.util.LinkedList;

public class MyQueue<T> {

	private int size;
	// private T element;

	private LinkedList<T> queue;

	public MyQueue() {
		queue = new LinkedList<T>();
	}

	public synchronized int size() {
		this.size = queue.size();
		return size;
	}

	/**
	 * This method adds an item to the end of the queue.
	 */
	public synchronized void enqueue(Object o) {
		queue.addLast((T) o);
		this.notifyAll();
	}

	/**
	 * This method removes all items from the queue.
	 */
	public synchronized void clear() {
		while (!queue.isEmpty()) {
			queue.remove();
		}
	}

	/**
	 * This method removes an object from the end opposite of that to which
	 * things are added.If the queue is empty, the thread will wait here until
	 * an item becomes available
	 * 
	 * @return object removed
	 */
	public synchronized Object dequeue() {
		while (queue.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		return queue.removeFirst();
	}
}
