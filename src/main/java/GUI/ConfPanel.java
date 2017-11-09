package GUI;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

public class ConfPanel extends JTable {
	ConfTableModel model = new ConfTableModel();

	ConfPanel() {
		super();
		setModel(model);
//		EditorComboBox cbx = new EditorComboBox("A,b,c".split(","));
		ConfTableCellEditor anEditor = new ConfTableCellEditor();
//		ComboBoxTableCellEditor anEditor = new ComboBoxTableCellEditor();
//		setCellEditor(anEditor);
		getColumnModel().getColumn(1).setCellEditor(anEditor);
	}

	public void setConf(String key, Object o) {
		model.set(key, o);
	}

	class ConfTableCellEditor extends DefaultCellEditor implements TableCellEditor {
		int flg;
		private JTextField textField;
		private JFileChooser fileChooser;
		private JSpinner field = new JSpinner();
		private ArrayList <String> recs = new ArrayList <String>();
		private JComboBox combo;
		String file;
		ConfTableCellEditor(){
			super(new JTextField());
			textField = new JTextField();
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			recs.add("Sample");
			recs.add("Sample2");
			recs.add("Sample3");
			combo = new JComboBox(recs.toArray());
		}
		@Override
		public Object getCellEditorValue() {
			if(flg==0){
				File f = new File(file);
				return f;
//				}else if(flg == 2){
//				return field.getValue();
			}else if(flg == 3){
				return field.getValue();
			}else if(flg == 4){
				return combo.getSelectedItem();
			}else{
				return textField.getText();
			}
		}
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
			
			String k = (String) model.conf.keySet().toArray()[row];
			
			if(model.conf.get(k) instanceof File){
				flg = 0;
				file = value.toString();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						fileChooser.setSelectedFile(new File(file));
						if (fileChooser.showOpenDialog(textField) == JFileChooser.APPROVE_OPTION) {
							file = fileChooser.getSelectedFile().getAbsolutePath();
						}
						fireEditingStopped();
					}
				});
				textField.setText(file);
				return textField;
//			}else if(flg==1){
//				return field;
			}else if(model.conf.get(k) instanceof JComboBox){
//				flg = 2;
				return ((JComboBox)(model.conf.get(k))).getComponent(0);
			}else if(model.conf.get(k) instanceof Integer){
				flg = 3;
				return field;
			}else if(recs.contains(model.conf.get(k))){
				flg = 4;
				return combo;
			}else{
				flg = 1;
				textField.setText(value.toString());
				return textField;
			}
		}
	}

	class ConfTableModel implements TableModel {
		private HashMap<String, Object> conf = new HashMap<String, Object>();

		void set(String key, Object o) {
			conf.put(key, o);
		}

		public int getRowCount() {
			return conf.size();
		}

		public int getColumnCount() {
			return 2;
		}

		public String getColumnName(int columnIndex) {
			if (columnIndex == 0) {
				return "KEY";
			} else {
				return "VALUE";
			}
		}

		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 0) {
				return "KEY".getClass();
			} else {
				return Object.class;
			}
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return false;
			} else {
				return true;
			}
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			String ky = conf.keySet().toArray()[rowIndex].toString();
			if (columnIndex == 0) {
				return ky;
			} else {
				return conf.get(ky).toString();
			}
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			String ky = conf.keySet().toArray()[rowIndex].toString();
			conf.put(ky, aValue);
		}

		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub

		}

		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub

		}

	}
}
