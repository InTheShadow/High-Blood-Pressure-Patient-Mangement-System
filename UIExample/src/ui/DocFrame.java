package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import experi.dao.*;
import experi.entity.*;
import ui.RegOrChange.Role;
public class DocFrame {

	protected Shell shell;
	protected Display display;
	protected List patList;
	protected static String patUserName;
	protected static Patient patients[];
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DocFrame window = new DocFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void refresh(){
		patList.removeAll();

		if(patients == null) {
			DoctorDao dao = new DoctorDao(); 
			patients = dao.findPatients(Login.logDoc.getId());
		}
		if(patients == null) patients = new Patient[0];
		else {
			for(int i=0;i<patients.length;i++) patList.add(patients[i].getUserName()+"（"+patients[i].getName()+"）");
		}
		//更新医生负责的患者名单
	}
	
	protected void deleteFromDatabase(String userName){
		//更新医生负责的患者名单
		PatientDao dao = new PatientDao();
		dao.removeDocId(userName);
		if(DocFrame.patients.length == 1) {
			DocFrame.patients = new Patient[0];
		}
		else{
			Patient tempPatients[] = DocFrame.patients;
			DocFrame.patients = new Patient[tempPatients.length-1];
			int j = 0;
			for(int i = 0;i<tempPatients.length;i++) {
				if(!tempPatients[i].getUserName().equals(userName)) {
					DocFrame.patients[j] = tempPatients[i];
					j++;
				}
			}
		}
		refresh();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - shell.getSize().y/2); 
		

		
		
		
		Button btnBack = new Button(shell, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.close();
	    		Login.main(null);
			}
		});
		btnBack.setBounds(309, 190, 102, 30);
		btnBack.setText("\u8FD4\u56DE\u767B\u5F55\u754C\u9762");
		
		patList = new List(shell, SWT.BORDER|SWT.V_SCROLL);
		patList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(patList.getSelectionCount() > 0)
					patUserName = patList.getSelection()[0].split("（")[0];
			}
		});
		patList.setBounds(10, 33, 278, 199);
		this.refresh();
		
		Button btnAddPat = new Button(shell, SWT.NONE);
		btnAddPat.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PatAdd.main(null);
			}
		});
		btnAddPat.setBounds(309, 46, 98, 30);
		btnAddPat.setText(" \u6DFB\u52A0\u60A3\u8005");
		
		Label docLabel = new Label(shell, SWT.NONE);
		docLabel.setBounds(10, 10, 224, 20);
		docLabel.setText("\u60A3\u8005\u540D\u5355\uFF1A\uFF08\uFF09\u5185\u4E3A\u771F\u5B9E\u59D3\u540D");
		
		Button btnRead = new Button(shell, SWT.NONE);
		btnRead.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(patList.getSelectionCount() == 0){
	    			MessageBox messagebox = new MessageBox(shell);
	    			messagebox.setMessage("您未选择一名患者！");
	    			messagebox.open();
				}
				else{
					PatientDao dao = new PatientDao();
					Login.logPat = dao.findByUserName(patUserName);
					PatBody.bodies = null;
					display.close();
					PatBody.main(null);
				}
			}
		});
		btnRead.setBounds(309, 118, 98, 30);
		btnRead.setText("\u9605\u8BFB\u4E0E\u5EFA\u8BAE");
		
		Button btnChange = new Button(shell, SWT.NONE);
		btnChange.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RegOrChange.RoleEnabled = Role.doctor;
				display.close();
	    		RegOrChange.main(null);
				
			}
		});
		btnChange.setBounds(313, 154, 98, 30);
		btnChange.setText("\u4FEE\u6539\u4E2A\u4EBA\u4FE1\u606F");
		
		Button btnDelete = new Button(shell, SWT.NONE);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(patList.getSelectionCount() == 0){
	    			MessageBox messagebox = new MessageBox(shell);
	    			messagebox.setMessage("您未选择一名患者！");
	    			messagebox.open();
				}
				else{
	    			MessageBox messagebox = new MessageBox(shell);
	    			messagebox.setMessage("删除患者成功!");
	    			messagebox.open();
					deleteFromDatabase(patUserName);
				}
			}
		});
		btnDelete.setText("\u5220\u9664\u60A3\u8005");
		btnDelete.setBounds(309, 82, 98, 30);
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
		shell.setTouchEnabled(true);
		shell.setSize(450, 300);
		shell.setText("\u533B\u751F\u7AEF");

	}
}
