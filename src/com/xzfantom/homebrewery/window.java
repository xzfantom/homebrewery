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
		splitPane.setResizeWeight(10.0);
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
		commandPanel.setLayout(null);
		splitPane.setDividerLocation(250);
		splitPane.setDividerLocation(-1.0);
	}
}
