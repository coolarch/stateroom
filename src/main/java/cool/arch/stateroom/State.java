package cool.arch.stateroom;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

public class State<M> {

	private static final AtomicLong COUNTER = new AtomicLong(-1);

	private String name = "State " + COUNTER.incrementAndGet();

	private boolean acceptState = false;

	@Override
	public String toString() {
		return String.format("{'name':'%s', 'acceptState':%s}", name, Boolean.toString(acceptState));
	}

	/**
	 * 
	 */
	private BiFunction<State<M>, Context<M>, M> modelTransform = (state, context) -> context.getModel();

	public Context<M> onEnter(final Context<M> context) {
		final M model = modelTransform.apply(this, context);

		return context.derive(model);
	}

	public String getName() {
		return name;
	}

	public boolean isAcceptState() {
		return acceptState;
	}

	public BiFunction<State<M>, Context<M>, M> getModelTransform() {
		return modelTransform;
	}

	void setName(String name) {
		this.name = name;
	}

	void setAcceptState(boolean acceptState) {
		this.acceptState = acceptState;
	}

	void setModelTransform(BiFunction<State<M>, Context<M>, M> modelTransform) {
		this.modelTransform = modelTransform;
	}

	public static <T> StateBuilder<T> builder() {
		return new StateBuilderImpl<>();
	}

	public static <T> State<T> of(final String name) {
		return State.<T> builder()
			.withName(name)
			.build();
	}

	public static <T> State<T> of(final String name, final boolean acceptState) {
		return State.<T> builder()
			.withName(name)
			.withAcceptState(acceptState)
			.build();
	}

	public static <T> State<T> of(final String name, final boolean acceptState,
		BiFunction<State<T>, Context<T>, T> modelTransform) {
		return State.<T> builder()
			.withName(name)
			.withAcceptState(acceptState)
			.withModelTransform(modelTransform)
			.build();
	}
}
