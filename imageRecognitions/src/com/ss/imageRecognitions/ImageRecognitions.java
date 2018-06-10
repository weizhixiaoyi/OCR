package com.ss.imageRecognitions;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import net.sourceforge.tess4j.example.TesseractExample;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Text;

public class ImageRecognitions extends Shell {
	//本地模糊路径地址 /Users/zhenhai/Downloads/SoftwareCup/imageRecognitions
	private String filePath;  //图片路径
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text text;
	private Text text_1;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			ImageRecognitions shell = new ImageRecognitions(display);
			shell.open();
			shell.layout();
			shell.setLocation(Display.getCurrent().getClientArea().width/2-shell.getSize().x/2,
					Display.getCurrent().getClientArea().height/2-shell.getSize().y/2);
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create the shell.
	 * @param display
	 */
	public ImageRecognitions(Display display) {
		super(display, SWT.CLOSE | SWT.TITLE);
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		setImage(SWTResourceManager.getImage(ImageRecognitions.class, "/images/logo.jpg"));
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(37, 60, 65, 17);
		lblNewLabel.setText("图片路径：");
		
		Label lblNewLabel_1 = new Label(composite, SWT.BORDER);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setBounds(124, 59, 450, 23);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog=new FileDialog(composite.getShell());
				fileDialog.setText("上传员工照片");
				fileDialog.setFilterExtensions(new String[]{"*.jpg","*.png","*.jpeg","*.ico","*.gif"});
				filePath = fileDialog.open();
				
				lblNewLabel_1.setText(filePath);
			}
		});
		btnNewButton.setBounds(38, 142, 80, 27);
		btnNewButton.setText("选择图片");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String singlePath = lblNewLabel_1.getText().trim();
				String allPath = text_1.getText().trim();
				
				if("".equals(singlePath) && "".equals(allPath)){
					MessageDialog.openError(getShell(), "温馨提示", "请选择图片或输入图片文件夹地址...");
					return;
				}else if(!"".equals(singlePath) && !"".equals(allPath)){
					MessageDialog.openError(getShell(), "错误提示", "不能同时识别图片和图片文件夹...");
					return;
				}else if(!"".equals(singlePath) && "".equals(allPath)){
					text.setText(TesseractExample.getOneImageText(singlePath));
					return;
				}else if("".equals(singlePath) && !"".equals(allPath)){
					String str = "";
					List<String> list = TesseractExample.getAllImageText(allPath);
					for(String li:list){
						str+=li+"\r\n";
					}
					text.setText(str);
					return;
				}
			}
		});
		btnNewButton_1.setBounds(151, 142, 80, 27);
		btnNewButton_1.setText("开始识别");
		
		text = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setBounds(37, 186, 546, 151);
		
		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		lblNewLabel_2.setBounds(37, 22, 252, 20);
		lblNewLabel_2.setText("请选择图片路径或输入文件夹路径");
		
		Label lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setBounds(37, 101, 81, 17);
		lblNewLabel_3.setText("文件夹路径：");
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setBounds(124, 98, 450, 23);
		formToolkit.adapt(text_1, true, true);
		
		Button btnNewButton_2 = new Button(composite, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblNewLabel_1.setText("");
				text_1.setText("");
				text.setText("");
			}
		});
		btnNewButton_2.setBounds(266, 142, 80, 27);
		formToolkit.adapt(btnNewButton_2, true, true);
		btnNewButton_2.setText("清空");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("图像识别系统");
		setSize(630, 389);
	}

	@Override
	protected void checkSubclass() {
	}
}
