package commonlib;


import java.io.UnsupportedEncodingException;


import org.testng.IExecutionListener;

public class ExecutionListener extends emailReport implements IExecutionListener  {
	private long startTime;


	//@Override
	public void onExecutionStart() {
		startTime = System.currentTimeMillis();
		
	}

	//@Override
	public void onExecutionFinish() {

		try {
			sendEmail();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}