package ui;

import java.sql.Timestamp;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import experi.entity.*;
import experi.dao.*;

public class PatAdvice {

	protected Shell shell;
	protected Display display;
	private Text txtAdvice;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PatAdvice window = new PatAdvice();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void sendToDataBase(){
		java.util.Date currentDateTime = new java.util.Date();
		Advice advice = new Advice();
		advice.setDoc_id(Login.logDoc.getId());
		advice.setPat_id(Login.logPat.getId());
		advice.setAdvice_value(txtAdvice.getText());
		advice.setAdvice_recordTime(new Timestamp(currentDateTime.getTime()));
		AdviceDao dao = new AdviceDao();
		dao.insertAdvice(advice);
	}

	/**
	 * Open the window.
	 */
	public void open() {
	    display = Display.getDefault();
		createContents();
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - shell.getSize().y/2); 
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setText("\u7ED9\u60A3\u8005\u7684\u5EFA\u8BAE");
		shell.setSize(450, 300);
		
		txtAdvice = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtAdvice.setBounds(31, 33, 322, 168);
		
		Label adviceLabel = new Label(shell, SWT.NONE);
		adviceLabel.setText("\u533B\u751F\u5EFA\u8BAE:");
		adviceLabel.setBounds(31, 10, 76, 20);
		
		Button btnClick = new Button(shell, SWT.NONE);
		btnClick.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sendToDataBase();
    			MessageBox messagebox = new MessageBox(shell);
    			messagebox.setMessage("您给出的建议已提交！");
    			messagebox.open();
			}
		});
		btnClick.setText("\u786E\u8BA4");
		btnClick.setBounds(80, 213, 76, 30);
		
		Button btnBack = new Button(shell, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.close();
				DocFrame.main(null);
			}
		});
		btnBack.setText("\u8FD4\u56DE");
		btnBack.setBounds(243, 213, 76, 30);

	}
}
