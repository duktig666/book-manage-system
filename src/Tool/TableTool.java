package Tool;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TableTool {
	static JTable table;
	static DefaultTableModel dfttable;
	/**
	 * 公共的表格方法
	 */
	public static JTable setTable(JTable table, DefaultTableModel dfttable) {
		TableTool.table = table;
		TableTool.dfttable = dfttable;
		// 设置表格数据居中显示
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
		//设置表格行高
		table.setRowHeight(20);
		//设置表格整列不可移动
		table.getTableHeader().setReorderingAllowed(false); // 设置整列不可移动
		return table;
	}
	
	/**
	 * 取消表格选中状态
	 * 将传入int值设为-1
	 */
	public static int cancelTableSelected(JTable table,int id) {
		  table.clearSelection();  //取消选择
		  return id=-1;
	}
	
	/**
	 * 取消表格选中状态
	 * 将传入String值设为null
	 */
	public static String setNull(JTable table, String str) {
		table.clearSelection();
		return str=null;
	}

}
