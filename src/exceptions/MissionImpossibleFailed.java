package exceptions;

public class MissionImpossibleFailed extends Exception {
	private static final long serialVersionUID = 1L;

	public MissionImpossibleFailed(String message) {
		super(message);
	}

}
