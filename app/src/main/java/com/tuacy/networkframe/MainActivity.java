package com.tuacy.networkframe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tuacy.networkframe.base.BaseActivity;
import com.tuacy.networkframe.bean.BookBean;
import com.tuacy.networkframe.bean.ZipBean;
import com.tuacy.networkframe.library.ProtocolsClient;
import com.tuacy.networkframe.library.base.ProtocolsBaseRequest;
import com.tuacy.networkframe.library.callback.ProtocolsBaseCallback;
import com.tuacy.networkframe.library.exception.ProtocolsException;
import com.tuacy.networkframe.library.functions.ProtocolsFlatMapFunc;
import com.tuacy.networkframe.library.functions.ProtocolsZipFunc;
import com.tuacy.networkframe.request.AllBookRequest;
import com.tuacy.networkframe.request.BookByIdRequest;

import java.util.List;

public class MainActivity extends BaseActivity {

	private Button mButtonRequest;
	private Button mButtonFlatZip;
	private Button mButtonFlatMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mButtonRequest = (Button) findViewById(R.id.button_request);
		mButtonFlatZip = (Button) findViewById(R.id.button_request_zip);
		mButtonFlatMap = (Button) findViewById(R.id.button_request_flat_map);
	}

	private void initEvent() {
		mButtonRequest.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProtocolsClient.getInstance().protocolsRequest(mContext, new AllBookRequest(), new ProtocolsBaseCallback<List<BookBean>>() {
					@Override
					public void onProtocolStart(Object tag) {
						Log.d("tuacy", "start");
					}

					@Override
					public void onProtocolSuccess(Object tag, List<BookBean> bean) {
						Log.d("tuacy", bean.toString());
					}

					@Override
					public void onProtocolError(Object tag, ProtocolsException exception) {
						Log.d("tuacy", "error code = " + exception.getErrorCode());
					}
				});
			}
		});

		mButtonFlatZip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProtocolsClient.getInstance()
							   .protocolsRequestZipWith(mContext, new AllBookRequest(), new BookByIdRequest(0),
														new ProtocolsZipFunc<List<BookBean>, BookBean, ZipBean>() {
															@Override
															public ZipBean call(List<BookBean> bookBeen, BookBean book) {
																return new ZipBean(bookBeen, book);
															}
														}, new ProtocolsBaseCallback<ZipBean>() {
									   @Override
									   public void onProtocolStart(Object tag) {
										   Log.d("tuacy", "start");
									   }

									   @Override
									   public void onProtocolSuccess(Object tag, ZipBean bean) {
										   Log.d("tuacy", bean.getBook().toString());
										   Log.d("tuacy", bean.getBookList().toString());
									   }

									   @Override
									   public void onProtocolError(Object tag, ProtocolsException exception) {
										   Log.d("tuacy", "error code = " + exception.getErrorCode());
									   }
								   });
			}
		});

		mButtonFlatMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProtocolsClient.getInstance()
							   .protocolsRequestFlatMap(mContext, new AllBookRequest(), new BookByIdRequest(0),
														new ProtocolsFlatMapFunc<List<BookBean>, BookBean, ProtocolsBaseRequest<BookBean>>() {
															@Override
															public ProtocolsBaseRequest<BookBean> call(List<BookBean> bookBeen,
																									   ProtocolsBaseRequest<BookBean> bookBeanProtocolsBaseRequest) {
																if (bookBeanProtocolsBaseRequest instanceof BookByIdRequest) {
																	BookByIdRequest bookByIdRequest
																		= (BookByIdRequest) bookBeanProtocolsBaseRequest;
																	bookByIdRequest.setId(bookBeen.get(0).getId());
																	return bookByIdRequest;
																}
																return bookBeanProtocolsBaseRequest;
															}
														}, new ProtocolsBaseCallback<BookBean>() {
									   @Override
									   public void onProtocolStart(Object tag) {
										   Log.d("tuacy", "start");
									   }

									   @Override
									   public void onProtocolSuccess(Object tag, BookBean bean) {
										   Log.d("tuacy", bean.toString());
									   }

									   @Override
									   public void onProtocolError(Object tag, ProtocolsException exception) {
										   Log.d("tuacy", "error code = " + exception.getErrorCode());
									   }
								   });
			}
		});
	}

	private void initData() {

	}
}
