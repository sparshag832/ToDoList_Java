import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Taskbar;
import java.awt.color.ColorSpace;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;
import java.awt.BorderLayout;

public class Home extends JFrame {

	protected static final CharSequence CharSequence = null;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
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
	public void setTableData()
	{
	
			try
			{
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/todo?user=root&password=root");
	String query="select * from mytask";
	PreparedStatement statement=connection.prepareStatement(query);
	ResultSet result=statement.executeQuery();

	java.sql.ResultSetMetaData rsmd=   result.getMetaData();
	DefaultTableModel model=new DefaultTableModel();
	
	
	for(int i=1;i<=rsmd.getColumnCount();i++)
	{
		
		model.addColumn(rsmd.getColumnName(i));
	}  

	
	while(result.next())
	{
		String row[]=new String[rsmd.getColumnCount()];
		for(int i=1;i<=rsmd.getColumnCount();i++)
		{
			row[i-1]=result.getString(i);
		}
		model.addRow(row);
	}
	table.setModel(model);
	connection.close();

			}

			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			


	}
	public Home() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setForeground(Color.RED);
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Task:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 68, 46, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(49, 63, 373, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("ADD TASK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try
				{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/todo?user=root&password=root");
		String query="insert into mytask values(?)";
		PreparedStatement statement=connection.prepareStatement(query);
		statement.setString(1, textField.getText());
		int i=statement.executeUpdate();
	JOptionPane.showMessageDialog(null, "Task  Added Succesfully");
		connection.close();
				}

				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				

				textField.setText(null);
				setTableData();
			}
			
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.GREEN);
		btnNewButton.setBounds(432, 66, 131, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("DELETE TASK");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tableModel=(DefaultTableModel)table.getModel();
				if(table.getSelectedRowCount()==1)
				{
					
					int row=table.getSelectedRow();
					String taskString=(tableModel.getValueAt(row, 0).toString());
					 
					 try {
							Class.forName("com.mysql.cj.jdbc.Driver");
							Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/todo?user=root&password=root");
							String queryString="delete from  mytask where Tasks = ?";
							PreparedStatement statement=connection.prepareStatement(queryString);
							statement.setString(1,taskString);
							
							int i=statement.executeUpdate();
							//System.out.println(i);
							connection.close();
							if(i>0)
							{
							JOptionPane.showMessageDialog(null, "Data Deleted");
						    textField.setText(null);
							}
							setTableData();
						}
						catch(Exception e1)
						{
							JOptionPane.showMessageDialog(null, e1);
						}
						
					 
				}
				else {
					JOptionPane.showMessageDialog(null, "Please Select Any Task");
				}
			
		}
						
				
		
});
		btnNewButton_1.setForeground(new Color(255, 240, 245));
		btnNewButton_1.setBackground(Color.RED);
		btnNewButton_1.setBounds(432, 153, 131, 23);
		contentPane.add(btnNewButton_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		panel.setBounds(0, 11, 584, 41);
		contentPane.add(panel);
		
		JLabel headLabel = new JLabel("To Do Application");
		panel.add(headLabel);
		headLabel.setFont(new Font("Tw Cen MT Condensed", Font.PLAIN, 26));
		headLabel.setForeground(Color.GREEN);
		headLabel.setBackground(Color.RED);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(21, 127, 401, 223);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
        table = new JTable();
        panel_1.add(table, BorderLayout.CENTER);
        
		JScrollPane scrollPane = new JScrollPane(table);
		panel_1.add(scrollPane);
		
		
		
		setTableData();
		
	}
}
