package ui;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import experi.dao.*;
import experi.entity.*;
import org.eclipse.swt.widgets.Spinner;

public class RegOrChange {

	protected Shell shell;
	protected Display display;
	enum Role{patient,doctor,all};
	protected static  Role RoleEnabled;
	protected boolean docFrame;
	private Text textUser;
	private Text textPass;
	private Text textPassSet;
	private boolean reg;
	private Combo cboRole;
	private Text textAddress;
	private Label lblAddress;
	private Text textName;
	private Spinner spinAge;
	private Button btnTrue; 
	private Button btnFalse;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//RoleEnabled = Role.all;
			RegOrChange window = new RegOrChange();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected boolean cheekUserName(String user_name){
		boolean find = false;
		//...检查数据库中有无相同用户名
		if(docFrame){
			DoctorDao dao = new DoctorDao();
			Doctor doctor = dao.findByUserName(user_name.trim());
			find = (doctor == null) ? false:true;
		}

		return find;
	}
	
	protected Doctor getDoctor(){
		Doctor doc = new Doctor();
		String doc_userName = textUser.getText().trim();
		String doc_name = textName.getText().trim();
		String doc_password = textPass.getText().trim();
		int doc_age = spinAge.getSelection();
		boolean doc_sex = btnTrue.getSelection();
		String doc_hospital = textAddress.getText().trim();
		boolean can_get = false;
		if((!doc_userName.equals("")) && (!doc_name.equals("")) && (!doc_password.equals(""))){
			can_get = true;
			doc.setName(doc_name);
			doc.setUserName(doc_userName);
			doc.setPassword(doc_password);
		}
		if(!can_get) return null;
		if(doc_age != 0) doc.setAge(doc_age);
		if(!doc_hospital.equals("")) doc.setHospital(doc_hospital);
		doc.setSex(doc_sex);
		return doc;
		
	}
	
	protected Patient getPatient(){
		Patient pat = new Patient();
		String pat_userName = textUser.getText().trim();
		String pat_name = textName.getText().trim();
		String pat_password = textPass.getText().trim();
		int pat_age = spinAge.getSelection();
		boolean pat_sex = btnTrue.getSelection();
		String pat_address = textAddress.getText().trim();
		boolean can_get = false;
		if((!pat_userName.equals("")) && (!pat_name.equals("")) && (!pat_password.equals(""))){
			can_get = true;
			pat.setName(pat_name);
			pat.setUserName(pat_userName);
			pat.setPassword(pat_password);
		}
		if(!can_get) return null;
		if(pat_age != 0) pat.setAge(pat_age);
		if(!pat_address.equals("")) pat.setAddress(pat_address);
		pat.setSex(pat_sex);
		return pat;
		
	}
	
	protected void setDoctor(Doctor doc){
		textUser.setText(doc.getName());
		textName.setText(doc.getName());
		textPass.setText(doc.getPassword());
		textPassSet.setText(doc.getPassword());
		textAddress.setText(doc.getHospital() == null ? "":doc.getHospital());
		btnTrue.setSelection(doc.getSex());
		btnFalse.setSelection(!doc.getSex());
		spinAge.setSelection(doc.getAge());
	}
	
	protected void setPatient(Patient pat){
		textUser.setText(pat.getName());
		textName.setText(pat.getName());
		textPass.setText(pat.getPassword());
		textPassSet.setText(pat.getPassword());
		textAddress.setText(pat.getAddress() == null ? "":pat.getAddress());
		btnTrue.setSelection(pat.getSex());
		btnFalse.setSelection(!pat.getSex());
		spinAge.setSelection(pat.getAge());
	}
	
	protected boolean inputDatabase(String user_name,String password){
		if(cheekUserName(user_name)&&reg) return false;
		else if(reg){
			if(docFrame){
				Doctor doc = getDoctor();
				if(doc == null) return false;
				DoctorDao dao = new DoctorDao();
				dao.insertDoctor(doc);
				Login.logDoc = doc;
			}
			else{
				Patient pat = getPatient();
				if(pat == null) return false;
				PatientDao dao = new PatientDao();
				dao.insertPatient(pat);
				Login.logPat = pat;
			}
			return true;
		}
		else{
			if(docFrame){
				Doctor doc = getDoctor();
				if(doc == null) return false;
				DoctorDao dao = new DoctorDao();
				dao.updateDoctor(doc);
			}
			else{
				Patient pat = getPatient();
				if(pat == null) return false;
				PatientDao dao = new PatientDao();
				dao.updatePatient(pat);
			}
			return true;
		}
	}

	/**
	 * Open the window.
	 */
	private void setWindowType(){
		switch(RoleEnabled){
		case doctor:
			cboRole.add("医生");
			shell.setText("医生修改信息");
			textUser.setEditable(false);
			docFrame = true;
			reg = false;
			break;
		case patient:
			cboRole.add("患者");
			shell.setText("患者修改信息");
			textUser.setEditable(false);
			docFrame = false;
			reg = false;
			break;
		case all:
			cboRole.add("医生");
			cboRole.add("患者");
			shell.setText("注册");
			textUser.setText("");
			reg = true;
		}
		cboRole.select(0);
		if(!reg){
			if(docFrame){
				Doctor doctor = Login.logDoc;
				if(doctor != null){
					setDoctor(doctor);
				}

			}
			else{
				Patient patient = Login.logPat;
				if(patient != null){
					setPatient(patient);
				}
			}
		}
	}
	
	
	
	public void open() {
	    display = Display.getDefault();
		createContents();
	    Composite comMes = new Composite(shell, SWT.NONE);
		comMes.setBounds(21, 77, 434, 354);
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - shell.getSize().y/2); 
		
		Label lblUser = new Label(comMes, SWT.NONE);
		lblUser.setBounds(41, 32, 76, 20);
		lblUser.setText("\u7528\u6237\u540D\uFF1A");
		
		Label lblPass = new Label(comMes, SWT.NONE);
		lblPass.setText("\u5BC6\u7801\uFF1A");
		lblPass.setBounds(41, 107, 76, 20);
		
		Label lblPassSet = new Label(comMes, SWT.NONE);
		lblPassSet.setText("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		lblPassSet.setBounds(41, 150, 76, 20);
		
		textUser = new Text(comMes, SWT.BORDER);
		textUser.setBounds(117, 29, 162, 26);
		
		textPass = new Text(comMes, SWT.BORDER | SWT.PASSWORD);
		textPass.setBounds(117, 104, 162, 26);
		
		textPassSet = new Text(comMes, SWT.BORDER | SWT.PASSWORD);
		textPassSet.setBounds(117, 147, 162, 26);
		
		Label lblStar = new Label(comMes, SWT.NONE);
		lblStar.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblStar.setBounds(31, 3, 9, 20);
		lblStar.setText("*");
		
		Label lblMes = new Label(comMes, SWT.NONE);
		lblMes.setBounds(41, 3, 108, 20);
		lblMes.setText("\u8868\u793A\u5FC5\u586B\u4FE1\u606F");
		
		Label lblStar2 = new Label(comMes, SWT.NONE);
		lblStar2.setText("*");
		lblStar2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblStar2.setBounds(31, 32, 9, 20);
		
		Label lblStar3 = new Label(comMes, SWT.NONE);
		lblStar3.setText("*");
		lblStar3.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblStar3.setBounds(31, 107, 9, 20);
		
		Label lblStar4 = new Label(comMes, SWT.NONE);
		lblStar4.setText("*");
		lblStar4.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblStar4.setBounds(31, 150, 9, 20);
		
		Label lblSex = new Label(comMes, SWT.NONE);
		lblSex.setText("\u6027\u522B\uFF1A");
		lblSex.setBounds(41, 193, 76, 20);
		
		Label lblAge = new Label(comMes, SWT.NONE);
		lblAge.setText("\u5E74\u9F84\uFF1A");
		lblAge.setBounds(41, 230, 76, 20);
		
		lblAddress = new Label(comMes, SWT.NONE);
		lblAddress.setText("\u6240\u5C5E\u533B\u9662\uFF1A");
		lblAddress.setBounds(41, 271, 76, 20);
		
		textAddress = new Text(comMes, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		textAddress.setBounds(117, 271, 291, 52);
		
		Label lblName = new Label(comMes, SWT.NONE);
		lblName.setText("\u59D3\u540D\uFF1A");
		lblName.setBounds(41, 64, 76, 20);
		
		textName = new Text(comMes, SWT.BORDER);
		textName.setBounds(117, 61, 162, 26);
		
		Label labelStar5 = new Label(comMes, SWT.NONE);
		labelStar5.setText("*");
		labelStar5.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		labelStar5.setBounds(31, 61, 9, 20);
		
		spinAge = new Spinner(comMes, SWT.BORDER);
		spinAge.setMaximum(200);
		spinAge.setBounds(117, 222, 57, 26);
		
		Composite comSex = new Composite(comMes, SWT.NONE);
		comSex.setBounds(109, 182, 194, 31);
		
		btnTrue = new Button(comSex, SWT.RADIO);
		btnTrue.setSelection(true);
		btnTrue.setBounds(10, 10, 66, 21);
		btnTrue.setText("\u7537");
		
		btnFalse = new Button(comSex, SWT.RADIO);
		btnFalse.setText("\u5973");
		btnFalse.setBounds(118, 10, 66, 21);
		
		Button btnClick = new Button(shell, SWT.NONE);
		btnClick.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!textPass.getText().equals(textPassSet.getText())){
	    			MessageBox messagebox = new MessageBox(shell);
	    			messagebox.setMessage("您的密码两次输入不一致！");
	    			messagebox.open();
	    			return ;
				}
				if(reg){
				    docFrame = cboRole.getText().equals("医生");
					if(!(inputDatabase(textUser.getText(), textPass.getText()))){
		    			MessageBox messagebox = new MessageBox(shell);
		    			messagebox.setMessage("您的名字已被占用或您的必填信息未填！");
		    			messagebox.open();
					}
					else{
		    			MessageBox messagebox = new MessageBox(shell);
		    			messagebox.setMessage("注册成功！");
		    			messagebox.open();
					}
				}
				else{
					if(!(inputDatabase(textUser.getText(), textPass.getText()))){
		    			MessageBox messagebox = new MessageBox(shell);
		    			messagebox.setMessage("您的用户信息未找到！");
		    			messagebox.open();
					}
					else{
		    			MessageBox messagebox = new MessageBox(shell);
		    			messagebox.setMessage("修改成功!");
		    			messagebox.open();
					}
				}
			}
		});
		btnClick.setBounds(47, 451, 98, 30);
		btnClick.setText("\u786E\u8BA4");
		
		Button btnBack = new Button(shell, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(reg){
					display.close();
		    		Login.main(null);
				}
				else{
					display.close();
					if(docFrame) DocFrame.main(null);
					else PatFrame.main(null);
				}
			}
		});
		btnBack.setText("\u8FD4\u56DE");
		btnBack.setBounds(276, 451, 98, 30);
		
		setWindowType();
		
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
		shell.setSize(483, 538);
		shell.setText("SWT Application");
		
		Composite comRole = new Composite(shell, SWT.NONE);
		comRole.setBounds(21, 10, 438, 61);
		
	    cboRole = new Combo(comRole, SWT.NONE);
	    cboRole.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		if(cboRole.getSelectionIndex() == cboRole.indexOf("医生")) {
	    			lblAddress.setText("所属医院");
	    			docFrame = true;
	    			
	    		}
	    		else if(cboRole.getSelectionIndex() == cboRole.indexOf("患者")) {
	    			lblAddress.setText("家庭地址");
	    			docFrame = false;
	    		}
	    	}
	    });
		cboRole.setBounds(102, 14, 92, 28);
		cboRole.select(0);
		
		Label lblRole = new Label(comRole, SWT.NONE);
		lblRole.setBounds(10, 17, 76, 28);
		lblRole.setText("\u8D26\u6237\u7C7B\u522B\uFF1A");

	}
}
