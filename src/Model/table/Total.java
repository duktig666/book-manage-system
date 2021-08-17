package Model.table;

/**
 * ±íµÄÓ³Éä
 * @author rsw
 */
public class Total {
	private Book book;
	private BookType booktype;
	private Reader reader;
	private ReaderType readertype;
	private Borrow borrow;
	private Administrator admi;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BookType getBooktype() {
		return booktype;
	}

	public void setBooktype(BookType booktype) {
		this.booktype = booktype;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public ReaderType getReadertype() {
		return readertype;
	}

	public void setReadertype(ReaderType readertype) {
		this.readertype = readertype;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public Administrator getAdmi() {
		return admi;
	}

	public void setAdmi(Administrator admi) {
		this.admi = admi;
	}
}
