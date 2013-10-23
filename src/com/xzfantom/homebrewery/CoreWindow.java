//http://zetcode.com/tutorials/javaswingtutorial/
package com.xzfantom.homebrewery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class CoreWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea textArea = new JTextArea();

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
        bexit.setBorder(new EmptyBorder(0 ,0, 0, 0));
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

        JButton freehandb = new JButton(freehand);
        freehandb.setBorder(new EmptyBorder(3, 0, 3, 0));
        JButton shapeedb = new JButton(shapeed);
        shapeedb.setBorder(new EmptyBorder(3, 0, 3, 0));

        vertical.add(selectb);
        vertical.add(freehandb);
        vertical.add(shapeedb);

        add(vertical, BorderLayout.WEST);

        //JTextArea textArea = new JTextArea();
        add(textArea, BorderLayout.CENTER);
        //textArea.append("Test");

        JLabel statusbar = new JLabel(" Statusbar");
        statusbar.setPreferredSize(new Dimension(-1, 22));
        statusbar.setBorder(LineBorder.createGrayLineBorder());
        add(statusbar, BorderLayout.SOUTH);

        setSize(350, 300);
        setTitle("BorderLayout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void GetData(String S){
    	textArea.append(S);
    }
}



