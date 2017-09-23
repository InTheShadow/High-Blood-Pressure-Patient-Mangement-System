package ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import experi.dao.BodyDao;
import experi.entity.Body;

class BodyComparator implements Comparator{
	 public  int compare(Object  obj1, Object obj2 ){	 
		   Body body1 = (Body)obj1;
		   Body body2 = (Body)obj2;
		   return body1.getRecord_time().compareTo(body2.getRecord_time());
	 }

	}
public class PatBody {

	protected Shell shell;
	protected Display display;
	private Text body;
	private Label lblName;
	private Label lblUserName;
	static Body bodies[];
	private Text body2;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PatBody window = new PatBody();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void refresh(){
		//...更新该患者的身体参数
		lblName.setText("患者姓名："+Login.logPat.getName());
		lblUserName.setText("患者用户名："+Login.logPat.getUserName());
		body.append("性别："+(Login.logPat.getSex()?"男":"女")+"\n");
		if(Login.logPat.getAge() !=0 ) body.append("年龄："+Login.logPat.getAge()+"\n");
		if(Login.logPat.getHeight() != 0) body.append("身高："+Login.logPat.getHeight()+"(cm)\n");
		else body.append("身高：未知\n");
		if(Login.logPat.getWeight() != 0) body.append("体重："+Login.logPat.getWeight()+"(kg)\n");
		else body.append("体重：未知\n");
		if(bodies == null) {
			BodyDao dao = new BodyDao();
			bodies = dao.findBodies(Login.logPat.getId());
			if(bodies != null) Arrays.sort(bodies,new BodyComparator());
			if(bodies == null) bodies = new Body[0];
		}
		
		if(Login.logPat.getHistory() != null) body.append("个人病史：\n"+Login.logPat.getHistory()+"\n");
		
		body.append("\n");
		if(bodies != null && bodies.length != 0){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for(int i = 0;i<bodies.length;i++){
				
				body.append("测量日期(年-月-日)："+format.format(bodies[i].getRecord_time())+"\n");
				if(bodies[i].getHPre() != 0) body.append("血压："+bodies[i].getLPre()+"~"
						+bodies[i].getHPre()+"(mmHg)\n");
				if(bodies[i].getHR() != 0) body.append("心率："+bodies[i].getHR()+"(次/分)\n");
				if(bodies[i].getSug() > 0.0) body.append("血糖值："+bodies[i].getSug()+"(mmol/L)\n");
				body.append("\n");
			}
		}
		else{
			body.append("心率：无数据\n血压：无数据\n血糖值：无数据\n");
		}
		body2.append(body.getText());
		
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x/2, Display.getCurrent()  
                .getClientArea().height / 2 - shell.getSize().y/2); 
		
	    body = new Text(shell, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL|SWT.MULTI);
		body.setEditable(false);
		body.setBounds(41, 102, 329, 325);
		
		lblName = new Label(shell, SWT.NONE);
		lblName.setBounds(41, 48, 236, 20);
		lblName.setText("\u60A3\u8005\u59D3\u540D\uFF1A");
		
		lblUserName = new Label(shell, SWT.NONE);
		lblUserName.setText("\u60A3\u8005\u7528\u6237\u540D\uFF1A");
		lblUserName.setBounds(41, 74, 236, 20);
		
		body2 = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		body2.setEditable(false);
		body2.setBounds(376, 102, 329, 325);
		this.refresh();
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
		shell.setSize(744, 534);
		shell.setText("\u60A3\u8005\u8EAB\u4F53\u53C2\u6570");
		
		Label bodyLabel = new Label(shell, SWT.NONE);
		bodyLabel.setBounds(41, 22, 76, 20);
		bodyLabel.setText("\u8EAB\u4F53\u53C2\u6570");
		
		Button btnBack = new Button(shell, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.close();
				DocFrame.main(null);
			}
		});
		btnBack.setText("\u8FD4\u56DE");
		btnBack.setBounds(482, 447, 76, 30);
		
		Button btnAdvice = new Button(shell, SWT.NONE);
		btnAdvice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PatAdvice.main(null);
			}
		});
		btnAdvice.setText("\u5EFA\u8BAE");
		btnAdvice.setBounds(167, 447, 76, 30);

	}
}
