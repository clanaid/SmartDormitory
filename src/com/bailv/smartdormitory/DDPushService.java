package com.bailv.smartdormitory;

import java.nio.ByteBuffer;

import org.ddpush.im.v1.client.appuser.Message;
import org.ddpush.im.v1.client.appuser.TCPClientBase;

import com.bailv.util.DDPUSHUtil;
import com.bailv.util.EventsPost;
import com.bailv.util.EventsPost.EventType;

import de.greenrobot.event.EventBus;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class DDPushService extends Service {
	
	MyTcpClient myTcpClient;
	
	private final static String serverAddr = "180.76.148.14";
	private final static String serverPort = "9966";
	private final static String username = "r134";
	private final static String pushPort= "9999";
	
	public class MyTcpClient extends TCPClientBase {

		public MyTcpClient(byte[] uuid, int appid, String serverAddr, int serverPort)
				throws Exception {
			super(uuid, appid, serverAddr, serverPort, 10);

		}

		@Override
		public boolean hasNetworkConnection() {
			return DDPUSHUtil.hasNetwork(DDPushService.this);
		}
		

		@Override
		public void trySystemSleep() {
		}

		@Override
		public void onPushMessage(Message message) {
			if(message == null){
				return;
			}
			if(message.getData() == null || message.getData().length == 0){
				return;
			}
			if(message.getCmd() == 16){// 0x10推送
				Log.d("tuisong", "get");
			}
			if(message.getCmd() == 17){// 0x11 分类推送
				long msg = ByteBuffer.wrap(message.getData(), 5, 8).getLong();
				Log.d("fenlei", msg+"");
			}
			if(message.getCmd() == 32){// 0x20 自定义推送
				String str = null;
				try{
					str = new String(message.getData(),5,message.getContentLength(), "UTF-8");
				}catch(Exception e){
					str = DDPUSHUtil.convert(message.getData(),5,message.getContentLength());
				}
				Log.d("zhidingyi", str);
				EventsPost eventsPost = new EventsPost(str, EventType.DDPUSH);
				EventBus.getDefault().post(eventsPost);
			}
			//setPkgsInfo();
		}

	}

	public DDPushService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("serciceCreat", "in");
		resetClient();
	}

	@Override
	public int onStartCommand(Intent param, int flags, int startId) {
		
		return START_STICKY;
	}
	
	
	protected void resetClient(){
		
		Log.d("resetClient", "in");
		String serverIp = this.serverAddr;
		String serverPort = this.serverPort;
		String pushPort = this.pushPort;
		String userName = this.username;
		if(serverIp == null || serverIp.trim().length() == 0
				|| serverPort == null || serverPort.trim().length() == 0
				|| pushPort == null || pushPort.trim().length() == 0
				|| userName == null || userName.trim().length() == 0){
			return;
		}
		if(this.myTcpClient != null){
			try{myTcpClient.stop();}catch(Exception e){}
		}
		try{
			myTcpClient = new MyTcpClient(DDPUSHUtil.md5Byte(userName), 1, serverIp, Integer.parseInt(serverPort));
			myTcpClient.setHeartbeatInterval(50);
			myTcpClient.start();
			Log.d("tcpclient", "in");
		}catch(Exception e){
			Toast.makeText(this.getApplicationContext(), "启动失败"+e.getMessage(), Toast.LENGTH_LONG).show();
		}
		Toast.makeText(this.getApplicationContext(), "ddpush启动成功", Toast.LENGTH_LONG).show();
	}
	
	
	

	@Override
	public void onDestroy() {
		super.onDestroy();
		//this.cancelTickAlarm();
		myTcpClient.stop();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


}
