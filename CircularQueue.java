package Project1_RUDP;


import java.util.Arrays;

public class CircularQueue<E> {
		
		  private final int DEFAULT_SIZE = 10; // default capacity
		  private E[] queue;              // array that holds queue elements
		  private int capacity;    // number of elements n the queue
		  private int front;          // index of front of queue
		  private int rear;               // index of rear of queue
		  
		  @SuppressWarnings("unchecked")
		public CircularQueue(){
			  front = -1;
			  rear = DEFAULT_SIZE - 1;
			  capacity = 0;
			  queue = (E[]) new Object[DEFAULT_SIZE]; 
		  }
		  
		  @SuppressWarnings("unchecked")
		public CircularQueue(int initSize){
			  front = -1;
			  rear = initSize - 1;
			  capacity = 0;
			  queue = (E[]) new Object[initSize]; 
		  }
		  

		public E dequeue() throws QueueUnderflowException {
			  // Throws QueueUnderflowException if this queue is empty;
			  // otherwise, removes front element from this queue and returns it.
			if (isEmpty())
				throw new QueueUnderflowException("Dequeue attempted on empty queue.");
			else    {
				E toDequeue = queue[front];
			    queue[front] = null;
			    front = (front + 1) % queue.length;
			    capacity --;
			    return toDequeue;
			}
		}

	
		public boolean isEmpty() {
			
			return capacity == 0;
		}


		public void enqueue(E element) throws QueueOverflowException {
		// Throws QueueOverflowException if this queue is full;
		// otherwise, adds element to the rear of this queue.
			if (isFull())
				throw new QueueOverflowException("Enqueue attempted on a full queue.");
			else   {
				rear = (rear + 1) % queue.length;
				queue[rear] = element;
				capacity++;
				if(front == -1) {
					front=rear;
				}
			    }		
		}

		public boolean isFull() {
			
			return capacity == queue.length;
		}
		
		public String toString() {
	        return "CircularQueue [" + Arrays.toString(queue) + "]";
	    }
		
}
