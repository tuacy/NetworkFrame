package com.tuacy.networkframe.bean;


import java.util.List;

public class ZipBean {

	private List<BookBean> mBookList;
	private BookBean       mBook;

	public ZipBean(List<BookBean> bookList, BookBean book) {
		mBookList = bookList;
		mBook = book;
	}

	public List<BookBean> getBookList() {
		return mBookList;
	}

	public void setBookList(List<BookBean> bookList) {
		mBookList = bookList;
	}

	public BookBean getBook() {
		return mBook;
	}

	public void setBook(BookBean book) {
		mBook = book;
	}
}
