package Project1_RUDP;


@SuppressWarnings("serial")
public class QueueOverflowException extends RuntimeException {
	  public QueueOverflowException()  {
		    super(); //calling parent's default constructor.
		  }
		  public QueueOverflowException(String errorMessage)  {
		    super(errorMessage); //calling parent's constructor with parameter.
		  }
}
