//http://zetcode.com/tutorials/javaswingtutorial/
package com.xzfantom.homebrewery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class CoreWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	//private static COMTalker comTalker;
	private Dispatcher dispatcher;
	
	private JTextArea consoleOutput = new JTextArea();
	private JLabel statusbar = new JLabel(" Statusbar");
	private JTextField consoleInput = new JTextField();
	
	private InputConsoleHandler inputConsoleHandler = null;
	
	public CoreWindow() {

		initUI();
	}

	public final void initUI() {

		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");

		menubar.add(file);
		setJMenuBar(menubar);

		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);

		ImageIcon exit = new ImageIcon("resources/exit.png");
		JButton bexit = new JButton(exit);
		bexit.setBorder(new EmptyBorder(0, 0, 0, 0));
		toolbar.add(bexit);

		add(toolbar, BorderLayout.NORTH);
		JToolBar vertical = new JToolBar(JToolBar.VERTICAL);
		vertical.setFloatable(false);
		vertical.setMargin(new Insets(10, 5, 5, 5));

		ImageIcon select = new ImageIcon("resources/drive.png");
		ImageIcon freehand = new ImageIcon("resources/computer.png");
		ImageIcon shapeed = new ImageIcon("resources/printer.png");

		JButton selectb = new JButton(select);
		selectb.setBorder(new EmptyBorder(3, 0, 3, 0));
		selectb.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                  statusbar.setText("Action!");
             }
        });

		JButton freehandb = new JButton(freehand);
		freehandb.setBorder(new EmptyBorder(3, 0, 3, 0));
		JButton shapeedb = new JButton(shapeed);
		shapeedb.setBorder(new EmptyBorder(3, 0, 3, 0));

		vertical.add(selectb);
		vertical.add(freehandb);
		vertical.add(shapeedb);

		add(vertical, BorderLayout.WEST);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JComponent graphicPanel = makeTextPanel("graphic panel");
		tabbedPane.addTab("Graphic", graphicPanel);
		
		JComponent consolePanel = makeTextPanel("console panel");
		final JScrollPane scrollConsoleOutput = new JScrollPane(consoleOutput);
		consolePanel.add(scrollConsoleOutput, BorderLayout.CENTER);
		consolePanel.add(consoleInput, BorderLayout.CENTER);
		inputConsoleHandler = new InputConsoleHandler();
		consoleInput.addActionListener(inputConsoleHandler);
		
		tabbedPane.addTab("Console", consolePanel);
		
		JComponent settingsPanel = makeTextPanel("settings panel");
		tabbedPane.addTab("Settings", settingsPanel);
		
		add(tabbedPane, BorderLayout.CENTER);
		
		statusbar.setPreferredSize(new Dimension(-1, 22));
		statusbar.setBorder(LineBorder.createGrayLineBorder());
		add(statusbar, BorderLayout.SOUTH);

		setSize(350, 300);
		setTitle("BorderLayout");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	private class InputConsoleHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			sendData(e.getActionCommand());
			consoleInput.setText("");
		}
		
	}

	public void getData(String S) {
		consoleOutput.append(S);
	}
	
	private void sendData(String S){
		dispatcher.sendCOMMessage(S);
	}

	public void setDispatcher(Dispatcher ds) {
		dispatcher = ds;
	}	
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        
        panel.setLayout(new GridLayout(2, 1));
        
        return panel;
    }
}
