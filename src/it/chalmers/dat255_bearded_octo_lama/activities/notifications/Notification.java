package it.chalmers.dat255_bearded_octo_lama.activities.notifications;

public interface Notification {
	/**
	 * Action to be performed upon nofication activation.
	 */
	public void start();
	
	/**
	 * Action to be performed upon nofication deactivation.
	 */
	public void stop();

}
