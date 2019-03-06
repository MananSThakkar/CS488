package Project1_RUDP;
public class CircularQueueTester {

	public static void main(String[] args) {

	    CircularQueue<Integer> circularQueue = new CircularQueue(9);

	    circularQueue.enqueue(1);
	    circularQueue.enqueue(2);
	    circularQueue.enqueue(3);
	    circularQueue.enqueue(4);
	    circularQueue.enqueue(5);
	    circularQueue.enqueue(6);
	    circularQueue.enqueue(7);
	    circularQueue.enqueue(8);
	    circularQueue.enqueue(9);
	    
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(10);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(11);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(12);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(13);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(14);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(15);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(16);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(17);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(18);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(19);
	    System.out.println(circularQueue);
	    circularQueue.dequeue();
	    System.out.println(circularQueue);
	    circularQueue.enqueue(20);
	    System.out.println(circularQueue);
	    
	}


	}


