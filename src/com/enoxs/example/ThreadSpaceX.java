package com.enoxs.example;

public class ThreadSpaceX {

	public ThreadSpaceX() {
		// TODO 自動產生的建構子 Stub
		runSyncThread();
	}
	public void runStandardThread(){
		Thread thread = new Thread(new Runnable(){
			public void run(){
				try{
					Thread.sleep(5000);
					System.out.println("#Flag0.");
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		});
		thread.start();
		thread.interrupt();
	}
	public void runClassThread(){
		RunExtendsThread thread1 = new RunExtendsThread();
		thread1.start();
		Thread thread2 = new Thread(new RunImplementsRunnable());
		thread2.start();
	}
	public void runSyncThread(){
		SyncObject object = new SyncObject();
		Object lock = new Object();
		ThreadA a = new ThreadA(object,lock);
        ThreadB b = new ThreadB(object,lock);
        
        try {
        	a.start();
			Thread.sleep(50);
			b.start();
		} catch (InterruptedException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
        
	}
	
	public static void main(String []args){
		new ThreadSpaceX();
	}
	
	public class SyncObject{
		private int count=0;
		public void add(){
			count ++;
		}
		public int getCount(){
			return count;
		}
		private int money = 5000;
//		synchronized
		synchronized public void getMoney(int cost){
			this.money = this.money - cost;
		}
		public void showMoney(){
			System.out.println("Money -> " + this.money);
		}
	}
	
	public class RunExtendsThread extends Thread{
		public RunExtendsThread(){
			
		}
		@Override
		public void run(){
			try{
				System.out.println("RunExtendsThread #Flag1.");
				Thread.sleep(5000);
				System.out.println("RunExtendsThread#Flag2.");
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	

	
	public class RunImplementsRunnable implements Runnable{

		@Override
		public void run() {
			// TODO 自動產生的方法 Stub
			try{
				System.out.println("RunImplementsRunnable#Flag1.");
				Thread.sleep(5000);
				System.out.println("RunImplementsRunnable#Flag2.");
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public class ThreadA extends Thread{
		SyncObject obj;
		Object lock;
		public ThreadA(SyncObject obj,Object lock){
			this.obj = obj;
			this.lock = lock;
		}
		@Override
		public void run(){
			try{
				for(int i=0;i<10;i++){
					System.out.println("#" + i);
					obj.getMoney(1);
					Thread.sleep(50);
					obj.showMoney();
				}
				/*
				synchronized (lock) {
					for(int i=0;i<10;i++){
						obj.add();
						 if (obj.getCount() == 5) {
							 lock.notify();
							 System.out.println("已經發出了通知");
							 lock.wait();
							 }
						System.out.println("# " + obj.getCount());
						Thread.sleep(1000);
					}
					System.out.println("Thread A -> finish.");
				}
				*/
//				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	public class ThreadB extends Thread{
		SyncObject obj;
		Object lock;
		public ThreadB(SyncObject obj,Object lock){
			this.obj = obj;
			this.lock = lock;
		}
		@Override
		public void run(){
			try{
				Thread.sleep(150);
				obj.getMoney(1500);
//				System.out.println("# " + obj.getMsg());
				/*
				synchronized (lock) {
					if (obj.getCount() != 5) {
						System.out.println("wait begin " + System.currentTimeMillis());
						lock.wait();
						System.out.println("wait end  "+ System.currentTimeMillis());  
						lock.notify();
						System.out.println("Thread B -> finish.");
					}
				}
				*/
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	

}
