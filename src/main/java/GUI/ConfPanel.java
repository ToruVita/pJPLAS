package GUI;

import java.awt.Component;
import java.util.EventObject;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;

public class ConfPanel extends JTable{
	ConfTableModel model = new ConfTableModel();;
	ConfPanel(){
		super();
		setModel(model);
		ConfTableCellEditor anEditor = new ConfTableCellEditor();
		setCellEditor(anEditor);
	}
	class ConfTableCellEditor implements TableCellEditor{

		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
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
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	public void setConf(String key, Object o){
		model.setPair(key, o);
	}
	class ConfTableModel implements TableModel{
		class ConfPairObject{
			Component component;
			Object object;
			public ConfPairObject(Component component, Object object) {
				super();
				this.component = component;
				this.object = object;
			}
		}
		private HashMap <String, ConfPairObject> conf = new HashMap <String, ConfPairObject>();
		void setPair(String key, Object o){
			conf.put(key, new ConfPairObject(null, o));
		}
		public int getRowCount() {
			return conf.size();
		}
		public int getColumnCount() {
			return 2;
		}
		public String getColumnName(int columnIndex) {
			if(columnIndex==0){
				return "KEY";
			}else{
				return "VALUE";
			}
		}
		public Class<?> getColumnClass(int columnIndex) {
			if(columnIndex==0){
				return "KEY".getClass();
			}else{
				return Object.class;
			}
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(columnIndex==0){
				return false;
			}else{
				return true;
			}
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			if(columnIndex==0){
				return conf.keySet().toArray()[rowIndex].toString();
			}else{
				return conf.get(conf.keySet().toArray()[rowIndex]).toString();
			}
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			
		}

		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}

		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
