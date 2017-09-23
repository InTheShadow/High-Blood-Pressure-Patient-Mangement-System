package ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import experi.entity.*;
import experi.dao.*;
import ui.RegOrChange.Role;


public class PatFrame {

	protected Shell shell;
	protected Display display;
	protected static String userName;
	private Text txtAdvice;
	protected static Advice advices[];
	protected static Doctor doc;
	private Button btnEnter;
	private Button btnChange;
	private Button btnHis;
	private Label lblDoc;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PatFrame window = new PatFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static Patient cheekPat(String user_name){
		//...检查患者名和用户名是否在数据库中
		PatientDao dao = new PatientDao();
		Patient patient = dao.findByUserName(user_name.trim());
		return patient;
	}
	
	protected void refresh(){
		Patient patient = Login.logPat;
		DoctorDao dao = new DoctorDao();
		if(doc == null && patient.getDocId() > 0 ) {
			doc = dao.findById(patient.getDocId());
			Login.logDoc = doc;
		}
		if(doc != null)  lblDoc.setText("负责医生："+doc.getUserName()+"（"+doc.getName()+"）");
		else lblDoc.setText("负责医生：无");
		


		if(advices == null) {
			AdviceDao adDao = new AdviceDao();
			advices = adDao.findAdvices(patient.getId());
			if(advices == null) advices = new Advice[0];
			for(int i = 0;i<advices.length;i++) {
				advices[i].setDoc_name(dao.findById(advices[i].getDoc_id()).getName());
			}
		}
		if(advices != null){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int i = 0;i<advices.length;i++){
				txtAdvice.append("建议时间："+format.format(advices[i].getAdvice_recordTime())+"\n");
				txtAdvice.append("当时负责医生："+advices[i].getDoc_name()+"\n");
				txtAdvice.append("建议:\n"+advices[i].getAdvice_value()+"\n");
				txtAdvice.append("\n");
			}
		}

		//更新负责医生和医生的建议
	}

	/**
	 * Open the window.
	 */
	public void open() {
	    display = Display.getDefault();
		createContents();
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - shell.getSize().y/2); 
		
		btnEnter = new Button(shell, SWT.NONE);
		btnEnter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.close();
	    		PatEnter.main(null);
			}
		});
		btnEnter.setText("\u8F93\u5165\u8EAB\u4F53\u53C2\u6570");
		btnEnter.setBounds(49, 423, 102, 30);
		
		btnChange = new Button(shell, SWT.NONE);
		btnChange.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RegOrChange.RoleEnabled = Role.patient;
				display.close();
	    		RegOrChange.main(null);
			}
		});
		btnChange.setText("\u4FEE\u6539\u4E2A\u4EBA\u4FE1\u606F");
		btnChange.setBounds(157, 423, 98, 30);
		
		lblDoc = new Label(shell, SWT.NONE);
		lblDoc.setBounds(41, 10, 312, 20);
		lblDoc.setText("\u8D1F\u8D23\u533B\u751F\uFF1A");
		
		btnHis = new Button(shell, SWT.NONE);
		btnHis.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PatHis.main(null);
			}
		});
		btnHis.setText("\u4FEE\u6539\u4E2A\u4EBA\u75C5\u53F2");
		btnHis.setBounds(261, 423, 98, 30);
		shell.open();
		shell.layout();
		this.refresh();
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
		shell.setSize(625, 516);
		shell.setText("\u60A3\u8005\u7AEF");
		
		Button btnBack = new Button(shell, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.close();
	    		Login.main(null);
			}
		});
		btnBack.setText(" \u8FD4\u56DE\u767B\u5F55\u754C\u9762");
		btnBack.setBounds(365, 423, 109, 30);
		
		Label adviceLabel = new Label(shell, SWT.NONE);
		adviceLabel.setBounds(41, 33, 89, 20);
		adviceLabel.setText("\u533B\u751F\u5EFA\u8BAE:");
		
	    txtAdvice = new Text(shell, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL|SWT.MULTI);
		txtAdvice.setEditable(false);
		txtAdvice.setBounds(41, 59, 492, 341);


	}
}
