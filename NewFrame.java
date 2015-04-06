package jofima;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 * This class opens a Gui Jframe with 2 buttons - open file and close
 * when choosing open file a file selection JFileChooser window will
 * appear for file selection and will initiate the DataWork class with
 * the selected file
 * 
 *
 */

@SuppressWarnings("serial")
public class NewFrame extends javax.swing.JFrame {

	static String pathFile;
	
	    /**
	     * Creates new form NewJFrame
	     */
	    public NewFrame() {
	        initComponents();
	    }

	    private void initComponents() {

	        jButton1 = new javax.swing.JButton();
	        jButton2 = new javax.swing.JButton();
	        jLabel1 = new javax.swing.JLabel();
	        jLabel2 = new javax.swing.JLabel();
	        jLabel3 = new javax.swing.JLabel();
	        jLabel4 = new javax.swing.JLabel();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	        jButton1.setText("Abrir ficheiro");
	        jButton1.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jButton1ActionPerformed(evt);
	            }
	        });

	        jButton2.setText("Sair");
	        jButton2.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                jButton2ActionPerformed(evt);
	            }
	        });
	        
	        java.awt.Font font = new java.awt.Font(jLabel4.getFont().getName(), Font.ITALIC, jLabel4.getFont().getSize());
	        
	        jLabel1.setText("Carregue para abrir ficheiro Excel de input");

	        jLabel2.setText("Certifique que o ficheiro inicia com as seguintes colunas:");

	        jLabel3.setText("Num Cliente, Nome, Morada, Localidade, NIF,");
	        jLabel3.setFont(font);
	        
	        jLabel4.setText("Telefone, Morada2, Codigo postal, Designação Postal");
	        jLabel4.setFont(font);

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addGap(75, 75, 75)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jLabel4)
	                	.addComponent(jLabel3)
	                    .addComponent(jLabel2)
	                    .addComponent(jLabel1))
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            .addGroup(layout.createSequentialGroup()
	                .addGap(44, 44, 44)
	                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
	                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(54, 54, 54))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addGap(52, 52, 52)
	                .addComponent(jLabel1)
	                .addGap(35, 35, 35)
	                .addComponent(jLabel2)
	                .addGap(15, 15, 15)
	                .addComponent(jLabel3)
	                .addGap(10, 10, 10)
	                .addComponent(jLabel4)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addGap(56, 56, 56))
	        );

	        pack();
	    }                       

	// file selection button - will open JFileChooser and launch DataWork class
	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

		JFileChooser openFile = new JFileChooser();
		int userSelection = openFile.showOpenDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = openFile.getSelectedFile();

			try {
				pathFile = openFile.getCurrentDirectory().getCanonicalPath();

			} catch (IOException e) {

				JOptionPane.showMessageDialog(null, "ERRO DIRECTORIO", "ERRO",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			// Class DataWork has to be initiated after file selection
			new DataWorkList(fileToSave);
		}
	}
	    
	// close button - will exit system
	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	public void runGui() {

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new NewFrame().setVisible(true);
			}
		});
	}
	                      
	    private javax.swing.JButton jButton1;
	    private javax.swing.JButton jButton2;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel2;
	    private javax.swing.JLabel jLabel3;
	    private javax.swing.JLabel jLabel4;                  
	}
