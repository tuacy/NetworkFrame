package com.tuacy.networkframe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tuacy.networkframe.base.BaseActivity;
import com.tuacy.networkframe.bean.BookBean;
import com.tuacy.networkframe.library.ProtocolsClient;
import com.tuacy.networkframe.library.callback.ProtocolsBaseCallback;
import com.tuacy.networkframe.library.exception.ProtocolsException;
import com.tuacy.networkframe.request.AllBookRequest;

import java.util.List;

public class MainActivity extends BaseActivity {

	private Button mButtonAll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mButtonAll = (Button) findViewById(R.id.button_all_book);
	}

	private void initEvent() {
		mButtonAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProtocolsClient.getInstance()
							   .onProtocolsRequest(mContext, new AllBookRequest(), new ProtocolsBaseCallback<List<BookBean>>() {
								   @Override
								   public void onProtocolStart(Object tag) {

								   }

								   @Override
								   public void onProtocolSuccess(Object tag, List<BookBean> bean) {
									   Log.d("tuacy", bean.toString());
								   }

								   @Override
								   public void onProtocolError(Object tag, ProtocolsException exception) {

								   }
							   });
			}
		});
	}

	private void initData() {

	}
}
