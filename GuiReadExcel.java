package jofima;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow.CellIterator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GuiReadExcel {

	public String filePath;
	int userSelection;
	// JFileChooser openFile;

	File fileToSave;

	public void openGui() {
		Runnable r = new Runnable() {

			public void run() {
				new GuiReadExcel().createUI();
			}
		};

		EventQueue.invokeLater(r);
	}

	// create a GUI box with buttons to select a file to read
	public void createUI() {

		// Commented out in case of a second button
		// JButton saveBtn = new JButton("Save");
		// saveBtn.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		// JFileChooser saveFile = new JFileChooser();
		// saveFile.showSaveDialog(null);
		// }
		// });

		// create a button to add to the Gui frame
		JButton openBtn = new JButton("Abrir Ficheiro *.XLS");

		openBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser openFile = new JFileChooser();
				userSelection = openFile.showOpenDialog(null);
				if (userSelection == JFileChooser.APPROVE_OPTION) {

					fileToSave = openFile.getSelectedFile();

					// Instantiation has to be here or the selected file will
					// not be available
					new DataWork(fileToSave);

				}
			}
		});

		// create frame and insert the buttons created
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setSize(400, 300);
		// frame.pack(); //use if not setting size of frame
		frame.add(new JLabel(
				"  Escolha um ficheiro excel XLS "),
				BorderLayout.CENTER);
		// frame.add(new
		// JLabel("  The files will be saved at c:\\ "),BorderLayout.BEFORE_FIRST_LINE);
		// frame.add(saveBtn, BorderLayout.CENTER);
		frame.add(openBtn, BorderLayout.AFTER_LAST_LINE);
		frame.setTitle("    Split XLS    ");
		frame.setLocation(300, 300);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
