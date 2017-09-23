package ui;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import experi.entity.*;
import experi.dao.*;

public class PatEnter {

	protected Shell shell;
	protected Display display;
	private Spinner spHPre;
	private Spinner spLPre;
	private Spinner spHR;
	private Spinner spHeight;
	private Spinner spWeight;
	private Text txtSugar;
	private VerifyListener verifySugar;
	private VerifyListener verifyDate;
	private Text txtDate;
	private java.util.Date inputDate;
	private Button btnDate;
	
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PatEnter window = new PatEnter();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void sendToDataBase(){
		//...将身体数据发送到数据库中
		BodyDao dao = new BodyDao();
		Body body = new Body();
		body.setPat_id(Login.logPat.getId());
		body.setRecord_time(new Timestamp(inputDate.getTime()));
		if(spHR.isEnabled()){
			body.setHR(spHR.getSelection());
			dao.insertHR(body);
		}
		
		if(spHPre.isEnabled() && spLPre.isEnabled()){
			body.setHPre(spHPre.getSelection());
			body.setLPre(spLPre.getSelection());
			dao.insertPre(body);
		}
		
		if(txtSugar.isEnabled()){
			body.setSug(Float.parseFloat(txtSugar.getText()));
			dao.insertSug(body);
		}
		if(spHeight.isEnabled()) Login.logPat.setHeight(spHeight.getSelection());
		if(spWeight.isEnabled()) Login.logPat.setWeight(spWeight.getSelection());
		if(spWeight.isEnabled() || spHeight.isEnabled()){
			PatientDao pat_dao = new PatientDao();
			pat_dao.updatePatient(Login.logPat);
		}

	}
	
	/**
	 * Open the window.
	 */
	public void open() {
	    display = Display.getDefault();
		createContents();
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - shell.getSize().y/2); 
		
		Composite comBody = new Composite(shell, SWT.NONE);
		comBody.setBounds(45, 43, 385, 307);
		
		spHPre = new Spinner(comBody, SWT.BORDER);
		spHPre.setEnabled(false);
		spHPre.setMaximum(500);
		spHPre.setSelection(120);
		spHPre.setBounds(235, 121, 57, 26);
		
		spLPre = new Spinner(comBody, SWT.BORDER);
		spLPre.setEnabled(false);
		spLPre.setMaximum(500);
		spLPre.setSelection(80);
		spLPre.setBounds(235, 89, 57, 26);
		
		spHeight = new Spinner(comBody, SWT.BORDER);
		spHeight.setEnabled(false);
		spHeight.setMaximum(500);
		spHeight.setSelection(175);
		spHeight.setBounds(235, 7, 57, 26);
		
		spWeight = new Spinner(comBody, SWT.BORDER);
		spWeight.setEnabled(false);
		spWeight.setMaximum(500);
		spWeight.setSelection(60);
		spWeight.setBounds(235, 39, 57, 26);
		
		spHR = new Spinner(comBody, SWT.BORDER);
		spHR.setEnabled(false);
		spHR.setMaximum(500);
		spHR.setSelection(75);
		spHR.setBounds(235, 169, 57, 26);
		
		txtSugar = new Text(comBody, SWT.BORDER);
		txtSugar.setEnabled(false);
		verifySugar = new VerifyListener(){
			public void verifyText(VerifyEvent e) {
				Pattern pattern = Pattern.compile("[\\.\\d]*");   
				Matcher matcher = pattern.matcher(e.text);   
				if (matcher.matches())  
					e.doit = true;   
				else if (e.text.length() > 0) 
					e.doit = false;   
				else  
					// 控制键   
					e.doit = true;   
			}
		};
		txtSugar.addVerifyListener(verifySugar);
		txtSugar.setBounds(235, 201, 96, 23);
		
		Label lblDate = new Label(comBody, SWT.NONE);
		lblDate.setBounds(10, 257, 147, 20);
		lblDate.setText("\u6D4B\u91CF\u65E5\u671F(\u5E74-\u6708-\u65E5)\uFF1A");
		
		txtDate = new Text(comBody, SWT.BORDER);
		verifyDate = new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				Pattern pattern = Pattern.compile("[-\\d]*");   
				Matcher matcher = pattern.matcher(e.text);   
				if (matcher.matches())  
					e.doit = true;   
				else if (e.text.length() > 0) 
					e.doit = false;   
				else  
					e.doit = true; 
			}
		};
		txtDate.addVerifyListener(verifyDate);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		txtDate.setText(format.format(new java.util.Date()));
		txtDate.setBounds(235, 254, 96, 23);
		
		btnDate = new Button(comBody, SWT.NONE);
		btnDate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final  Shell dialog  =   new  Shell(shell, SWT.DIALOG_TRIM);
				dialog.setLayout( new  GridLayout( 3 ,  false ));

				final  DateTime calendar  =   new  DateTime(dialog, SWT.CALENDAR
						|  SWT.BORDER);

				new  Label(dialog, SWT.NONE);
				new  Label(dialog, SWT.NONE);
				Button ok  =   new  Button(dialog, SWT.PUSH);
				ok.setText( " OK " );
				ok.setLayoutData( new  GridData(SWT.FILL, SWT.CENTER,  false ,
						false ));
				ok.addSelectionListener( new  SelectionAdapter()  {
					public   void  widgetSelected(SelectionEvent e)  {
						txtDate.removeVerifyListener(verifyDate);
						txtDate.setText(calendar.getYear()+"-"+(calendar.getMonth()+1)+"-"+calendar.getDay());
						txtDate.addVerifyListener(verifyDate);
						dialog.close();
					} 
				} );
				dialog.setDefaultButton(ok);
				dialog.pack();
				dialog.open();
			} 
		} );
		btnDate.setBounds(330, 256, 24, 23);
		btnDate.setText("...");
		
		Button btnHeight = new Button(comBody, SWT.CHECK);
		btnHeight.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				spHeight.setEnabled(btnHeight.getSelection());
			}
		});
		btnHeight.setBounds(10, 10, 121, 20);
		btnHeight.setText("\u8EAB\u9AD8(cm)");
		
		Button btnWeight = new Button(comBody, SWT.CHECK);
		btnWeight.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				spWeight.setEnabled(btnWeight.getSelection());
			}
		});
		btnWeight.setText("\u4F53\u91CD(kg)");
		btnWeight.setBounds(10, 42, 121, 20);
		
		Button btnLPre = new Button(comBody, SWT.CHECK);
		btnLPre.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				spLPre.setEnabled(btnLPre.getSelection());
			}
		});
		btnLPre.setBounds(10, 89, 205, 29);
		btnLPre.setText("\u8212\u5F20\u538B-\u8840\u538B\u4E0B\u503C(mmHg)");
		
		Button btnHPre = new Button(comBody, SWT.CHECK);
		btnHPre.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				spHPre.setEnabled(btnHPre.getSelection());
			}
		});
		btnHPre.setBounds(10, 127, 195, 20);
		btnHPre.setText("\u6536\u7F29\u538B-\u8840\u538B\u4E0A\u503C(mmHg)");
		
		Button btnHR = new Button(comBody, SWT.CHECK);
		btnHR.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				spHR.setEnabled(btnHR.getSelection());
			}
		});
		btnHR.setBounds(10, 169, 180, 23);
		btnHR.setText("\u5FC3\u7387-\u5B89\u9759\u65F6(\u6B21/\u5206)");
		
		Button btnSug = new Button(comBody, SWT.CHECK);
		btnSug.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtSugar.setEnabled(btnSug.getSelection());
			}
		});
		btnSug.setBounds(10, 201, 205, 23);
		btnSug.setText("\u8840\u7CD6-\u5B89\u9759\u65F6(mmol/L)");
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
		shell.setSize(541, 456);
		shell.setText("\u8F93\u5165\u8EAB\u4F53\u53C2\u6570");
		
		Label bodyLabel = new Label(shell, SWT.NONE);
		bodyLabel.setText("\u8EAB\u4F53\u53C2\u6570\u8F93\u5165:  \u8BF7\u52FE\u9009\u9700\u8981\u8F93\u5165\u7684\u8EAB\u4F53\u53C2\u6570");
		bodyLabel.setBounds(50, 17, 344, 20);
		
		Button btnClick = new Button(shell, SWT.NONE);
		btnClick.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(txtSugar.isEnabled()){
						Pattern pattern = Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
						Matcher matcher = pattern.matcher(txtSugar.getText().trim());   
						if (!matcher.matches()) {
			    			MessageBox messagebox = new MessageBox(shell);
			    			messagebox.setMessage("您输入的血糖数值格式错误！");
			    			messagebox.open();
			    			return;
						}
					}
					java.util.Date currentDateTime = new java.util.Date();
					java.util.Date date =  new SimpleDateFormat("yyyy-MM-dd").parse(txtDate.getText().trim());
					if(date.after(currentDateTime)) {
		    			MessageBox messagebox = new MessageBox(shell);
		    			messagebox.setMessage("您输入的日期在未来！");
		    			messagebox.open();
		    			return;
					}
					inputDate = date;
					sendToDataBase();
	    			MessageBox messagebox = new MessageBox(shell);
	    			messagebox.setMessage("您输入的身体参数已提交！");
	    			messagebox.open();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
	    			MessageBox messagebox = new MessageBox(shell);
	    			messagebox.setMessage("您输入的测量日期格式错误！");
	    			messagebox.open();
					return;
				}

			}
		});
		btnClick.setText("\u786E\u8BA4");
		btnClick.setBounds(99, 369, 76, 30);
		
		Button btnBack = new Button(shell, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.close();
				PatFrame.main(null);
			}
		});
		btnBack.setText("\u8FD4\u56DE");
		btnBack.setBounds(249, 369, 76, 30);

	}
}
