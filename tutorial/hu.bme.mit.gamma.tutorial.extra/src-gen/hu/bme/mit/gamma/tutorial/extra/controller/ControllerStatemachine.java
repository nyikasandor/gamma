package hu.bme.mit.gamma.tutorial.extra.controller;
import hu.bme.mit.gamma.tutorial.extra.ITimer;
import java.util.LinkedList;
import java.util.List;

public class ControllerStatemachine implements IControllerStatemachine {

	protected class SCIPoliceInterruptImpl implements SCIPoliceInterrupt {
	
		private boolean police;
		
		public void raisePolice() {
			police = true;
		}
		
		protected void clearEvents() {
			police = false;
		}
	}
	
	protected SCIPoliceInterruptImpl sCIPoliceInterrupt;
	
	protected class SCIPriorityPoliceImpl implements SCIPriorityPolice {
	
		private List<SCIPriorityPoliceListener> listeners = new LinkedList<SCIPriorityPoliceListener>();
		
		public List<SCIPriorityPoliceListener> getListeners() {
			return listeners;
		}
		private boolean police;
		
		public boolean isRaisedPolice() {
			return police;
		}
		
		protected void raisePolice() {
			police = true;
			for (SCIPriorityPoliceListener listener : listeners) {
				listener.onPoliceRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		police = false;
		}
		
	}
	
	protected SCIPriorityPoliceImpl sCIPriorityPolice;
	
	protected class SCISecondaryPoliceImpl implements SCISecondaryPolice {
	
		private List<SCISecondaryPoliceListener> listeners = new LinkedList<SCISecondaryPoliceListener>();
		
		public List<SCISecondaryPoliceListener> getListeners() {
			return listeners;
		}
		private boolean police;
		
		public boolean isRaisedPolice() {
			return police;
		}
		
		protected void raisePolice() {
			police = true;
			for (SCISecondaryPoliceListener listener : listeners) {
				listener.onPoliceRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		police = false;
		}
		
	}
	
	protected SCISecondaryPoliceImpl sCISecondaryPolice;
	
	protected class SCIPriorityControlImpl implements SCIPriorityControl {
	
		private List<SCIPriorityControlListener> listeners = new LinkedList<SCIPriorityControlListener>();
		
		public List<SCIPriorityControlListener> getListeners() {
			return listeners;
		}
		private boolean toggle;
		
		public boolean isRaisedToggle() {
			return toggle;
		}
		
		protected void raiseToggle() {
			toggle = true;
			for (SCIPriorityControlListener listener : listeners) {
				listener.onToggleRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		toggle = false;
		}
		
	}
	
	protected SCIPriorityControlImpl sCIPriorityControl;
	
	protected class SCISecondaryControlImpl implements SCISecondaryControl {
	
		private List<SCISecondaryControlListener> listeners = new LinkedList<SCISecondaryControlListener>();
		
		public List<SCISecondaryControlListener> getListeners() {
			return listeners;
		}
		private boolean toggle;
		
		public boolean isRaisedToggle() {
			return toggle;
		}
		
		protected void raiseToggle() {
			toggle = true;
			for (SCISecondaryControlListener listener : listeners) {
				listener.onToggleRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		toggle = false;
		}
		
	}
	
	protected SCISecondaryControlImpl sCISecondaryControl;
	
	private boolean initialized = false;
	
	public enum State {
		main_region_Operating,
		main_region_Operating_operating_Init,
		main_region_Operating_operating_PriorityPrepares,
		main_region_Operating_operating_Secondary,
		main_region_Operating_operating_SecondaryPrepares,
		main_region_Operating_operating_Priority,
		main_region_Interrupted,
		$NullState$
	};
	
	private State[] historyVector = new State[1];
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private ITimer timer;
	
	private final boolean[] timeEvents = new boolean[5];
	public ControllerStatemachine() {
		sCIPoliceInterrupt = new SCIPoliceInterruptImpl();
		sCIPriorityPolice = new SCIPriorityPoliceImpl();
		sCISecondaryPolice = new SCISecondaryPoliceImpl();
		sCIPriorityControl = new SCIPriorityControlImpl();
		sCISecondaryControl = new SCISecondaryControlImpl();
	}
	
	public void init() {
		this.initialized = true;
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		for (int i = 0; i < 1; i++) {
			historyVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		}
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		enterSequence_main_region_default();
	}
	
	public void exit() {
		exitSequence_main_region();
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NullState$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return false;
	}
	/**
	* This method resets the incoming events (time events included).
	*/
	protected void clearEvents() {
		sCIPoliceInterrupt.clearEvents();
		sCIPriorityPolice.clearEvents();
		sCISecondaryPolice.clearEvents();
		sCIPriorityControl.clearEvents();
		sCISecondaryControl.clearEvents();
		for (int i=0; i<timeEvents.length; i++) {
			timeEvents[i] = false;
		}
	}
	
	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
		sCIPriorityPolice.clearOutEvents();
		sCISecondaryPolice.clearOutEvents();
		sCIPriorityControl.clearOutEvents();
		sCISecondaryControl.clearOutEvents();
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case main_region_Operating:
			return stateVector[0].ordinal() >= State.
					main_region_Operating.ordinal()&& stateVector[0].ordinal() <= State.main_region_Operating_operating_Priority.ordinal();
		case main_region_Operating_operating_Init:
			return stateVector[0] == State.main_region_Operating_operating_Init;
		case main_region_Operating_operating_PriorityPrepares:
			return stateVector[0] == State.main_region_Operating_operating_PriorityPrepares;
		case main_region_Operating_operating_Secondary:
			return stateVector[0] == State.main_region_Operating_operating_Secondary;
		case main_region_Operating_operating_SecondaryPrepares:
			return stateVector[0] == State.main_region_Operating_operating_SecondaryPrepares;
		case main_region_Operating_operating_Priority:
			return stateVector[0] == State.main_region_Operating_operating_Priority;
		case main_region_Interrupted:
			return stateVector[0] == State.main_region_Interrupted;
		default:
			return false;
		}
	}
	
	/**
	* Set the {@link ITimer} for the state machine. It must be set
	* externally on a timed state machine before a run cycle can be correct
	* executed.
	* 
	* @param timer
	*/
	public void setTimer(ITimer timer) {
		this.timer = timer;
	}
	
	/**
	* Returns the currently used timer.
	* 
	* @return {@link ITimer}
	*/
	public ITimer getTimer() {
		return timer;
	}
	
	public void timeElapsed(int eventID) {
		timeEvents[eventID] = true;
	}
	
	public SCIPoliceInterrupt getSCIPoliceInterrupt() {
		return sCIPoliceInterrupt;
	}
	
	public SCIPriorityPolice getSCIPriorityPolice() {
		return sCIPriorityPolice;
	}
	
	public SCISecondaryPolice getSCISecondaryPolice() {
		return sCISecondaryPolice;
	}
	
	public SCIPriorityControl getSCIPriorityControl() {
		return sCIPriorityControl;
	}
	
	public SCISecondaryControl getSCISecondaryControl() {
		return sCISecondaryControl;
	}
	
	/* Entry action for state 'Init'. */
	private void entryAction_main_region_Operating_operating_Init() {
		timer.setTimer(this, 0, 2 * 1000, false);
		
		sCIPriorityControl.raiseToggle();
	}
	
	/* Entry action for state 'PriorityPrepares'. */
	private void entryAction_main_region_Operating_operating_PriorityPrepares() {
		timer.setTimer(this, 1, 1 * 1000, false);
		
		sCIPriorityControl.raiseToggle();
	}
	
	/* Entry action for state 'Secondary'. */
	private void entryAction_main_region_Operating_operating_Secondary() {
		timer.setTimer(this, 2, 2 * 1000, false);
		
		sCIPriorityControl.raiseToggle();
		
		sCISecondaryControl.raiseToggle();
	}
	
	/* Entry action for state 'SecondaryPrepares'. */
	private void entryAction_main_region_Operating_operating_SecondaryPrepares() {
		timer.setTimer(this, 3, 1 * 1000, false);
		
		sCISecondaryControl.raiseToggle();
	}
	
	/* Entry action for state 'Priority'. */
	private void entryAction_main_region_Operating_operating_Priority() {
		timer.setTimer(this, 4, 2 * 1000, false);
		
		sCIPriorityControl.raiseToggle();
		
		sCISecondaryControl.raiseToggle();
	}
	
	/* Exit action for state 'Init'. */
	private void exitAction_main_region_Operating_operating_Init() {
		timer.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'PriorityPrepares'. */
	private void exitAction_main_region_Operating_operating_PriorityPrepares() {
		timer.unsetTimer(this, 1);
	}
	
	/* Exit action for state 'Secondary'. */
	private void exitAction_main_region_Operating_operating_Secondary() {
		timer.unsetTimer(this, 2);
	}
	
	/* Exit action for state 'SecondaryPrepares'. */
	private void exitAction_main_region_Operating_operating_SecondaryPrepares() {
		timer.unsetTimer(this, 3);
	}
	
	/* Exit action for state 'Priority'. */
	private void exitAction_main_region_Operating_operating_Priority() {
		timer.unsetTimer(this, 4);
	}
	
	/* 'default' enter sequence for state Operating */
	private void enterSequence_main_region_Operating_default() {
		enterSequence_main_region_Operating_operating_default();
	}
	
	/* 'default' enter sequence for state Init */
	private void enterSequence_main_region_Operating_operating_Init_default() {
		entryAction_main_region_Operating_operating_Init();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_Init;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state PriorityPrepares */
	private void enterSequence_main_region_Operating_operating_PriorityPrepares_default() {
		entryAction_main_region_Operating_operating_PriorityPrepares();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_PriorityPrepares;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Secondary */
	private void enterSequence_main_region_Operating_operating_Secondary_default() {
		entryAction_main_region_Operating_operating_Secondary();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_Secondary;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state SecondaryPrepares */
	private void enterSequence_main_region_Operating_operating_SecondaryPrepares_default() {
		entryAction_main_region_Operating_operating_SecondaryPrepares();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_SecondaryPrepares;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Priority */
	private void enterSequence_main_region_Operating_operating_Priority_default() {
		entryAction_main_region_Operating_operating_Priority();
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Operating_operating_Priority;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Interrupted */
	private void enterSequence_main_region_Interrupted_default() {
		nextStateIndex = 0;
		stateVector[0] = State.main_region_Interrupted;
	}
	
	/* 'default' enter sequence for region main_region */
	private void enterSequence_main_region_default() {
		react_main_region__entry_Default();
	}
	
	/* 'default' enter sequence for region operating */
	private void enterSequence_main_region_Operating_operating_default() {
		react_main_region_Operating_operating__entry_Default();
	}
	
	/* shallow enterSequence with history in child operating */
	private void shallowEnterSequence_main_region_Operating_operating() {
		switch (historyVector[0]) {
		case main_region_Operating_operating_Init:
			enterSequence_main_region_Operating_operating_Init_default();
			break;
		case main_region_Operating_operating_PriorityPrepares:
			enterSequence_main_region_Operating_operating_PriorityPrepares_default();
			break;
		case main_region_Operating_operating_Secondary:
			enterSequence_main_region_Operating_operating_Secondary_default();
			break;
		case main_region_Operating_operating_SecondaryPrepares:
			enterSequence_main_region_Operating_operating_SecondaryPrepares_default();
			break;
		case main_region_Operating_operating_Priority:
			enterSequence_main_region_Operating_operating_Priority_default();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for state Operating */
	private void exitSequence_main_region_Operating() {
		exitSequence_main_region_Operating_operating();
	}
	
	/* Default exit sequence for state Init */
	private void exitSequence_main_region_Operating_operating_Init() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_Init();
	}
	
	/* Default exit sequence for state PriorityPrepares */
	private void exitSequence_main_region_Operating_operating_PriorityPrepares() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_PriorityPrepares();
	}
	
	/* Default exit sequence for state Secondary */
	private void exitSequence_main_region_Operating_operating_Secondary() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_Secondary();
	}
	
	/* Default exit sequence for state SecondaryPrepares */
	private void exitSequence_main_region_Operating_operating_SecondaryPrepares() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_SecondaryPrepares();
	}
	
	/* Default exit sequence for state Priority */
	private void exitSequence_main_region_Operating_operating_Priority() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_region_Operating_operating_Priority();
	}
	
	/* Default exit sequence for state Interrupted */
	private void exitSequence_main_region_Interrupted() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for region main_region */
	private void exitSequence_main_region() {
		switch (stateVector[0]) {
		case main_region_Operating_operating_Init:
			exitSequence_main_region_Operating_operating_Init();
			break;
		case main_region_Operating_operating_PriorityPrepares:
			exitSequence_main_region_Operating_operating_PriorityPrepares();
			break;
		case main_region_Operating_operating_Secondary:
			exitSequence_main_region_Operating_operating_Secondary();
			break;
		case main_region_Operating_operating_SecondaryPrepares:
			exitSequence_main_region_Operating_operating_SecondaryPrepares();
			break;
		case main_region_Operating_operating_Priority:
			exitSequence_main_region_Operating_operating_Priority();
			break;
		case main_region_Interrupted:
			exitSequence_main_region_Interrupted();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region operating */
	private void exitSequence_main_region_Operating_operating() {
		switch (stateVector[0]) {
		case main_region_Operating_operating_Init:
			exitSequence_main_region_Operating_operating_Init();
			break;
		case main_region_Operating_operating_PriorityPrepares:
			exitSequence_main_region_Operating_operating_PriorityPrepares();
			break;
		case main_region_Operating_operating_Secondary:
			exitSequence_main_region_Operating_operating_Secondary();
			break;
		case main_region_Operating_operating_SecondaryPrepares:
			exitSequence_main_region_Operating_operating_SecondaryPrepares();
			break;
		case main_region_Operating_operating_Priority:
			exitSequence_main_region_Operating_operating_Priority();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for shallow history entry  */
	private void react_main_region_Operating_operating__entry_Default() {
		/* Enter the region with shallow history */
		if (historyVector[0] != State.$NullState$) {
			shallowEnterSequence_main_region_Operating_operating();
		} else {
			enterSequence_main_region_Operating_operating_Init_default();
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_region__entry_Default() {
		enterSequence_main_region_Operating_default();
	}
	
	private boolean react(boolean try_transition) {
		return false;
	}
	
	private boolean main_region_Operating_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react(try_transition)==false) {
				if (sCIPoliceInterrupt.police) {
					exitSequence_main_region_Operating();
					sCIPriorityPolice.raisePolice();
					
					sCISecondaryPolice.raisePolice();
					
					enterSequence_main_region_Interrupted_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	private boolean main_region_Operating_operating_Init_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (main_region_Operating_react(try_transition)==false) {
				if (timeEvents[0]) {
					exitSequence_main_region_Operating_operating_Init();
					enterSequence_main_region_Operating_operating_PriorityPrepares_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	private boolean main_region_Operating_operating_PriorityPrepares_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (main_region_Operating_react(try_transition)==false) {
				if (timeEvents[1]) {
					exitSequence_main_region_Operating_operating_PriorityPrepares();
					enterSequence_main_region_Operating_operating_Secondary_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	private boolean main_region_Operating_operating_Secondary_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (main_region_Operating_react(try_transition)==false) {
				if (timeEvents[2]) {
					exitSequence_main_region_Operating_operating_Secondary();
					enterSequence_main_region_Operating_operating_SecondaryPrepares_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	private boolean main_region_Operating_operating_SecondaryPrepares_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (main_region_Operating_react(try_transition)==false) {
				if (timeEvents[3]) {
					exitSequence_main_region_Operating_operating_SecondaryPrepares();
					enterSequence_main_region_Operating_operating_Priority_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	private boolean main_region_Operating_operating_Priority_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (main_region_Operating_react(try_transition)==false) {
				if (timeEvents[4]) {
					exitSequence_main_region_Operating_operating_Priority();
					enterSequence_main_region_Operating_operating_PriorityPrepares_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	private boolean main_region_Interrupted_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react(try_transition)==false) {
				if (sCIPoliceInterrupt.police) {
					exitSequence_main_region_Interrupted();
					sCIPriorityPolice.raisePolice();
					
					sCISecondaryPolice.raisePolice();
					
					enterSequence_main_region_Operating_default();
				} else {
					did_transition = false;
				}
			}
		}
		if (did_transition==false) {
		}
		return did_transition;
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case main_region_Operating_operating_Init:
				main_region_Operating_operating_Init_react(true);
				break;
			case main_region_Operating_operating_PriorityPrepares:
				main_region_Operating_operating_PriorityPrepares_react(true);
				break;
			case main_region_Operating_operating_Secondary:
				main_region_Operating_operating_Secondary_react(true);
				break;
			case main_region_Operating_operating_SecondaryPrepares:
				main_region_Operating_operating_SecondaryPrepares_react(true);
				break;
			case main_region_Operating_operating_Priority:
				main_region_Operating_operating_Priority_react(true);
				break;
			case main_region_Interrupted:
				main_region_Interrupted_react(true);
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
}