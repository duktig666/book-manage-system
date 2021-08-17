package Controller;

import java.util.Vector;

import Model.access.BookAccess;

/**
 * 分页查询的控制器
 * @author rsw
 *
 */
public class PageQueryCon {
	public static int curentPageIndex = 1; // 当前页码
	public int pageIndex;
	protected int countPerpage = 15; // 每页显示条数
	public int pageCount; // 总页数
	protected int recordCount; // 总记录条数
	protected static Vector<Vector<Object>> bigPageVector = new Vector<Vector<Object>>();
	protected Vector<Vector<Object>> smallPageVector = new Vector<Vector<Object>>();
	BookAccess bookDao = new BookAccess();

	/**
	 * 无参构造方法方便匿名调用方法
	 */
	public PageQueryCon(){
		
	}
	/**
	 * 传入指定页码的构造函数，参看第几页
	 * 
	 * @param curentPageIndex
	 */
	public PageQueryCon(Vector<Vector<Object>> vec) {
		bigPageVector=vec;
		
		//设置页数
		if (bigPageVector.size() % countPerpage == 0) {
			pageCount = bigPageVector.size() / countPerpage;
		} else {
			pageCount = (bigPageVector.size() / countPerpage) + 1;
		}
	}

	/**
	 * 此方法供调用，根据当前页，筛选记录
	 */
	public Vector<Vector<Object>> selectCount() {
		recordCount = bigPageVector.size();
		for (int i = (curentPageIndex - 1) * countPerpage; i < curentPageIndex * countPerpage && i < recordCount; i++) {
			smallPageVector.add(bigPageVector.get(i));
		}
		return smallPageVector;
	}

	/**
	 * 确切的获取当前页的记录，返回一个Vector<Vector<Object>>列表
	 * 
	 * @return
	 */
	public Vector<Vector<Object>> setCurentPageIndex() {
		curentPageIndex=1;
		return selectCount();
	}

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public Vector<Vector<Object>> previousPage() {
		if (curentPageIndex > 1) {
			curentPageIndex--;
			System.out.println("当前页:" + curentPageIndex);
			pageIndex=curentPageIndex;
		}
		return selectCount();
	}

	/**
	 * 下一页
	 */
	public Vector<Vector<Object>> nextPage() {

		if (curentPageIndex < pageCount) {
			curentPageIndex++;
			pageIndex=curentPageIndex;
			System.out.println("当前页:" + curentPageIndex);
		}
		return selectCount();
	}
	
	/**
	 * 尾页
	 */
	public Vector<Vector<Object>> lastPage() {
		curentPageIndex =  pageCount;
		return selectCount();
	}
	
	/**
	 * 跳转页数
	 */
	public Vector<Vector<Object>> jumpPage(int page) {
		curentPageIndex =  page;
		return selectCount();
	}

	/**
	 * 返回总页数
	 */
	public int pageCount() {
		return pageCount;
	}

}
