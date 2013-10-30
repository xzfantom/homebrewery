package com.xzfantom.homebrewery;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class window extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window frame = new window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public window() {
		setTitle("Homebrewery");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		
		JPanel graphicPanel = new JPanel();
		tabbedPane.addTab("Graphic", null, graphicPanel, null);
		graphicPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Canvas canvas = new Canvas();
		graphicPanel.add(canvas);
		
		JPanel consolePanel = new JPanel();
		tabbedPane.addTab("Console", null, consolePanel, null);
		
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
		
		JLabel temp = new JLabel("100 *C");
		panel_1.add(temp);
		
		JLabel lblTan = new JLabel("\u0422\u044D\u043D:");
		panel_1.add(lblTan);
		
		JLabel tan = new JLabel("Off");
		panel_1.add(tan);
		
		JLabel label = new JLabel("\u0412\u0440\u0435\u043C\u044F \u0432\u0441\u0435\u0433\u043E:");
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("00:00:00");
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("\u0412\u0440\u0435\u043C\u044F \u043F\u0430\u0443\u0437\u044B:");
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("00:00:00");
		panel_1.add(label_3);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		commandPanel.add(panel_2);
		
		JRadioButton radioButton = new JRadioButton("\u0412\u043A\u043B\u044E\u0447\u0435\u043D");
		
		panel_2.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("\u0410\u0432\u0442\u043E\u043C\u0430\u0442");
		panel_2.add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("\u0412\u044B\u043A\u043B\u044E\u0447\u0435\u043D");
		panel_2.add(radioButton_2);
		
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		commandPanel.add(panel_3);
		splitPane.setDividerLocation(1.0);
	}
}
