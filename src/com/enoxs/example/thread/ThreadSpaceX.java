package com.enoxs.example.thread;

public class ThreadSpaceX {

	public ThreadSpaceX() {
		// TODO 自動產生的建構子 Stub
		new ThreadSpaceX(1);
		new ThreadSpaceX(2);
		new ThreadSpaceX(3);

		TaskThread taskThread = new TaskThread();
		taskThread.start();

		Thread taskRunnable = new Thread(new TaskRunnable());
		taskRunnable.start();

	}
	public ThreadSpaceX(int index) {
		// TODO 自動產生的建構子 Stub
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("Thread,Hello#" + index);
			}
		});
		thread.start();
	}

	public static void main(String args[]){
		new ThreadSpaceX();
	}
	/**
	 * Class
	 */
	public class TaskThread extends Thread{
		@Override
		public void run() {
			System.out.println("TaskThread,Hello");
		}
	}

	public class TaskRunnable implements Runnable{

		@Override
		public void run() {
			System.out.println("TaskRunnable,Hello");
		}
	}


}
