package com.tuacy.retrofitdemo.request;


import java.util.List;

public class GetRequest {


	private List<NewsBean> news;

	public List<NewsBean> getNews() {
		return news;
	}

	public void setNews(List<NewsBean> news) {
		this.news = news;
	}

	@Override
	public String toString() {
		return "GetRequest{" + "news=" + news + '}';
	}

	public static class NewsBean {

		/**
		 * id : 1
		 * title : 曹县宣布昨日晚间登日成功
		 * date : 2016-08-12
		 * likes : 55
		 * views : 100086
		 */

		private int id;
		private String title;
		private String date;
		private int    likes;
		private int    views;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public int getLikes() {
			return likes;
		}

		public void setLikes(int likes) {
			this.likes = likes;
		}

		public int getViews() {
			return views;
		}

		public void setViews(int views) {
			this.views = views;
		}

		@Override
		public String toString() {
			return "NewsBean{" + "id=" + id + ", title='" + title + '\'' + ", date='" + date + '\'' + ", likes=" + likes + ", views=" +
				   views + '}';
		}
	}
}
