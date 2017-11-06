package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.AbstractCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

public class ConfPanel extends JTable {
	ConfTableModel model = new ConfTableModel();;

	ConfPanel() {
		super();
		setModel(model);
		ConfTableCellEditor anEditor = new ConfTableCellEditor();
		setCellEditor(anEditor);
	}

	class ConfTableCellEditor implements TableCellEditor, ActionListener {
		JFileChooser filec = new JFileChooser();
		JButton button = new JButton();

		ConfTableCellEditor() {
			button.addActionListener(this);
		}

		public Object getCellEditorValue() {
			if (filec.getSelectedFile() == null) return null;
			Icon icon = new ImageIcon(filec.getSelectedFile().getPath());
			return icon;
		}

		public boolean isCellEditable(EventObject anEvent) {
			// TODO Auto-generated method stub
			return true;
		}

		public boolean shouldSelectCell(EventObject anEvent) {
			System.out.println(anEvent.getSource().toString());
			return false;
		}

		public boolean stopCellEditing() {
			// TODO Auto-generated method stub
			return false;
		}

		public void cancelCellEditing() {
			// TODO Auto-generated method stub

		}

		public void addCellEditorListener(CellEditorListener l) {
			// TODO Auto-generated method stub

		}

		public void removeCellEditorListener(CellEditorListener l) {
			// TODO Auto-generated method stub

		}
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (value == null){
				button.setText("value is Null");
			}else{
				button.setText(value.toString());
			}

			if (value instanceof Icon){
				button.setIcon((Icon)value);
			}else{
				button.setIcon(null);
			}
			return button;
		}

		public void actionPerformed(ActionEvent e) {
			if (JFileChooser.APPROVE_OPTION == filec.showOpenDialog(button)) {
				stopCellEditing();
			}
		}

	}

	public void setConf(String key, Object o) {
		model.set(key, o);
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
				return conf.get(ky).getClass().toString() + conf.get(ky).toString();
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
