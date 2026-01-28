package domain;

import java.io.Serializable;

public enum GameMode implements Serializable {
	ONE_ICE_CREAM,
	TWO_ICE_CREAM,
	OPP_VS_ICE,
	SIMULATION_HUNGRY,
	SIMULATION_FEARFUL,
	SIMULATION_EXPERT;

	public static boolean isTwoPlayerMode (final GameMode mode) {
		return mode == TWO_ICE_CREAM || mode == OPP_VS_ICE;
	}

	public static boolean isSimulation (final GameMode mode) {
		return mode == SIMULATION_EXPERT || mode == SIMULATION_FEARFUL || mode == SIMULATION_HUNGRY;
	}
	
	private static final long serialVersionUID = 1L;
}
