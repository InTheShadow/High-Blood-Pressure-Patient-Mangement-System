package ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import experi.dao.*;

public class PatHis {

	protected Shell shell;
	protected Display display;
	private Text textHistory;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PatHis window = new PatHis();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void sendToDataBase(){
		PatientDao dao = new PatientDao();
		Login.logPat.setHistory(textHistory.getText());
		dao.updatePatient(Login.logPat);
	}
	
	protected void refresh(){
		if(Login.logPat.getHistory() != null) textHistory.setText(Login.logPat.getHistory());
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
		shell.setText("\u60A3\u8005\u75C5\u53F2");
		shell.setSize(450, 300);
		
		textHistory = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textHistory.setBounds(31, 33, 322, 168);
		
		Label lblHistory = new Label(shell, SWT.NONE);
		lblHistory.setText("\u4FEE\u6539\u75C5\u53F2\uFF1A");
		lblHistory.setBounds(31, 10, 76, 20);
		
		Button btnClick = new Button(shell, SWT.NONE);
		btnClick.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sendToDataBase();
    			MessageBox messagebox = new MessageBox(shell);
    			messagebox.setMessage("您的病史已提交！");
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
				PatFrame.main(null);
			}
		});
		btnBack.setText("\u8FD4\u56DE");
		btnBack.setBounds(243, 213, 76, 30);
		
		this.refresh();

	}
}
