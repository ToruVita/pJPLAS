package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.CellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

public class ConfPanel extends JTable {
	ConfTableModel model = new ConfTableModel();;

	ConfPanel() {
		super();
		setModel(model);
		ConfTableCellEditor anEditor = new ConfTableCellEditor(this);
		setCellEditor(anEditor);
	}

	class ConfTableCellEditor implements TableCellEditor, ActionListener {
		JFileChooser filec = new JFileChooser();
		JButton button = new JButton();
		EditorComboBox ecb = new EditorComboBox("A,b,c".split(","));
		ConfPanel confPanel;
		CellEditor currentEditor;

		ConfTableCellEditor(ConfPanel confPanel) {
			currentEditor = ecb;
//			this.confPanel = confPanel;
		}

		public Object getCellEditorValue() {
//			if (filec.getSelectedFile() == null)
//				return null;
//			Icon icon = new ImageIcon(filec.getSelectedFile().getPath());
//			return icon;
		    return currentEditor.getCellEditorValue();
		}

		public boolean isCellEditable(EventObject anEvent) {
			// TODO Auto-generated method stub
			return true;
		}

		public boolean shouldSelectCell(EventObject anEvent) {
			System.out.println("naka");
		    return currentEditor.shouldSelectCell(anEvent);
		}

		public boolean stopCellEditing() {
			return currentEditor.stopCellEditing();
		}

		public void cancelCellEditing() {
		    currentEditor.cancelCellEditing();
		}

		public void addCellEditorListener(CellEditorListener l) {
			 ecb.addCellEditorListener(l);
		}

		public void removeCellEditorListener(CellEditorListener l) {
			ecb.removeCellEditorListener(l);
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
//			if (value == null) {
//				button.setText("value is Null");
//			} else {
//				button.setText(value.toString());
//			}
//
//			if (value instanceof Icon) {
//				button.setIcon((Icon) value);
//			} else {
//				button.setIcon(null);
//			}
			currentEditor = ecb;
			return (Component)currentEditor;
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

	class EditorComboBox extends JComboBox implements CellEditor {

		String value;

		Vector listeners = new Vector();

		// Mimic all the constructors people expect with ComboBoxes.
		public EditorComboBox(Object[] list) {
			super(list);
			setEditable(false);
			value = list[0].toString();

			// Listen to our own action events so that we know when to stop
			// editing.
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					if (stopCellEditing()) {
						fireEditingStopped();
					}
				}
			});
		}

		// Implement the CellEditor methods.
		public void cancelCellEditing() {
		}

		// Stop editing only if the user entered a valid value.
		public boolean stopCellEditing() {
			try {
				value = (String) getSelectedItem();
				if (value == null) {
					value = (String) getItemAt(0);
				}
				return true;
			} catch (Exception e) {
				// Something went wrong.
				return false;
			}
		}

		public Object getCellEditorValue() {
			return value;
		}

		// Start editing when the right mouse button is clicked.
		public boolean isCellEditable(EventObject eo) {
			if ((eo == null) || ((eo instanceof MouseEvent) && (((MouseEvent) eo).isMetaDown()))) {
				return true;
			}
			return false;
		}

		public boolean shouldSelectCell(EventObject eo) {
			return true;
		}

		// Add support for listeners.
		public void addCellEditorListener(CellEditorListener cel) {
			listeners.addElement(cel);
		}

		public void removeCellEditorListener(CellEditorListener cel) {
			listeners.removeElement(cel);
		}

		protected void fireEditingStopped() {
			if (listeners.size() > 0) {
				ChangeEvent ce = new ChangeEvent(this);
				for (int i = listeners.size() - 1; i >= 0; i--) {
					((CellEditorListener) listeners.elementAt(i)).editingStopped(ce);
				}
			}
		}
	}
}
