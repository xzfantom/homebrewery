package com.xzfantom.homebrewery;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JToolBar;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JLabel;
import java.awt.ComponentOrientation;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Canvas;
import javax.swing.SwingConstants;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Dimension;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private Dispatcher dispatcher = null;
	private JLabel temp = null;
	private JLabel tan = null;
	private JLabel timeOverall;
	private JLabel timePause;
	private JTextArea textArea = null;
	

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public window() {
		setTitle("Homebrewery");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar menuBar = new JToolBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		
		JMenuBar menuBar_1 = new JMenuBar();
		menuBar.add(menuBar_1);
		
		JMenu mnFile = new JMenu("File");
		menuBar_1.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JToolBar statusBar = new JToolBar();
		contentPane.add(statusBar, BorderLayout.SOUTH);
		
		JLabel statusBarLabel = new JLabel("Ready");
		statusBar.add(statusBarLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		splitPane.setResizeWeight(0.0);
		panel.add(splitPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPane);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		  
		
		
		GraphPanel graphicPanel = new GraphPanel();
		tabbedPane.addTab("Graphic", null, graphicPanel, null);
		graphicPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		JPanel consolePanel = new JPanel();
		tabbedPane.addTab("Console", null, consolePanel, null);
		consolePanel.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendData(arg0.getActionCommand());
				textField.setText("");
			}
		});
		consolePanel.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setAutoscrolls(true);
		
		consolePanel.add(scrollPane, BorderLayout.CENTER);
		
		
		
		
		JPanel settingsPanel = new JPanel();
		tabbedPane.addTab("Settings", null, settingsPanel, null);
		
		
		
		JPanel commandPanel = new JPanel();
		splitPane.setLeftComponent(commandPanel);
		commandPanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setMaximumSize(new Dimension(0, 10));
		commandPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel lblTemp = new JLabel("\u0422\u0435\u043C\u043F\u0435\u0440\u0430\u0442\u0443\u0440\u0430: ");
		lblTemp.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblTemp);
		
		temp = new JLabel("100 *C");
		panel_1.add(temp);
		
		JLabel lblTan = new JLabel("\u0422\u044D\u043D:");
		panel_1.add(lblTan);
		
		tan = new JLabel("Off");
		panel_1.add(tan);
		
		JLabel label = new JLabel("\u0412\u0440\u0435\u043C\u044F \u0432\u0441\u0435\u0433\u043E:");
		panel_1.add(label);
		
		timeOverall = new JLabel("00:00:00");
		panel_1.add(timeOverall);
		
		JLabel label_2 = new JLabel("\u0412\u0440\u0435\u043C\u044F \u043F\u0430\u0443\u0437\u044B:");
		panel_1.add(label_2);
		
		timePause = new JLabel("00:00:00");
		panel_1.add(timePause);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		commandPanel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label_4 = new JLabel("\u0423\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u0438\u0435 \u0442\u044D\u043D\u043E\u043C:");
		panel_2.add(label_4);
		
		JRadioButton radioButton = new JRadioButton("\u0412\u043A\u043B\u044E\u0447\u0435\u043D");
		buttonGroup.add(radioButton);
		panel_2.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("\u0410\u0432\u0442\u043E\u043C\u0430\u0442");
		radioButton_1.setSelected(true);
		buttonGroup.add(radioButton_1);
		panel_2.add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("\u0412\u044B\u043A\u043B\u044E\u0447\u0435\u043D");
		buttonGroup.add(radioButton_2);
		panel_2.add(radioButton_2);
		
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		commandPanel.add(panel_3);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (null != dispatcher){
					dispatcher.Connect();
				} else {
					appendToConsole("dispatcher is null");
				}
			}
		});
		panel_3.add(btnConnect);
		splitPane.setDividerLocation(1.0);
		
		
	}
	
	public void setDispatcher (Dispatcher ds){
		dispatcher = ds;
	}
	
	public void updateInfo (timeTemp tt){
		temp.setText(String.valueOf(tt.temp)+" *C");
		tan.setText(String.valueOf(tt.heaterOn));
		timeOverall.setText(String.valueOf(tt.time));
	}
	
	public void appendToConsole (String S){
		//textArea.append(S+"\n");
		textArea.setText(S + "\n" + textArea.getText());
	}
	
	private void sendData(String S){
		dispatcher.sendCOMMessage(S);
	}
	
	class GraphPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;
		private static final int startX = 10;
		private static final int startY = 600;
		private int maxX = 60;
		private static final int maxY = 30;

		public GraphPanel() {
	        setBorder(BorderFactory.createLineBorder(Color.black));
	    }

	    public Dimension getPreferredSize() {
	        return new Dimension(250,200);
	    }

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);       

	        // Draw Text
	        g.drawString("Температура",10,20);
	        ArrayList<timeTemp> al = new ArrayList<timeTemp>(240);
	        g.setColor(new Color(0,0,0));
	        maxX = getWidth()-startX;
	        g.drawLine(startX, startY, maxX, startY);
	        g.drawLine(startX, startY, startX, maxY);
	        for (timeTemp item: al){
	        	
	        }
	    }
	}

	public void getData(String string) {
		appendToConsole(string);		
	}
}
