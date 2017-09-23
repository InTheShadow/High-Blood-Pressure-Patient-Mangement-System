package ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import experi.dao.PatientDao;
import experi.entity.Doctor;
import experi.entity.Patient;

public class PatAdd {

	protected Shell shell;
	protected Display display;
	private Text patEnter;
	private Text patUserEnter;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PatAdd window = new PatAdd();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static Patient cheekPat(String user_name,String name){
		//...检查患者名和用户名是否在数据库中
		PatientDao dao = new PatientDao();
		Patient patient = dao.findByUserName(user_name.trim());
		if(patient != null && name.equals(patient.getName())) return patient;
		return null;
	}
	
	protected static boolean inputPat(String user_name,String name){
		Patient patient;
		if((patient = cheekPat(user_name,name))!=null) {
			Doctor doctor = Login.logDoc;
			if(doctor == null) return false;
			PatientDao patDao = new PatientDao();
			patDao.updateDocId(doctor.getId(),patient.getUserName());
			if(DocFrame.patients == null) {
				DocFrame.patients = new Patient[1];
				DocFrame.patients[0] = patient;
			}
			else{
				Patient tempPatients[] = DocFrame.patients;
				DocFrame.patients = new Patient[tempPatients.length+1];
				for(int i = 0;i<tempPatients.length;i++) DocFrame.patients[i] = tempPatients[i];
				DocFrame.patients[tempPatients.length] = patient;
			}
			return true;
		}
		else return false;
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
		shell.setSize(333, 216);
		shell.setText("\u6536\u5F55\u60A3\u8005");
		
		Label patLabel = new Label(shell, SWT.NONE);
		patLabel.setBounds(10, 77, 76, 20);
		patLabel.setText("\u60A3\u8005\u59D3\u540D\uFF1A");
		
		patEnter = new Text(shell, SWT.BORDER);
		patEnter.setBounds(108, 74, 173, 30);
		
		Label patUserLab = new Label(shell, SWT.NONE);
		patUserLab.setBounds(10, 37, 92, 20);
		patUserLab.setText("\u60A3\u8005\u7528\u6237\u540D\uFF1A");
		
		patUserEnter = new Text(shell, SWT.BORDER);
		patUserEnter.setBounds(108, 34, 173, 30);
		
		Button click = new Button(shell, SWT.NONE);
		click.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(inputPat(patUserEnter.getText(),patEnter.getText())){
	    			MessageBox messagebox = new MessageBox(shell);
	    			messagebox.setMessage("收录患者成功!");
 
	    			messagebox.open();
					display.close();
					DocFrame.main(null);
				}
				else{
	    			MessageBox messagebox = new MessageBox(shell);
	    			messagebox.setMessage("该患者不在数据库中！");
	    			messagebox.open();
					display.close();
					DocFrame.main(null);
				}
			}
		});
		click.setBounds(40, 125, 76, 30);
		click.setText("\u786E\u8BA4");
		
		Button cancel = new Button(shell, SWT.NONE);
		cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.close();
				DocFrame.main(null);
			}
		});
		cancel.setText("\u53D6\u6D88");
		cancel.setBounds(183, 125, 76, 30);

	}

}
