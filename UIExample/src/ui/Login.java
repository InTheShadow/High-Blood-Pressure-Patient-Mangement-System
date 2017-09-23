package ui;

import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;

import experi.dao.*;
import experi.entity.*;
import ui.RegOrChange.Role;

public class Login {
	protected Shell shell;
	protected Display display;
	protected boolean doc_selected;
	protected static Doctor logDoc;
	protected static Patient logPat;
	private Text userEnter;
	private Text passwordEnter;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Login window = new Login();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	protected boolean cheekDatabase(String user_name,String password){
		boolean find = false;
		//...检查数据库中有无该用户名和密码
		if(doc_selected){
			DoctorDao dao = new DoctorDao();
			Doctor doctor = dao.findByUserName(user_name.trim());
			if(doctor != null && password.equals(doctor.getPassword())) find = true;
			logDoc = doctor;
		}
		
		else{
			PatientDao dao = new PatientDao();
			Patient patient = dao.findByUserName(user_name.trim());
			if(patient != null && password.equals(patient.getPassword())) find = true;
			logPat = patient;
		}

		return find;
	}
	public void open() {
		doc_selected = true;
		display = Display.getDefault();
		createContents();
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - shell.getSize().y/2);  
		
		Label label = new Label(shell, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label.setBounds(82, 10, 253, 26);
		label.setText("\u6B22\u8FCE\u4F7F\u7528\u9AD8\u8840\u538B\u60A3\u8005\u7BA1\u7406\u7CFB\u7EDF\uFF01");
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
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		shell.setSize(436, 302);
		shell.setText("\u7528\u6237\u767B\u5F55");
	    
	    Group group = new Group(shell, SWT.NONE);
	    group.setText("\u89D2\u8272\u9009\u62E9");
	    group.setBounds(37, 42, 321, 213);
	    
	    Button loginLogin = new Button(group, SWT.NONE);
	    loginLogin.setLocation(36, 158);
	    loginLogin.setSize(98, 30);
	    loginLogin.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		String userName = userEnter.getText().trim();
	    		if(!(cheekDatabase(userName,passwordEnter.getText()))){
	    			MessageBox messagebox = new MessageBox(shell);
	    			messagebox.setMessage("您的用户名或密码错误！");
	    			messagebox.open();
	    		}
	    		else {
		    		display.close();
		    		if(doc_selected){ 
		    			DocFrame.patients = null;
		    			DocFrame.main(null);
		    		}
		    		else {
		    			PatFrame.advices = null;
		    			PatFrame.doc = null;
		    			PatFrame.main(null);
		    		}
	    		}
	    	}
	    });
	    loginLogin.setText("\u767B\u9646");
	    
	    
	    Button loginAssign = new Button(group, SWT.NONE);
	    loginAssign.setLocation(187, 158);
	    loginAssign.setSize(98, 30);
	    loginAssign.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
				RegOrChange.RoleEnabled = Role.all;
				display.close();
	    		RegOrChange.main(null);
	    	}
	    });
	    loginAssign.setText("\u6CE8\u518C");
	    
	    Button doctor = new Button(group, SWT.RADIO);
	    doctor.setLocation(187, 36);
	    doctor.setSize(119, 20);
	    doctor.setTouchEnabled(true);
	    doctor.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		if(doc_selected) doc_selected = false;
	    	}
	    });
	    doctor.setText("\u9AD8\u8840\u538B\u60A3\u8005");
	    
	    Button patient = new Button(group, SWT.RADIO);
	    patient.setSelection(true);
	    patient.setLocation(36, 36);
	    patient.setSize(119, 20);
	    patient.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		if(!doc_selected) doc_selected = true;
	    	}
	    });
	    patient.setText("\u793E\u533A\u533B\u751F");
	    
	    userEnter = new Text(group, SWT.BORDER);
	    userEnter.setBounds(142, 74, 141, 23);
	    
	    Label userName = new Label(group, SWT.NONE);
	    userName.setBounds(63, 77, 73, 20);
	    userName.setText("\u7528\u6237\u540D\uFF1A");
	    
	    passwordEnter = new Text(group, SWT.BORDER|SWT.PASSWORD);
	    passwordEnter.setBounds(142, 114, 141, 20);
	    
	    Label password = new Label(group, SWT.NONE);
	    password.setBounds(58, 114, 76, 20);
	    password.setText("  \u5BC6\u7801\uFF1A");

	}
}
