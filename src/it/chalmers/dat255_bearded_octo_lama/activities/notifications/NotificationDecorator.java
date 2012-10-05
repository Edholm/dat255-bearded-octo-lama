package it.chalmers.dat255_bearded_octo_lama.activities.notifications;

public abstract class NotificationDecorator implements Notification {

	protected final Notification decoratedNotification;
	
	public NotificationDecorator(Notification decoratedNotification) {
		this.decoratedNotification = decoratedNotification;
	}

	public void start() {
		decoratedNotification.start();
	}

}
